package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.produkseller.GetDataProductSellerItem
import and5.abrar.e_commerce.view.HomeActivity
import and5.abrar.e_commerce.view.LoginActivity
import and5.abrar.e_commerce.view.adapter.AdapterProductSeller
import and5.abrar.e_commerce.viewmodel.ViewModelHome
import and5.abrar.e_commerce.viewmodel.ViewModelProductSeller
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.forEachIndexed
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_lengkapi_detail_product.*
import kotlinx.android.synthetic.main.activity_profile.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import java.io.File

@AndroidEntryPoint
class LengkapiDetailProductActivity : AppCompatActivity() {
    private lateinit var  userManager: UserManager
    private lateinit var adapter : AdapterProductSeller
    private lateinit var image : Uri
    private val galleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
        icon_foto.setImageURI(result)
        image = result!!
    }
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var postCategory : String
    var categoryID = mutableListOf<Int>()
    var categoryName = mutableListOf<String>()
    var selectedName : MutableList<String?> = mutableListOf()
    var selectedID : MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_detail_product)
        userManager = UserManager(this)
        getCategory()
        arrayAdapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, categoryName)
        select_kategori.setAdapter(arrayAdapter)
        select_kategori.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        arrayAdapter.notifyDataSetChanged()
        select_kategori.setOnItemClickListener{ adapterView, view,position,l ->
            val selected : String? = arrayAdapter.getItem(position)
            selectedName.add(arrayAdapter.getItem(position))
            selectedID.add(categoryID[position])
            categoryName.remove(selected)
            categoryID.remove(categoryID[position])
            val getID = selectedID.toString()
            postCategory = getID.replace("[","").replace("]","")
            Log.e("postcate", postCategory)
            Log.e("slecid",selectedID.toString())
        }

        btn_terbitkan.setOnClickListener {
            jualbarang()
        }
        icon_foto.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ->{
                    galleryResult.launch("image/*")
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)->{
                    showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1010)
                }
            }
        }
    }

    fun jualbarang(){
        var categoryProduct : RequestBody =
            postCategory.toRequestBody("text/plain".toMediaTypeOrNull())
        val namaProdcut : RequestBody =
            edt_namaprodut.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val hargaProduct : RequestBody =
            edt_hargaproduct.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val lokasi: RequestBody =
            edt_lokasi.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val desc : RequestBody =
            edt_deskripsi.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val contentResolver = this.applicationContext.contentResolver
        val type = contentResolver.getType(image)
        val tempFile = File.createTempFile("temp-", null, null)
        val inputstream = contentResolver.openInputStream(image)
        tempFile.outputStream().use {
            inputstream?.copyTo(it)
        }
        val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
        val body = MultipartBody.Part.createFormData("image", tempFile.name, requestBody)
        val viewModelDataSeller = ViewModelProvider(this)[ViewModelProductSeller::class.java]
        viewModelDataSeller.jualproduct(userManager.fetchAuthToken().toString(),namaProdcut,desc,hargaProduct,categoryProduct,lokasi,body)
        startActivity(Intent(applicationContext, HomeActivity::class.java))
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("izin diperlukan.")
            .setMessage("Diperlukan untuk mengimpor foto.")
            .setPositiveButton("setuju") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1010)
            }
            .create()
            .show()
    }
    private fun getCategory(){
        val viewModelSellerCategory = ViewModelProvider(this)[ViewModelProductSeller::class.java]
        viewModelSellerCategory.sellerCategory.observe(this){ it ->
            it.forEach{
                categoryName.add(it.name)
                categoryID.add(it.id)
            }
        }
        viewModelSellerCategory.getSellerCategory()
    }
}
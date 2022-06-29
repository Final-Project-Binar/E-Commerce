package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.produkseller.GetDataProductSellerItem
import and5.abrar.e_commerce.view.HomeActivity
import and5.abrar.e_commerce.view.LoginActivity
import and5.abrar.e_commerce.view.adapter.AdapterProductSeller
import and5.abrar.e_commerce.viewmodel.ViewModelProductSeller
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lengkapi_detail_product)
        userManager = UserManager(this)
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
        val namaProdcut : RequestBody =
            edt_namaprodut.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val hargaProduct : RequestBody =
            edt_hargaproduct.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val lokasi: RequestBody =
            edtDeskripsiKegiatan.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())

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
}
@file:Suppress("DEPRECATION")

package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.viewmodel.ViewModelUser
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.DelicateCoroutinesApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@DelicateCoroutinesApi
@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var  userManager: UserManager
    private var selectedUri: Uri? = null
    private lateinit var image : Uri
    private var ngambil : Boolean = false

    private val galleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
    pict_image_profile.setImageURI(result)
    image = result!!
    ngambil = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        userManager = UserManager(this)
        updatedata()
        back.setOnClickListener {
            startActivity(Intent(this,AkunSayaActivity::class.java))
        }
        pict_image_profile.setOnClickListener {
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
        getprofile()
    }
    
    private fun getprofile(){
        val viewModelDataSeller = ViewModelProvider(this)[ViewModelUser::class.java]
        viewModelDataSeller.getProfiler(token = userManager.fetchAuthToken().toString())
        viewModelDataSeller.profileData.observe(this) {
            etNama_profile.setText(it.fullName)
            profile_kota.setText(it.city)
            etAlamat_profile.setText(it.address)
            etNohp_profile.setText(it.phoneNumber.toString())
            Glide.with(applicationContext).load(it.imageUrl).into(pict_image_profile)
        }
    }

     private fun viewmodel(token : String, fname : RequestBody , phone : RequestBody , address : RequestBody ,  city:RequestBody, image :MultipartBody.Part  ){
        val viewModelDataSeller = ViewModelProvider(this@ProfileActivity)[ViewModelUser::class.java]
        viewModelDataSeller.userProfile(token,fname, phone, address, city,image)
         startActivity(Intent(this,AkunSayaActivity::class.java))
    }

    private fun updatenogambar(token : String, fname : RequestBody , phone : RequestBody , address : RequestBody ,  city:RequestBody){
        val viewModelDataSeller = ViewModelProvider(this@ProfileActivity)[ViewModelUser::class.java]
        viewModelDataSeller.userProfilenogambar(token,fname, phone, address, city)
        startActivity(Intent(this,AkunSayaActivity::class.java))
    }


     private fun updatedata() {
         btn_simpan_profile.setOnClickListener {
             val fname: RequestBody =
                 etNama_profile.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
             val kota: RequestBody =
                 profile_kota.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
             val alamat: RequestBody =
                 etAlamat_profile.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
             val nohp: RequestBody =
                 etNohp_profile.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
             if (!ngambil){
                 Log.e("nogambar","ghg")
                 updatenogambar(
                     token = userManager.fetchAuthToken().toString(),
                     fname,
                     nohp,
                     alamat,
                     kota
                 )
             }else{
                 val contentResolver = this.applicationContext.contentResolver
                 val type = contentResolver.getType(image)
                 val tempFile = File.createTempFile("temp-", null, null)
                 val inputstream = contentResolver.openInputStream(image)
                 tempFile.outputStream().use {
                     inputstream?.copyTo(it)
                 }
                 val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
                 val body = MultipartBody.Part.createFormData("image", tempFile.name, requestBody)
                 Log.e("ada gambar", type.toString())
                 viewmodel(
                     token = userManager.fetchAuthToken().toString(),
                     fname,
                     nohp,
                     alamat,
                     kota,
                     body
                 )
             }
         }
     }

    private fun startContentProvider() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 2020)

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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            2020 -> {
                val uri = data?.data
                if (uri != null) {
                    selectedUri = uri
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1010 ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startContentProvider()
                } else {
                    Toast.makeText(this, "Izin DI tolak", Toast.LENGTH_SHORT).show()
                }
        }
    }


}
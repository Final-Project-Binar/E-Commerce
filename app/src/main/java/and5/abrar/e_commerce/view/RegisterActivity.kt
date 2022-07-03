package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.register.PostUserRegister
import and5.abrar.e_commerce.model.register.RequestPost
import and5.abrar.e_commerce.network.ApiClient
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var apiClient: ApiClient
    private lateinit var image : Uri
    private val galleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
        image_register.setImageURI(result)
        image = result!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        image_register.setOnClickListener {
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
        register()
        goToLogin()
    }
     // retrofit

    private fun register(){
        btn_daftar.setOnClickListener {
            val contentResolver = this.applicationContext.contentResolver
            val type = contentResolver.getType(image)
            val tempFile = File.createTempFile("temp-", null, null)
            val inputstream = contentResolver.openInputStream(image)
            tempFile.outputStream().use {
                inputstream?.copyTo(it)
            }
            val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
            val body = MultipartBody.Part.createFormData("image", tempFile.name, requestBody)
            val email : RequestBody = etEmail_register.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val fullName : RequestBody = etNama_register.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val password : RequestBody = etPassword_register.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val phone : RequestBody = etPhone_register.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val alamat : RequestBody = etAddress_register.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val city : RequestBody = etCity_register.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            if (etNama_register.text.isEmpty()){
                Toast.makeText(this@RegisterActivity, "Nama lengkap harus di isi", Toast.LENGTH_SHORT).show()
                tv_error_nama_register.text = "Nama lengkap harus di isi"
            } else if (etEmail_register.text.isEmpty()){
                Toast.makeText(this@RegisterActivity, "Email harus diisi", Toast.LENGTH_SHORT).show()
                tv_error_email_register.text = "Email harus diisi"
            } else if ( etPassword_register.text.isEmpty()){
                Toast.makeText(this@RegisterActivity, "Password harus di isi", Toast.LENGTH_SHORT).show()
                tv_error_password_register.text = "Password harus di isi"
            } else if ( etPassword_register.text.length < 5){
                Toast.makeText(this@RegisterActivity, "Panjang Password kurang dari 5", Toast.LENGTH_SHORT).show()
                tv_error_password_register.text = "Panjang Password kurang dari 5"
            } else if (etPhone_register.text.isEmpty()){
                Toast.makeText(this@RegisterActivity, "Nomor Handphone Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
                tv_error_phone_register.text = "Nomor Handphone Harus di isi "
            }else if (etAddress_register.text.isEmpty()){
                Toast.makeText(this@RegisterActivity, "Address Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
                tv_error_address_register.text = "Address Harus di isi "
            }else if (etCity_register.text.isEmpty()){
                Toast.makeText(this@RegisterActivity, "City Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
                tv_error_city_register.text = "City Harus di isi "
            }
            else {
                doRegister(email,fullName,password,alamat,city,body,phone)
            }
        }
    }

    private fun doRegister(email: RequestBody, fullName: RequestBody, password: RequestBody, alamat : RequestBody, kota : RequestBody, gambar : MultipartBody.Part, phone : RequestBody){
        apiClient = ApiClient()

        apiClient.getApiService(this).registeruser(email,fullName,password,phone,alamat,kota,gambar)
            .enqueue(object : Callback<PostUserRegister> {
                override fun onResponse(
                    call: Call<PostUserRegister>,
                    response: Response<PostUserRegister>
                ) {
                    if (response.isSuccessful){
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        Toast.makeText(this@RegisterActivity, response.message(), Toast.LENGTH_SHORT).show()
//                        if (fullName.isEmpty()){
//                            Toast.makeText(this@RegisterActivity, "Nama lengkap harus di isi", Toast.LENGTH_SHORT).show()
//                            tv_error_nama_register.text = "Nama lengkap harus di isi"
//                        } else if (email.isEmpty()){
//                            Toast.makeText(this@RegisterActivity, "Email harus diisi", Toast.LENGTH_SHORT).show()
//                            tv_error_email_register.text = "Email harus diisi"
//                        } else if (password.isEmpty()){
//                            Toast.makeText(this@RegisterActivity, "Password harus di isi", Toast.LENGTH_SHORT).show()
//                            tv_error_password_register.text = "Password harus di isi"
//                        } else if (password.length < 5){
//                            Toast.makeText(this@RegisterActivity, "Panjang Password kurang dari 5", Toast.LENGTH_SHORT).show()
//                            tv_error_password_register.text = "Panjang Password kurang dari 5"
//                        } else {
//                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
//                            Toast.makeText(this@RegisterActivity, response.message(), Toast.LENGTH_SHORT).show()
//                        }
                    } else {
                        Toast.makeText(this@RegisterActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PostUserRegister>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
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
    // sudah punya akun
    private fun goToLogin(){
        tv_sudah_punya_akun.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register()
        goToLogin()
    }

    private fun register(){
        btn_daftar.setOnClickListener {
            val email : RequestBody = etEmail_register.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val fullName : RequestBody = etNama_register.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val password : RequestBody = etPassword_register.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val phone : RequestBody = etPhone_register.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val alamat : RequestBody = etAddress_register.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val city : RequestBody = etCity_register.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            if (etNama_register.text.isEmpty()){
                Toast.makeText(this@RegisterActivity, "Nama lengkap harus di isi", Toast.LENGTH_SHORT).show()
                tv_error_nama_register.text = "Nama lengkap harus di isi"
            }
            else if (etEmail_register.text.isEmpty()){
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
                doRegister(email,fullName,password,alamat,city,phone)
            }
        }
    }

    private fun doRegister(email: RequestBody, fullName: RequestBody, password: RequestBody, alamat : RequestBody, kota : RequestBody, phone : RequestBody){
        apiClient = ApiClient()

        apiClient.getApiService(this).registeruser(email,fullName,password,phone,alamat,kota)
            .enqueue(object : Callback<PostUserRegister> {
                override fun onResponse(
                    call: Call<PostUserRegister>,
                    response: Response<PostUserRegister>
                ) {
                    if (response.isSuccessful){
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        Toast.makeText(this@RegisterActivity, response.message(), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@RegisterActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PostUserRegister>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    // sudah punya akun
    private fun goToLogin(){
        loginDisini.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
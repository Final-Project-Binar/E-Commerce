package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.register.PostUserRegister
import and5.abrar.e_commerce.model.register.RequestPost
import and5.abrar.e_commerce.network.ApiClient
import and5.abrar.e_commerce.network.ApiClient3
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.isSrgb
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

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
//
////     using view model
//    private fun register(){
//        btn_daftar.setOnClickListener {
//            registerUsingViewModel()
//        }
//    }
//
//    private fun registerUsingViewModel(){
//        val address = etAddress_register.text.toString()
//        val city = etCity_register.text.toString()
//        val email = etEmail_register.text.toString()
//        val fullName = etNama_register.text.toString()
//        val password = etPassword_register.text.toString()
//        val phoneNumber = etPhone_register.text.toString()
//
//        val viewModel = ViewModelProvider(this)[ViewModelUserRegister::class.java]
//        viewModel.registerLiveData.observe(this, Observer {
//            if (fullName.isEmpty()){
//                Toast.makeText(this, "Nama lengkap harus di isi", Toast.LENGTH_SHORT).show()
//                tv_error_nama_register.text = "Nama lengkap harus di isi"
//            } else if (email.isEmpty()){
//                Toast.makeText(this, "Email harus diisi", Toast.LENGTH_SHORT).show()
//                tv_error_email_register.text = "Email harus diisi"
//            } else if (password.isEmpty()){
//                Toast.makeText(this, "Password harus di isi", Toast.LENGTH_SHORT).show()
//                tv_error_password_register.text = "Password harus di isi"
//            } else if (password.length < 5){
//                Toast.makeText(this, "Panjang Password kurang dari 5", Toast.LENGTH_SHORT).show()
//                tv_error_password_register.text = "Panjang Password kurang dari 5"
//            } else if (phoneNumber.toLong() <= 0){
//                Toast.makeText(this, "Phone Number harus di isi", Toast.LENGTH_SHORT).show()
//                tv_error_phone_register.text = "Phone Number harus di isi"
//            } else if (phoneNumber.toLong() <= 10){
//                Toast.makeText(this, "Phone Number kurang dari 10", Toast.LENGTH_SHORT).show()
//                tv_error_phone_register.text = "Phone Number kurang dari 10"
//            } else if (address.isEmpty()){
//                Toast.makeText(this, "Alamat harus di isi", Toast.LENGTH_SHORT).show()
//                tv_error_address_register.text = "Alamat harus di isi"
//            } else if (city.isEmpty()){
//                Toast.makeText(this, "Kota harus di isi", Toast.LENGTH_SHORT).show()
//                tv_error_city_register.text = "Kota harus di isi"
//            } else {
//                Toast.makeText(this, "Berhasil Registrasi", Toast.LENGTH_SHORT).show()
////                startActivity(Intent(this, LoginActivity::class.java))
//            }
//        })
//
//        viewModel.register(address, city, email, fullName, image_register.toString(), password, phoneNumber.toLong())
//    }



     // retrofit

    private fun register(){
        btn_daftar.setOnClickListener {
            val email = etEmail_register.text.toString()
            val fullName = etNama_register.text.toString()
            val password = etPassword_register.text.toString()

            if (fullName.isEmpty()){
                Toast.makeText(this, "Nama lengkap harus di isi", Toast.LENGTH_SHORT).show()
                tv_error_nama_register.text = "Nama lengkap harus di isi"
            } else if (email.isEmpty()){
                Toast.makeText(this, "Email harus diisi", Toast.LENGTH_SHORT).show()
                tv_error_email_register.text = "Email harus diisi"
            } else if (password.isEmpty()){
                Toast.makeText(this, "Password harus di isi", Toast.LENGTH_SHORT).show()
                tv_error_password_register.text = "Password harus di isi"
            } else if (password.length < 5){
                Toast.makeText(this, "Panjang Password kurang dari 5", Toast.LENGTH_SHORT).show()
                tv_error_password_register.text = "Panjang Password kurang dari 5"
            } else {
//                startActivity(Intent(this, LoginActivity::class.java))
                doRegister(email, fullName, password)
            }
        }

    }

    private fun doRegister(email: String, fullName: String, password: String){
        apiClient = ApiClient()

        apiClient.getApiService(this).register(RequestPost("-", "-", email, fullName, "-", password, 62))
            .enqueue(object : Callback<PostUserRegister> {
                override fun onResponse(
                    call: Call<PostUserRegister>,
                    response: Response<PostUserRegister>
                ) {
                    if (response.isSuccessful){
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
        tv_sudah_punya_akun.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }


}
package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.register.PostUserRegister
import and5.abrar.e_commerce.model.register.RequestPost
import and5.abrar.e_commerce.network.ApiClient
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

     // retrofit

    private fun register(){
        btn_daftar.setOnClickListener {
            val email = etEmail_register.text.toString()
            val fullName = etNama_register.text.toString()
            val password = etPassword_register.text.toString()

            doRegister(email, fullName, password)

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
                        if (fullName.isEmpty()){
                            Toast.makeText(this@RegisterActivity, "Nama lengkap harus di isi", Toast.LENGTH_SHORT).show()
                            tv_error_nama_register.text = "Nama lengkap harus di isi"
                        } else if (email.isEmpty()){
                            Toast.makeText(this@RegisterActivity, "Email harus diisi", Toast.LENGTH_SHORT).show()
                            tv_error_email_register.text = "Email harus diisi"
                        } else if (password.isEmpty()){
                            Toast.makeText(this@RegisterActivity, "Password harus di isi", Toast.LENGTH_SHORT).show()
                            tv_error_password_register.text = "Password harus di isi"
                        } else if (password.length < 5){
                            Toast.makeText(this@RegisterActivity, "Panjang Password kurang dari 5", Toast.LENGTH_SHORT).show()
                            tv_error_password_register.text = "Panjang Password kurang dari 5"
                        } else {
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            Toast.makeText(this@RegisterActivity, response.message(), Toast.LENGTH_SHORT).show()
                        }
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
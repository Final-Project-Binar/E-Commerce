package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
//import and5.abrar.e_commerce.viewmodel.ViewModelUserRegister
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_register.*

//@SuppressLint("SetTextI18n")
//@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
//    private var checkEmail = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//        checkDataRegister()
    }

//    private fun checkDataRegister(){
//        val fullName = etNama_register.text.toString()
//        val email = etEmail_register.text.toString()
//        val password = etPassword_register.text.toString()
//
//        if (fullName.isEmpty()){
//            Toast.makeText(this, "Isi Nama Lengkap", Toast.LENGTH_SHORT).show()
//            tv_error_nama_register.text = "Isi Nama Lengkap"
//        } else if (email.isEmpty()){
//            Toast.makeText(this, "Isi Email Anda", Toast.LENGTH_SHORT).show()
//            tv_error_email_register.text = "Isi Email Anda"
//        } else if (password.isEmpty()){
//            Toast.makeText(this, "Isi Password Anda", Toast.LENGTH_SHORT).show()
//            tv_error_password_register.text = "Isi Password Anda"
//        } else if (etPassword_register.text.length < 5){
//            Toast.makeText(this, "Panjang password kurang dari 5", Toast.LENGTH_SHORT).show()
//            tv_error_password_register.text = "Panjang password kurang dari 5"
//        } else if (email == checkEmail && checkEmail.toInt() > 1) {
//            Toast.makeText(this, "Email Sudah Terdaftar", Toast.LENGTH_SHORT).show()
//            tv_error_email_register.text = "Email Sudah Terdaftar"
//        } else {
//            registerUsingViewModel(email, password, fullName)
//        }
//    }

//    private fun registerUsingViewModel(email : String, password : String, fullName : String){
//        val viewModelRegister = ViewModelProvider(this)[ViewModelUserRegister::class.java]
//
//        viewModelRegister.registerLiveData.observe(this, Observer {
//            if (it != null ){
//                checkEmail = it.email
//                Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this, LoginActivity::class.java))
//            } else {
//                Toast.makeText(this, "Register Gagal", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        viewModelRegister.register(email, password, fullName)
//
//    }
}
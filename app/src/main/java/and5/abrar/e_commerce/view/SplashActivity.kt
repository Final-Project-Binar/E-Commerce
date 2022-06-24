package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.login.LoginRequest
import and5.abrar.e_commerce.model.login.LoginResponse
import and5.abrar.e_commerce.model.login.PostLoginUserResponse
import and5.abrar.e_commerce.viewmodel.ViewModelUser
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var userManager: UserManager
    private lateinit var email : String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        userManager = UserManager(this)

        Handler(Looper.getMainLooper()).postDelayed({
            userManager.booelan.asLiveData().observe(this) {
                if (it == true ) {
                    userManager.email.asLiveData().observe(this) { result ->
                        email = result.toString()
                    }
                    userManager.password.asLiveData().observe(this) { result ->
                        password = result.toString()
                    }
                    requestNewLoginToken(email, password)
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }, 2000)
    }

    private fun requestNewLoginToken(email: String, password: String){
        val viewModelUser = ViewModelProvider(this)[ViewModelUser::class.java]
        viewModelUser.userLogin(LoginRequest(email, password))
        viewModelUser.responseMessage.observe(this) { responseMessage ->
            if (responseMessage){
                viewModelUser.user.observe(this) {
                    saveToken(it, password)
                }
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveToken(postLoginUserResponse: PostLoginUserResponse, password: String ){
        userManager = UserManager(this)
        lifecycleScope.launch {
            withContext(Dispatchers.Main){
                userManager.setBoolean(true)
                userManager.saveToken(
                    postLoginUserResponse.email,
                    postLoginUserResponse.name,
                    postLoginUserResponse.access_token,
                    password
                )
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
            }
        }
    }
}
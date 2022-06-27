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
        checkAccount()
    }
    private fun checkAccount(){
        userManager = UserManager(this)
        val checkToken = userManager.fetchAuthToken().toString()
        Handler(Looper.getMainLooper()).postDelayed({
            if (checkToken != ""){
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        },3500)
    }

}
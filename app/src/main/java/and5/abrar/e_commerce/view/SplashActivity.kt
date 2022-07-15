@file:Suppress("DEPRECATION")

package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.view.seller.OfflineActvty
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        userManager = UserManager(this)

        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true
        if (isConnected){
            checkAccount()
        }else{
            Toast.makeText(applicationContext, "Tidak Ada Koneksi Internet", Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(this, OfflineActvty::class.java))
        }
        textView5.isVisible = true
        progressBarSplash.isVisible = true
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
        },4500)

    }

}
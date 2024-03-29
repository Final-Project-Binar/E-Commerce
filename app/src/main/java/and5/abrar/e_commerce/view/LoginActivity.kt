@file:Suppress("RedundantSamConstructor")

package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.login.LoginRequest
import and5.abrar.e_commerce.model.login.LoginResponse
import and5.abrar.e_commerce.network.ApiClient
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isInvisible
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@DelicateCoroutinesApi
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var userManager: UserManager
    private lateinit var apiClient: ApiClient
    // create a CancellationSignal variable and assign a value null to it
    private var cancellationSignal: CancellationSignal? = null
    private var email : String = ""
    private var password : String = ""

    // create an authenticationCallback
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() = @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            // here we need to implement two methods
            // onAuthenticationError and onAuthenticationSucceeded
            // If the fingerprint is not recognized by the app it will call
            // onAuthenticationError and show a toast
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                notifyUser("Autentikasi Gagal : Fitur Sidik Jari Tidak Terbaca")
            }

            // If the fingerprint is recognized by the app then it will call
            // onAuthenticationSucceeded and show a toast that Authentication has Succeed
            // Here you can also start a new activity after that
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                notifyUser("Autentikasi Berhasil")
                if(email.isEmpty() && password.isEmpty()){
                    notifyUser("Anda Belum Mendaftar FingerPrint")
                }else{
                    loginauth(email,password)
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userManager = UserManager(this)
        apiClient = ApiClient()
        checkBiometricSupport()
        userManager.fingerEmail.asLiveData().observe(this){
            email = it
        }
        userManager.fingerPassword.asLiveData().observe(this){
            password = it
        }
        login_btnLogin.setOnClickListener {
            loginauth(login_email.text.toString(),login_pass.text.toString())
        }
        login_regsister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        back()
        start_authentication.setOnClickListener {
            // This creates a dialog of biometric auth and
            // it requires title , subtitle ,
            // and description
            // In our case there is a cancel button by
            // clicking it, it will cancel the process of
            // fingerprint authentication
            val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Tekan Jari Anda ke SidikJari")
                .setNegativeButton("Cancel", this.mainExecutor, DialogInterface.OnClickListener { _, _ ->
                    notifyUser("Autentikasi Dibatalkan")
                }).build()

            // start the authenticationCallback in mainExecutor
            biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
        }
    }

    private fun loginauth(loginemail : String, loginPassword : String){
        apiClient.getApiService().login(LoginRequest(email = loginemail, password = loginPassword))
            .enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()
                    if (response.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "Berhasil Login", Toast.LENGTH_SHORT).show()
                        userManager.saveAuthToken(
                            token = loginResponse!!.authToken
                        )
                        GlobalScope.launch {
                            userManager.setBoolean(true)
                            userManager.logindata(loginemail,loginPassword)
                        }
                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                        finish()
                    } else if (login_email.text.toString().isEmpty()){
                        Toast.makeText(applicationContext, "Email Harus Diisi", Toast.LENGTH_SHORT).show()
                    } else if (login_pass.text.toString().isEmpty()){
                        Toast.makeText(applicationContext, "Password Harus Diisi", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "Email atau Password Salah", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }
    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Autentikasi Dibatalkan Oleh Pengguna")
        }
        return cancellationSignal as CancellationSignal
    }

    // it checks whether the app the app has fingerprint permission
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkBiometricSupport(): Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isDeviceSecure) {
            start_authentication.isInvisible = true
            return false
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notifyUser("Autentikasi SidikJari Belum Di Nyalakan")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }

    // this is a toast method which is responsible for showing toast
    // it takes a string as parameter
    private fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun back(){
        imageBack.setOnClickListener {
            startActivity(Intent(applicationContext, AkunSayaActivity::class.java))
            finish()
        }
    }
}
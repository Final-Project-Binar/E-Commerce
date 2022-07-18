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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.CancellationSignal
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat


@DelicateCoroutinesApi
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var userManager: UserManager
    private lateinit var apiClient: ApiClient
    // create a CancellationSignal variable and assign a value null to it
    private var cancellationSignal: CancellationSignal? = null

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
                notifyUser("Authentication Error : $errString")
            }

            // If the fingerprint is recognized by the app then it will call
            // onAuthenticationSucceeded and show a toast that Authentication has Succeed
            // Here you can also start a new activity after that
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                notifyUser("Authentication Succeeded")
                loginauth("abrar@mail.com","12345678")
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userManager = UserManager(this)
        apiClient = ApiClient()
        checkBiometricSupport()
        login_btnLogin.setOnClickListener {
            loginauth(login_email.text.toString(),login_pass.text.toString())
        }
        login_regsister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        start_authentication.setOnClickListener {
            // This creates a dialog of biometric auth and
            // it requires title , subtitle ,
            // and description
            // In our case there is a cancel button by
            // clicking it, it will cancel the process of
            // fingerprint authentication
            val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Tekan Jari Anda ke fingerprint")
                .setNegativeButton("Cancel", this.mainExecutor, DialogInterface.OnClickListener { dialog, which ->
                    notifyUser("Authentication Cancelled")
                }).build()

            // start the authenticationCallback in mainExecutor
            biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
        }
    }

    private fun loginauth(loginemail : String, loginPassword : String){
        apiClient.getApiService(this).login(LoginRequest(email = loginemail, password = loginPassword))
            .enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()
                    if (response.isSuccessful) {
                        userManager.saveAuthToken(
                            token = loginResponse!!.authToken
                        )
                        GlobalScope.launch {
                            userManager.setBoolean(true)
                        }
                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                    } else {
                        Toast.makeText(applicationContext, "gagal login", Toast.LENGTH_SHORT).show()
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
            notifyUser("Authentication was Cancelled by the user")
        }
        return cancellationSignal as CancellationSignal
    }

    // it checks whether the app the app has fingerprint permission
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkBiometricSupport(): Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isDeviceSecure) {
            notifyUser("Fingerprint authentication has not been enabled in settings")
            return false
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint Authentication Permission is not enabled")
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
}
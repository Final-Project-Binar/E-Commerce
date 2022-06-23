package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.login.LoginRequest
import and5.abrar.e_commerce.model.login.LoginResponse
import and5.abrar.e_commerce.network.ApiClient
import and5.abrar.e_commerce.view.buyer.NotifikasiBuyerActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var userManager: UserManager
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userManager = UserManager(this)
        apiClient = ApiClient()
        login_btnLogin.setOnClickListener {
            loginauth(login_email.text.toString(),login_pass.text.toString())
        }

    }
        private fun loginauth(loginemail : String, loginPassword : String){
            apiClient.getApiService(this).login(LoginRequest(email = loginemail, password = loginPassword))
                .enqueue(object : Callback<LoginResponse>{
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            userManager.saveAuthToken(token = response.body()!!.authToken)
                            startActivity(Intent(applicationContext,NotifikasiBuyerActivity::class.java))
                        } else {
                            Toast.makeText(applicationContext, "gagal login", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                    }

                })
        }
}
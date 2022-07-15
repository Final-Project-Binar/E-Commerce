package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.register.PostUserRegister
import and5.abrar.e_commerce.network.ApiClient
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.coroutines.DelicateCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@DelicateCoroutinesApi
class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var apiClient: ApiClient
    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        apiClient = ApiClient()
        userManager = UserManager(this)
        changePassword()

    }

    private fun changePassword(){
        btn_change_pasword.setOnClickListener {
            if (etCurrentPassword.text.toString().isEmpty()){
                Toast.makeText(this, "password lama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (etNewPassword.text.toString().isEmpty()){
                Toast.makeText(this, "password baru tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (etConfirmPassword.text.toString().isEmpty()){
                Toast.makeText(this, "konfirmasi password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (etConfirmPassword.text.toString() != etNewPassword.text.toString()){
                Toast.makeText(this, "konfirmasi password tidak sama", Toast.LENGTH_SHORT).show()
            } else {
                prosesChangePassword()
            }
        }

    }

    private fun prosesChangePassword(){
        val currentPassword : RequestBody = etCurrentPassword.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val newPassword : RequestBody = etNewPassword.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val confirmPassword : RequestBody = etConfirmPassword.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        apiClient.getApiService(this).changePassword(
            userManager.fetchAuthToken().toString(),
            currentPassword, newPassword, confirmPassword

        )
            .enqueue(object : Callback<PostUserRegister> {
                override fun onResponse(
                    call: Call<PostUserRegister>,
                    response: Response<PostUserRegister>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(this@ChangePasswordActivity, response.message(), Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@ChangePasswordActivity, AkunSayaActivity::class.java))
                    } else {
                        Toast.makeText(this@ChangePasswordActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PostUserRegister>, t: Throwable) {
                    Toast.makeText(this@ChangePasswordActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }
}
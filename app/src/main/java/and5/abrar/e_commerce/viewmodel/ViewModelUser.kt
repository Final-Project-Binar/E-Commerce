package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.login.LoginRequest
import and5.abrar.e_commerce.model.login.LoginResponse
import and5.abrar.e_commerce.model.login.PostLoginUserResponse
import and5.abrar.e_commerce.model.register.PostUserRegister
import and5.abrar.e_commerce.network.ApiClient
import and5.abrar.e_commerce.network.ApiService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelUser @Inject constructor(api: ApiService) : ViewModel() {
    private val livedataUser = MutableLiveData<PostLoginUserResponse>()
    private val liveDatUserReg = MutableLiveData<PostUserRegister>()

    val user : LiveData<PostLoginUserResponse> = livedataUser
    val userReg : LiveData<PostUserRegister> = liveDatUserReg
    private val liveDataResponseMessage = MutableLiveData<Boolean>()
    val responseMessage: LiveData<Boolean> = liveDataResponseMessage
    private val apiService = api
    private lateinit var apiClient: ApiClient

    fun userLogin(loginRequest: LoginRequest){

        apiClient = ApiClient()
        apiService.auth(loginRequest)
            .enqueue(object : Callback<PostLoginUserResponse> {
                override fun onResponse(
                    call: Call<PostLoginUserResponse>,
                    response: Response<PostLoginUserResponse>
                ) {
                    liveDataResponseMessage.value = response.isSuccessful
                    if (response.isSuccessful) {

                        livedataUser.value = response.body()

                    } else {
                        liveDataResponseMessage.value = false
                    }
                }

                override fun onFailure(call: Call<PostLoginUserResponse>, t: Throwable) {
                    liveDataResponseMessage.value = false
                }

            })
    }

    fun registerUser(email: String, full_name: String, password: String){
        apiService.registeruser(email, full_name, password)
            .enqueue(object : Callback<PostUserRegister> {
                override fun onResponse(
                    call: Call<PostUserRegister>,
                    response: Response<PostUserRegister>
                ) {
                    if (response.isSuccessful){
                        liveDatUserReg.postValue(response.body())
                    } else {

                    }
                }

                override fun onFailure(call: Call<PostUserRegister>, t: Throwable) {

                }

            })
    }

}
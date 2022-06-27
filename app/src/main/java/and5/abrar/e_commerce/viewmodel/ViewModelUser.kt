package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.login.LoginRequest
import and5.abrar.e_commerce.model.login.LoginResponse
import and5.abrar.e_commerce.model.login.PostLoginUserResponse
import and5.abrar.e_commerce.model.produkseller.GetUserResponse
import and5.abrar.e_commerce.model.register.PostUserRegister
import and5.abrar.e_commerce.model.user.GetUser
import and5.abrar.e_commerce.network.ApiClient
import and5.abrar.e_commerce.network.ApiService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ViewModelUser @Inject constructor(api: ApiService) : ViewModel() {
    private val livedatauserprofile = MutableLiveData<GetUser>()

    val profile : LiveData<GetUser> = livedatauserprofile

    private val liveDataResponseMessage = MutableLiveData<Boolean>()
    val responseMessage: LiveData<Boolean> = liveDataResponseMessage
    private val apiService = api


    fun userProfile(token : String, fname : RequestBody, phone : RequestBody, address : RequestBody, city:RequestBody,image :MultipartBody.Part  ){
        apiService.profileuser(token,fname,phone,address, city,image)
            .enqueue(object  : Callback<GetUser>{
                override fun onResponse(call: Call<GetUser>, response: Response<GetUser>) {
                   if(response.isSuccessful){
                       livedatauserprofile.value = response.body()
                   }
                }

                override fun onFailure(call: Call<GetUser>, t: Throwable) {
                   //
                }

            })
    }
}
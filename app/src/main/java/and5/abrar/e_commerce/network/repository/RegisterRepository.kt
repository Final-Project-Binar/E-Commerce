package and5.abrar.e_commerce.network.repository

import and5.abrar.e_commerce.model.register.PostUserResponse
import and5.abrar.e_commerce.network.ApiService
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val apiService: ApiService){
    fun postUser(email :String, password: String, fullname : String, liveDataRegister: MutableLiveData<PostUserResponse>){
        val retrofit : Call<PostUserResponse> = apiService.postRegister(email, password, fullname)
        retrofit.enqueue(object : Callback<PostUserResponse> {
            override fun onResponse(
                call: Call<PostUserResponse>,
                response: Response<PostUserResponse>
            ) {
                if (response.isSuccessful){
                    liveDataRegister.postValue(response.body())
                } else {
                    liveDataRegister.postValue(null)
                }
            }
            override fun onFailure(call: Call<PostUserResponse>, t: Throwable) {
                liveDataRegister.postValue(null)
            }
        })
    }
}
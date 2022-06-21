//package and5.abrar.e_commerce.repository
//
//import and5.abrar.e_commerce.model.register.PostUserRegister
//import and5.abrar.e_commerce.model.register.RequestPost
//import and5.abrar.e_commerce.network.ApiService
//import androidx.lifecycle.MutableLiveData
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import javax.inject.Inject
//
//class RegisterRepository @Inject constructor(private val apiService: ApiService){
//    fun postUser(address: String, city: String, email: String, fullName: String, imageUrl: String, password: String, phoneNumber: Long, liveDataRegister: MutableLiveData<PostUserRegister>){
//        val retrofit : Call<PostUserRegister> = apiService.register(RequestPost(address, city, email, fullName, imageUrl, password, phoneNumber))
//        retrofit.enqueue(object : Callback<PostUserRegister> {
//            override fun onResponse(
//                call: Call<PostUserRegister>,
//                response: Response<PostUserRegister>
//            ) {
//                if (response.isSuccessful){
//                    liveDataRegister.postValue(response.body())
//                } else {
//                    liveDataRegister.postValue(null)
//                }
//            }
//
//            override fun onFailure(call: Call<PostUserRegister>, t: Throwable) {
//                liveDataRegister.postValue(null)
//            }
//
//        })
//    }
//}
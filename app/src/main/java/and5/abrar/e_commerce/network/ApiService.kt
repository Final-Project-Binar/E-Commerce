package and5.abrar.e_commerce.network

import and5.abrar.e_commerce.model.login.GetLogin
import and5.abrar.e_commerce.model.login.LoginRequest
import and5.abrar.e_commerce.model.login.LoginResponse
//import and5.abrar.e_commerce.model.register.PostUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

//    @POST("auth/register")
//    @FormUrlEncoded
//    fun postRegister(
//        @Field("full_name") fullName : String,
//        @Field("email") email : String,
//        @Field("password") password : String
//    ) : Call<PostUserResponse>

}
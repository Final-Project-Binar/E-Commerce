package and5.abrar.e_commerce.network

import and5.abrar.e_commerce.model.login.LoginRequest
import and5.abrar.e_commerce.model.login.LoginResponse
import and5.abrar.e_commerce.model.notifikasi.GetNotifikasiItem
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.model.produkseller.GetDataProductSellerItem
import and5.abrar.e_commerce.model.produkseller.GetUserResponse
import and5.abrar.e_commerce.model.register.PostUserRegister
import and5.abrar.e_commerce.model.register.RequestPost
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("buyer/product")
    suspend fun getBuyerProduct(): List<GetBuyerProductItem>

    @GET("notification")
    fun getNotif(
        @Header("access_token") token: String
    ): Call<List<GetNotifikasiItem>>

    @GET("auth/user")
    fun getSellerData(
        @Header("Authorization") token: String
    ): Call<GetUserResponse>

    @GET("seller/product")
    fun getProductSeller(
        @Header("Authorization") token : String
    ) : Call<List<GetDataProductSellerItem>>

    @POST("auth/register")
    fun register(@Body requestPost: RequestPost): Call<PostUserRegister>



}
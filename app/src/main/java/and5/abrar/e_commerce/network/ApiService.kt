package and5.abrar.e_commerce.network

import and5.abrar.e_commerce.model.login.GetLogin
import and5.abrar.e_commerce.model.login.LoginRequest
import and5.abrar.e_commerce.model.login.LoginResponse
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.model.produkseller.GetProdukSellerItem
import and5.abrar.e_commerce.model.register.PostUserRegister
import and5.abrar.e_commerce.model.register.RequestPost
//import and5.abrar.e_commerce.model.register.PostUserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun register(@Body requestPost: RequestPost): Call<PostUserRegister>

    @GET("buyer/product")
    suspend fun getBuyerProduct(): List<GetBuyerProductItem>

    @GET("seller/product")
    suspend fun getSellerProduct(
        @Header("Authorization") token : String,
        @Query("status") status: String = "available"
    ): List<GetProdukSellerItem>

}
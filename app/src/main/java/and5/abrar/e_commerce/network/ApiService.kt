package and5.abrar.e_commerce.network

import and5.abrar.e_commerce.model.login.LoginRequest
import and5.abrar.e_commerce.model.login.LoginResponse
import and5.abrar.e_commerce.model.notifikasi.GetNotifikasiItem
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.model.produkseller.GetProdukSellerItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("buyer/product")
    suspend fun getBuyerProduct(): List<GetBuyerProductItem>

    @GET("seller/product")
    suspend fun getSellerProduct(
        @Header("Authorization") token : String,
        @Query("status") status: String = "available"
    ): List<GetProdukSellerItem>

    @GET("notification")
    fun getNotif(): Call<GetNotifikasiItem>

}
package and5.abrar.e_commerce.network

import and5.abrar.e_commerce.model.login.LoginRequest
import and5.abrar.e_commerce.model.login.LoginResponse
import and5.abrar.e_commerce.model.login.PostLoginUserResponse
import and5.abrar.e_commerce.model.notifikasi.GetNotifikasiItem
import and5.abrar.e_commerce.model.orderbuyer.PostBuyerOrder
import and5.abrar.e_commerce.model.orderbuyer.PostBuyerOrderResponseItem
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.model.produkseller.GetDataProductSellerItem
import and5.abrar.e_commerce.model.produkseller.GetUserResponse
import and5.abrar.e_commerce.model.produkseller.PostSellerProduct
import and5.abrar.e_commerce.model.register.PostUserRegister
import and5.abrar.e_commerce.model.register.RequestPost
import and5.abrar.e_commerce.model.user.GetUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface ApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("buyer/product")
    suspend fun getBuyerProduct(
        @Query("status") status: String = "available"
    ): List<GetBuyerProductItem>

    @POST("buyer/order")
    fun updateBidPrice(
        @Header("access_token") token: String,
        @Body reqBidPrice: PostBuyerOrder
    ): Call<PostBuyerOrderResponseItem>

    @GET("buyer/product")
    fun getBuyerProductSearch(
    @Query("search") search : String
    ): Call<List<GetBuyerProductItem>>

    @GET("notification")
    fun getNotif(
        @Header("access_token") token: String
    ): Call<List<GetNotifikasiItem>>

    @PATCH("notification")
    fun patchNotif(
        @Header("access_token") token: String,
        @Path("id") id : Int
    ): Call<List<GetNotifikasiItem>>

    @GET("auth/user")
    fun getSellerData(
        @Header("access_token") token: String
    ): Call<GetUserResponse>

    @GET("seller/product")
    fun getProductSeller(
        @Header("access_token") token : String
    ) : Call<List<GetDataProductSellerItem>>

    @POST("auth/register")
    fun register(@Body requestPost: RequestPost): Call<PostUserRegister>

    @POST("auth/register")
    @FormUrlEncoded
    fun registeruser(
        @Field("email") email: String,
        @Field("full_name") full_name: String,
        @Field("password") password: String
    ): Call<PostUserRegister>

    @PUT("auth/user")
    @Multipart
    fun profileuser(
        @Header("access_token") token : String,
        @Part ("full_name") fname : RequestBody,
        @Part ("phone_number") pnumber: RequestBody,
        @Part ("address") address : RequestBody,
        @Part ("city") city : RequestBody,
        @Part image :MultipartBody.Part
    ) : Call<GetUser>

    @POST("seller/product")
    @Multipart
    fun tambahproduct(
        @Header("access_token") token : String,
        @Part("name") nama : RequestBody,
        @Part("description") desc : RequestBody,
        @Part("base_price") harga : RequestBody,
        @Part("category_ids") category: RequestBody,
        @Part("location ") lokasi : RequestBody,
        @Part image :MultipartBody.Part
    ):Call<PostSellerProduct>
}
@file:Suppress("FunctionName")

package and5.abrar.e_commerce.network

import and5.abrar.e_commerce.model.banner.GetBannerItem
import and5.abrar.e_commerce.model.category.GetCategorySellerItem
import and5.abrar.e_commerce.model.login.LoginRequest
import and5.abrar.e_commerce.model.login.LoginResponse
import and5.abrar.e_commerce.model.notifikasi.GetNotifikasiItem
import and5.abrar.e_commerce.model.orderbuyer.PostBuyerOrder
import and5.abrar.e_commerce.model.orderbuyer.PostBuyerOrderResponseItem
import and5.abrar.e_commerce.model.orderseller.GetOrderSellerItem
import and5.abrar.e_commerce.model.ordersellerid.GetSellerOrderId
import and5.abrar.e_commerce.model.ordersellerid.PathSellerOrderId
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductResponse
import and5.abrar.e_commerce.model.produkseller.*
import and5.abrar.e_commerce.model.register.PostUserRegister
import and5.abrar.e_commerce.model.user.GetUser
import and5.abrar.e_commerce.model.user.GetUserProfile
import and5.abrar.e_commerce.model.wishlist.WishListBuyer
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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

    @GET("buyer/product/{id}")
     fun getdetailproduct(
        @Path("id") id : Int
    ): Call<GetBuyerProductResponse>

    @GET("notification")
    fun getNotif(
        @Header("access_token") token: String
    ): Call<List<GetNotifikasiItem>>

    @PATCH("notification/{id}")
    fun patchNotif(
        @Header("access_token") token: String,
        @Path("id") id : Int
    ): Call<GetNotifikasiItem>

    @GET("auth/user")
    fun getSellerData(
        @Header("access_token") token: String
    ): Call<GetUserResponse>

    @GET("seller/product")
    fun getProductSeller(
        @Query("status") status: String = "available",
        @Header("access_token") token : String,
    ) : Call<List<GetDataProductSellerItem>>


    @POST("auth/register")
    @Multipart
    fun registeruser(
        @Part("email") email: RequestBody,
        @Part("full_name") full_name: RequestBody,
        @Part("password") password: RequestBody,
        @Part("phone_number") phone : RequestBody,
        @Part("address") alamat : RequestBody,
        @Part("city") city : RequestBody
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

    @PUT("auth/user")
    @Multipart
    fun profileusernogambar(
        @Header("access_token") token : String,
        @Part ("full_name") fname : RequestBody,
        @Part ("phone_number") pnumber: RequestBody,
        @Part ("address") address : RequestBody,
        @Part ("city") city : RequestBody,
    ) : Call<GetUser>

    @POST("seller/product")
    @Multipart
    fun tambahproduct(
        @Header("access_token") token : String,
        @Part("name") nama : RequestBody,
        @Part("description") desc : RequestBody,
        @Part("base_price") harga : RequestBody,
        @Part("category_ids") category: RequestBody,
        @Part("location") lokasi : RequestBody,
        @Part image :MultipartBody.Part
    ):Call<PostSellerProduct>

    @GET("seller/product/{id}")
    fun getProductid(
        @Header("access_token") token: String,
        @Path("id") id: Int
    ): Call<GetDataProductSellerItem>

    @PUT("seller/product/{id}")
    @Multipart
    fun updateProductimage(
        @Header("access_token") token : String,
        @Path("id") id :Int,
        @Part("name") nama : RequestBody,
        @Part("description") desc : RequestBody,
        @Part("base_price") harga : RequestBody,
        @Part("category_ids") category: RequestBody,
        @Part("location") lokasi : RequestBody,
        @Part image :MultipartBody.Part
    ): Call<PutSellerProduct>

    @PUT("seller/product/{id}")
    @Multipart
    fun updateProductimagenocategory(
        @Header("access_token") token : String,
        @Path("id") id :Int,
        @Part("name") nama : RequestBody,
        @Part("description") desc : RequestBody,
        @Part("base_price") harga : RequestBody,
        @Part("location") lokasi : RequestBody,
        @Part image :MultipartBody.Part
    ): Call<PutSellerProduct>

    @PUT("seller/product/{id}")
    @Multipart
    fun updateProductnoImage(
        @Header("access_token") token : String,
        @Path("id") id :Int,
        @Part("name") nama : RequestBody,
        @Part("description") desc : RequestBody,
        @Part("base_price") harga : RequestBody,
        @Part("category_ids") category: RequestBody,
        @Part("location") lokasi : RequestBody
    ): Call<PutSellerProduct>

    @PUT("seller/product/{id}")
    @Multipart
    fun updateProductnoImagenocategory(
        @Header("access_token") token : String,
        @Path("id") id :Int,
        @Part("name") nama : RequestBody,
        @Part("description") desc : RequestBody,
        @Part("base_price") harga : RequestBody,
        @Part("location") lokasi : RequestBody
    ): Call<PutSellerProduct>

    @DELETE("seller/product/{id}")
    fun deleteProduct(
        @Header("access_token") token: String,
        @Path("id") id: Int
    ): Call<DeleteSellerProduct>

    @GET("seller/category")
    suspend fun GetCategory() : List<GetCategorySellerItem>

    @GET("seller/order")
    fun getOrderSeller(
        @Query("status") status: String = "pending",
        @Query("status") status1: String = "terima",
        @Header("access_token") token : String,
    ) : Call<List<GetOrderSellerItem>>

    @GET("seller/order")
    fun getOrderSellerStatus(
        @Header("access_token") token : String,
        @Query("status") status: String = "accepted"
    ) : Call<List<GetOrderSellerItem>>

    @GET("seller/banner")
    fun getBannerItem() : Call<List<GetBannerItem>>

    @GET("seller/order/{id}")
    fun getInfoPenawar(
        @Header("access_token") token : String,
        @Path("id") id : Int
    ) : Call<GetSellerOrderId>


    @PATCH("seller/order/{id}")
    @Multipart
    fun patchStatus(
        @Header("access_token") token : String,
        @Path("id") id : Int,
        @Part ("status") status: RequestBody
    ) : Call<PathSellerOrderId>

    @GET("auth/user")
    fun getProfileData(
        @Header("access_token") token: String
    ): Call<GetUserProfile>

    @PUT("auth/change-password")
    @Multipart
    fun changePassword(
        @Header("access_token") token: String,
        @Part ("current_password") current_password: RequestBody,
        @Part ("new_password") new_password: RequestBody,
        @Part ("confirm_password") confirm_password: RequestBody
    ): Call<PostUserRegister>

    @POST("buyer/wishlist")
    @Multipart
    fun postWishList(
        @Header("access_token") token: String,
        @Part("product_id") id : Int
    ) : Call<WishListBuyer>

    @GET("buyer/wishlist")
    fun getWishList(
        @Header("access_token") token: String
    ) : Call<List<WishListBuyer>>

}
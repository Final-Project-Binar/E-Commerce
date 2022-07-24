package and5.abrar.e_commerce.model.wishlist


import com.google.gson.annotations.SerializedName

data class GetWishListItemItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Product")
    val product: ProductX,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("user_id")
    val userId: Int
)
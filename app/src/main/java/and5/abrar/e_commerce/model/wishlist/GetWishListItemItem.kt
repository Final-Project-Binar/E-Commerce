package and5.abrar.e_commerce.model.wishlist


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetWishListItemItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Product")
    val product: ProductX?,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("user_id")
    val userId: Int
) : Parcelable
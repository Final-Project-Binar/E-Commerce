package and5.abrar.e_commerce.model.wishlist


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WishListBuyer(
    @SerializedName("name")
    val name: String,
    @SerializedName("Product")
    val product: Product
): Parcelable
package and5.abrar.e_commerce.model.orderbuyer


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostBuyerOrderResponseItem(
    @SerializedName("buyer_id")
    val buyerId: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_id")
    val product_id: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("image_product")
    val image : String,
    @SerializedName("base_price")
    val base_price : Int,
    @SerializedName("product_name")
    val product_name : String
): Parcelable
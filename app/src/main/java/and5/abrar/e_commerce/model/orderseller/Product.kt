package and5.abrar.e_commerce.model.orderseller


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class Product(
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("image_name")
    val imageName: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("User")
    val user: User,
    @SerializedName("user_id")
    val userId: Int
): Serializable
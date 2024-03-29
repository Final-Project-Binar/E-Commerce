package and5.abrar.e_commerce.model.produkbuyer


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetBuyerProductResponse(
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("Categories")
    val categories: List<CategoryX>,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
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
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("User")
    val user: UserX,
    @SerializedName("user_id")
    val userId: Int
):Serializable
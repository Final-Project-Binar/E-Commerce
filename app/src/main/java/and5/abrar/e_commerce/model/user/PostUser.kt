package and5.abrar.e_commerce.model.user


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PostUser(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("updatedAt")
    val updatedAt: String
):Serializable
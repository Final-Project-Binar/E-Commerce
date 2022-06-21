package and5.abrar.e_commerce.model.register

import com.google.gson.annotations.SerializedName

data class RequestPost(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: Long
)

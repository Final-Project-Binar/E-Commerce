package and5.abrar.e_commerce.model.produkbuyer


import com.google.gson.annotations.SerializedName

data class UserX(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("phone_number")
    val phoneNumber: String
)
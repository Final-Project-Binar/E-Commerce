package and5.abrar.e_commerce.model.category


import com.google.gson.annotations.SerializedName

data class GetCategorySellerItem(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
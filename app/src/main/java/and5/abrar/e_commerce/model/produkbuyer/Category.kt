package and5.abrar.e_commerce.model.produkbuyer


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
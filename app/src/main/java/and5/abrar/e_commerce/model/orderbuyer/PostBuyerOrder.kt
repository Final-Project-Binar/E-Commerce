package and5.abrar.e_commerce.model.orderbuyer

import com.google.gson.annotations.SerializedName

data class PostBuyerOrder(
    @SerializedName("product_id")
    val product_id: Int,
    @SerializedName("bid_price")
    val bid_price: Int
)

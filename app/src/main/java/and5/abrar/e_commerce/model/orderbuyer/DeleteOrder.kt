package and5.abrar.e_commerce.model.orderbuyer

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeleteOrder(
    @SerializedName("name")
    val name : String,
    @SerializedName("message")
    val message : String
): Parcelable

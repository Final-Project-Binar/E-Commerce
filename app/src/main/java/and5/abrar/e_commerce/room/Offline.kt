package and5.abrar.e_commerce.room

import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import android.content.Context
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import retrofit2.http.Body


@Parcelize
@Entity(tableName = "offline_data")
data class Offline (
    @PrimaryKey(autoGenerate = true) val id :Int?,
    val image : String?,
    val nama : String?,
    val category : String?,
    val harga : String?
    ):Parcelable



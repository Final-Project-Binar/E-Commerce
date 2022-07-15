package and5.abrar.e_commerce.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class Offline (
    @PrimaryKey(autoGenerate = true) val id :Int?,
    val image : String?,
    val nama : String?,
    val category : String?,
    val harga : String?
    ):Parcelable



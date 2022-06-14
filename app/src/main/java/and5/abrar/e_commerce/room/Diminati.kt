package and5.abrar.e_commerce.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Diminati(
    @PrimaryKey(autoGenerate = true)
    val id : Int?,
):Parcelable

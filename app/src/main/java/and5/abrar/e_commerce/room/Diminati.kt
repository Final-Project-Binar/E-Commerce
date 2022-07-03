package and5.abrar.e_commerce.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Diminati(
    @PrimaryKey(autoGenerate = false)
    val id : Int?,
    @ColumnInfo(name = "image")
    val image : String,
    @ColumnInfo(name = "namaProduct")
    val namaProdcut : String,
    @ColumnInfo(name = "category")
    val category : String,
    @ColumnInfo(name = "harga")
    val harga : String,
    @ColumnInfo(name = "harga tawar")
    val tawar : String,
    @ColumnInfo(name = "deskripsi")
    val deskripsi : String,
    @ColumnInfo(name = "status")
    val status : String,
    @ColumnInfo(name = "tanggal")
    val tanggal : String
):Parcelable

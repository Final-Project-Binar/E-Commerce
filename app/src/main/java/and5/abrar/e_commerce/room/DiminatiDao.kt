package and5.abrar.e_commerce.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DiminatiDao {
    @Insert
    fun addDiminati(diminati: Diminati) : Long

    @Query("SELECT * FROM Diminati")
    fun getDiminati(): List<Diminati>
}
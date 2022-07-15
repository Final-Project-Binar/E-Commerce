package and5.abrar.e_commerce.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OfflineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertOffline(offline: Offline)

    @Query("SELECT * FROM Offline")
    fun getOffline(): List<Offline>

    @Query("DELETE FROM Offline")
    suspend fun deleteoffline()

}
package and5.abrar.e_commerce.room

import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OfflineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertOffline(offline: Offline)

    @Query("SELECT * FROM offline_data")
    fun getOffline(): List<Offline>

    @Query("DELETE FROM offline_data")
    suspend fun deleteoffline()

}
package and5.abrar.e_commerce.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Offline::class], version = 1, exportSchema = false)
abstract class OfflineDatabase : RoomDatabase() {
    abstract fun offlineDao() : OfflineDao

    companion object{
        private var INSTANCE : OfflineDatabase? = null
        fun getInstance(context: Context): OfflineDatabase?{
            if (INSTANCE == null){
                synchronized(OfflineDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        OfflineDatabase::class.java, "offline.db"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}
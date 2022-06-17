package and5.abrar.e_commerce.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Diminati::class], version = 1)
abstract class DiminatiDatabase:RoomDatabase() {
    abstract fun diminatiDao(): DiminatiDao

    companion object{
        private var INSTANCE : DiminatiDatabase? = null

        fun getinstance(context: Context): DiminatiDatabase?{
            if(INSTANCE == null){
                synchronized(DiminatiDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, DiminatiDatabase::class.java,"diminati.db").build()
                }
            }
            return INSTANCE
        }
    }
}
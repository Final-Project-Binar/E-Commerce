package and5.abrar.e_commerce.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Diminati::class], version = 1)
abstract class DiminatiDatabase:RoomDatabase() {
}
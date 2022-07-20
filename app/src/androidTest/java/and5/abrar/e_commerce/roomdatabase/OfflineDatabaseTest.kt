package and5.abrar.e_commerce.roomdatabase

import and5.abrar.e_commerce.room.OfflineDao
import and5.abrar.e_commerce.room.OfflineDatabase
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OfflineDatabaseTest : TestCase(){
    private lateinit var db : OfflineDatabase
    private lateinit var dao : OfflineDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, OfflineDatabase::class.java).build()
        dao = db.offlineDao()
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun testDataRoom(){
        dao.getOffline()
    }
}
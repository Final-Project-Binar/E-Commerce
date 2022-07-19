@file:Suppress("LocalVariableName", "DEPRECATION")

package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.room.OfflineDatabase
import and5.abrar.e_commerce.view.AkunSayaActivity
import and5.abrar.e_commerce.view.HomeActivity
import and5.abrar.e_commerce.view.adapter.AdapterHomeOffline
import and5.abrar.e_commerce.view.buyer.NotifikasiBuyerActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_daftar_jual_dua_diminati_kosong.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@AndroidEntryPoint
class OfflineActvty : AppCompatActivity() {

    private var offlineDB : OfflineDatabase? = null
    private lateinit var adapterHomeOffline: AdapterHomeOffline

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_jual_dua_diminati_kosong)
        offlineDB = OfflineDatabase.getInstance(this)
        testrv.layoutManager = GridLayoutManager(this,2)
           GlobalScope.launch {
               val Offlinelist = offlineDB?.offlineDao()?.getOffline()
               runOnUiThread {
                   if (Offlinelist?.size != null){
                       Offlinelist.let {
                           adapterHomeOffline = AdapterHomeOffline(it)
                           testrv.adapter = adapterHomeOffline
                       }
                   }
               }
           }
    }
}
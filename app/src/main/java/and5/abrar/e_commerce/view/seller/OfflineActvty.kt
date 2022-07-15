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
    private val bottomNavigasi = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.notifikasi -> {
                startActivity(Intent(this, NotifikasiBuyerActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.dashboard -> {
                startActivity(Intent(this, HomeActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.jual -> {
                startActivity(Intent(this, LengkapiDetailProductActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.akun -> {
                startActivity(Intent(this, AkunSayaActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.daftar_jual -> {
                startActivity(Intent(this, DaftarJualActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_jual_dua_diminati_kosong)
        val botnav = findViewById<BottomNavigationView>(R.id.navigation)
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi)
        offlineDB = OfflineDatabase.getInstance(this)
//            val viewModel = ViewModelProvider(this)[ViewModelHome::class.java]
//            viewModel.getOffline()
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
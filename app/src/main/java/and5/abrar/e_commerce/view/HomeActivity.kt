package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.view.adapter.AdapterHome
import and5.abrar.e_commerce.view.buyer.NotifikasiBuyerActivity
import and5.abrar.e_commerce.view.seller.AddProductSellerActivity
import and5.abrar.e_commerce.view.seller.DaftarJualActivity
import and5.abrar.e_commerce.viewmodel.ViewModelHome
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*



@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
 private lateinit var  adapterHome: AdapterHome
    private lateinit var  userManager: UserManager

    private val bottomNavigasi = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.notifikasi -> {
                startActivity(Intent(this, NotifikasiBuyerActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.dashboard -> {
                Toast.makeText(this, "Kamu Sedang Berada Di Home", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener false
            }
            R.id.jual -> {
                startActivity(Intent(this, AddProductSellerActivity::class.java))
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
        setContentView(R.layout.activity_home)
        userManager = UserManager(this)
        val botnav = findViewById<BottomNavigationView>(R.id.navigation)
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi)
        iniviewmodel()
    }

    fun iniviewmodel(){
        adapterHome = AdapterHome{
            val pindahdata = Intent(applicationContext, AddProductSellerActivity::class.java)
            pindahdata.putExtra("detailproduk",it)
            startActivity(pindahdata)
        }
        rv_homeProduk.layoutManager=GridLayoutManager(this,2)
        rv_homeProduk.adapter=adapterHome
        val viewModel = ViewModelProvider(this).get(ViewModelHome::class.java)
        viewModel.product.observe(this){
          if (it != null){
              adapterHome.setProduk(it)
              adapterHome.notifyDataSetChanged()
          }

        }

    }
}
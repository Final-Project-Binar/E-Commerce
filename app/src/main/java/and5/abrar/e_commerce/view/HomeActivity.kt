package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.network.ApiClient2
import and5.abrar.e_commerce.view.adapter.AdapterHome
import and5.abrar.e_commerce.view.seller.AddProductSellerActivity
import and5.abrar.e_commerce.view.seller.DaftarJualActivity
import and5.abrar.e_commerce.view.seller.LengkapiDetailProductActivity
import and5.abrar.e_commerce.view.seller.NotifikasiSellerActivity
import and5.abrar.e_commerce.viewmodel.ViewModelHome
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*



@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
 private lateinit var  adapterHome: AdapterHome
    private lateinit var  userManager: UserManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        userManager = UserManager(this)
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
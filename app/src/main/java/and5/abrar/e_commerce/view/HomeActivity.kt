package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.view.adapter.AdapterBanner
import and5.abrar.e_commerce.view.adapter.AdapterHome
import and5.abrar.e_commerce.view.buyer.AddProductBuyerActivity
import and5.abrar.e_commerce.view.buyer.NotifikasiBuyerActivity
import and5.abrar.e_commerce.view.seller.AddProductSellerActivity
import and5.abrar.e_commerce.view.seller.DaftarJualActivity
import and5.abrar.e_commerce.view.seller.LengkapiDetailProductActivity
import and5.abrar.e_commerce.viewmodel.ViewModelBanner
import and5.abrar.e_commerce.viewmodel.ViewModelHome
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var  adapterHome: AdapterHome
    private lateinit var adapterBanner: AdapterBanner
    lateinit var viewPager: ViewPager
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
        setContentView(R.layout.activity_home)
        userManager = UserManager(this)
        val botnav = findViewById<BottomNavigationView>(R.id.navigation)
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi)
        button_Fashion.isClickable = false
        button_semua.isClickable = true
        button_Other.isClickable = false
        button_Electronic.isClickable = false
        search()
        iniviewmodel()
        ctgyfashion()
        ctgyhome()
        ctgyelektronik()
        ctgyother()
        bannerSeller()
    }

    fun iniviewmodel(){
        adapterHome = AdapterHome {
            val clickedProduct = Bundle()
            clickedProduct.putSerializable("detailproduk",it)
            val pindah = Intent(this, AddProductBuyerActivity::class.java)
                pindah.putExtras(clickedProduct)
            startActivity(pindah)
        }
        rv_homeProduk.layoutManager=GridLayoutManager(this,2)
        rv_homeProduk.adapter=adapterHome
        val viewModel = ViewModelProvider(this)[ViewModelHome::class.java]
        viewModel.searchproduct("")
        viewModel.product.observe(this){
          if (it != null){
              adapterHome.setProduk(it)
              adapterHome.notifyDataSetChanged()
          }
        }

    }
    private fun ctgyhome(){
        button_semua.setOnClickListener {
            button_Fashion.isSelected = false
            button_semua.isSelected = true
            button_Other.isSelected = false
            button_Electronic.isSelected = false
            iniviewmodel()
        }
    }
    private fun ctgyfashion(){
        button_Fashion.setOnClickListener {
            button_Fashion.isSelected = true
            button_semua.isSelected = false
            button_Other.isSelected = false
            button_Electronic.isSelected = false
            val viewModel = ViewModelProvider(this).get(ViewModelHome::class.java)
            viewModel.searchproduct("")
            viewModel.product.observe(this){
                val listdataproduct : MutableList<GetBuyerProductItem> = mutableListOf()
                if (it.isNotEmpty()){
                    for (i in it.indices){
                        for (j in it[i].categories.indices){
                            val hasilcetgory = it[i].categories[j].name
                            if(hasilcetgory == "Pakaian Pria" || hasilcetgory == "Sepatu Pria" || hasilcetgory == "Tas Pria" || hasilcetgory == "Aksesoris Fashion" || hasilcetgory == "Fashion Muslim" ){
                                listdataproduct += it[i]
                            }
                        }
                    }
                    adapterHome.setProduk(listdataproduct)
                    adapterHome.notifyDataSetChanged()
                }
            }
        }
    }
    private fun ctgyelektronik() {
        button_Electronic.setOnClickListener {
            button_Fashion.isSelected = false
            button_semua.isSelected = false
            button_Other.isSelected = false
            button_Electronic.isSelected = true

            val viewModel = ViewModelProvider(this).get(ViewModelHome::class.java)
            viewModel.searchproduct("")
            viewModel.product.observe(this){
                val listdataproduct : MutableList<GetBuyerProductItem> = mutableListOf()
                if (it.isNotEmpty()){
                    for (i in it.indices){
                        for (j in it[i].categories.indices){
                            val hasilcetgory = it[i].categories[j].name
                            if(hasilcetgory == "Elektronik" ||
                                hasilcetgory == "Komputer dan Aksesoris" ||
                                hasilcetgory == "Handphone dan Aksesoris" ||
                                hasilcetgory == "Voucher" ||
                                    hasilcetgory == "Pakaian Wanita" ||
                                    hasilcetgory == "Fashion Muslim" ||
                                    hasilcetgory == "Fashion bayi dan Anak" ||
                                    hasilcetgory == "Sepatu Wanita" ||
                                    hasilcetgory == "Tas Wanita"){
                                listdataproduct += it[i]
                            }
                        }
                    }
                    adapterHome.setProduk(listdataproduct)
                    adapterHome.notifyDataSetChanged()
                }
            }
        }
    }

    private fun ctgyother(){
        button_Other.setOnClickListener {
            button_Fashion.isSelected = false
            button_semua.isSelected = false
            button_Other.isSelected = true
            button_Electronic.isSelected = false

            val viewModel = ViewModelProvider(this).get(ViewModelHome::class.java)
            viewModel.searchproduct("")
            viewModel.product.observe(this){
                val listdataproduct : MutableList<GetBuyerProductItem> = mutableListOf()
                if (it.isNotEmpty()){
                    for (i in it.indices){
                        for (j in it[i].categories.indices){
                            val hasilcetgory = it[i].categories[j].name
                            if(hasilcetgory == "Kesehatan" ||
                                hasilcetgory == "Hobi dan Koleksi" ||
                                hasilcetgory == "Makanan dan Minuman" ||
                                hasilcetgory == "Perawatan dan Kecantikan" ||
                                hasilcetgory == "Perlengkapan Rumah" ||
                                hasilcetgory == "Ibu dan Bayi" ||
                                hasilcetgory == "Otomotif" ||
                                hasilcetgory == "Olahraga dan Outdoor" ||
                                hasilcetgory == "Buku dan Alat Tulis" ||
                                hasilcetgory == "Souvenir dan Pesta"  ||
                                hasilcetgory == "Fotografi"    ){
                                listdataproduct += it[i]
                            }
                        }
                    }
                    adapterHome.setProduk(listdataproduct)
                    adapterHome.notifyDataSetChanged()
                }
            }
        }
    }

    private fun search(){
        Handler(Looper.getMainLooper()).postDelayed({
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {

                            val viewModel =
                                ViewModelProvider(this@HomeActivity)[ViewModelHome::class.java]
                            viewModel.searchproduct(query!!)
                            viewModel.product.observe(this@HomeActivity) {
                                if (it != null) {
                                    adapterHome.setProduk(it)
                                    adapterHome.notifyDataSetChanged()
                                }
                            }

                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        iniviewmodel()
                        return false
                    }
                })
        },10000)
                adapterHome = AdapterHome{
                    val pindahdata = Intent(applicationContext, AddProductSellerActivity::class.java)
                    pindahdata.putExtra("detailproduk",it)
                    startActivity(pindahdata)
                }
                rv_homeProduk.layoutManager=GridLayoutManager(this,2)
                rv_homeProduk.adapter=adapterHome
            }


    private fun bannerSeller(){
        viewPager = findViewById(R.id.imageView)

        val viewModelBanner = ViewModelProvider(this)[ViewModelBanner::class.java]
        viewModelBanner.sellerBanner.observe(this){
            adapterBanner = AdapterBanner(this, it)
            imageView.adapter = adapterBanner
        }
        
        viewModelBanner.getBanner()

    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}
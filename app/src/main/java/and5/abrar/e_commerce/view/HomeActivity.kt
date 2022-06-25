package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.view.adapter.AdapterHome
import and5.abrar.e_commerce.view.buyer.NotifikasiBuyerActivity
import and5.abrar.e_commerce.view.seller.AddProductSellerActivity
import and5.abrar.e_commerce.view.seller.DaftarJualActivity
import and5.abrar.e_commerce.viewmodel.ViewModelHome
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


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
        search()
        iniviewmodel()
        ctgyhoby()
        ctgyhome()
        ctgyelektronik()
        ctgykendaraan()
    }

    fun iniviewmodel(){
        adapterHome = AdapterHome() {
            val clickedProduct = Bundle()
            clickedProduct.putSerializable("detailproduk",it)
            val pindah = Intent(this, AddProductSellerActivity::class.java)
                .putExtras(clickedProduct)
            startActivity(pindah)
//            val pindahdata = Intent(applicationContext, AddProductSellerActivity::class.java)
//            pindahdata.putExtra("detailproduk",it)
//            startActivity(pindahdata)
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
    private fun ctgyhome(){
        button_semua.setOnClickListener {
            button_Hoby.isSelected = false
            button_semua.isSelected = true
            button_Kendaraan.isSelected = false
            button_Electronic.isSelected = false
            iniviewmodel()
        }
    }
    private fun ctgyhoby(){
        button_Hoby.setOnClickListener {
            button_Hoby.isSelected = true
            button_semua.isSelected = false
            button_Kendaraan.isSelected = false
            button_Electronic.isSelected = false

            val viewModel = ViewModelProvider(this).get(ViewModelHome::class.java)
            viewModel.product.observe(this){
                val listdataproduct : MutableList<GetBuyerProductItem> = mutableListOf()
                if (it.isNotEmpty()){
                    for (i in it.indices){
                        for (j in it[i].categories.indices){
                            val hasilcetgory = it[i].categories[j].name
                            if(hasilcetgory == "hobi" || hasilcetgory == "hoby" || hasilcetgory == "hobbi"){
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
            button_Hoby.isSelected = false
            button_semua.isSelected = false
            button_Kendaraan.isSelected = false
            button_Electronic.isSelected = true

            val viewModel = ViewModelProvider(this).get(ViewModelHome::class.java)
            viewModel.product.observe(this){
                val listdataproduct : MutableList<GetBuyerProductItem> = mutableListOf()
                if (it.isNotEmpty()){
                    for (i in it.indices){
                        for (j in it[i].categories.indices){
                            val hasilcetgory = it[i].categories[j].name
                            if(hasilcetgory == "Electronic" || hasilcetgory == "electronic" || hasilcetgory == "elektonik"){
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

    private fun ctgykendaraan(){
        button_Kendaraan.setOnClickListener {
            button_Hoby.isSelected = false
            button_semua.isSelected = false
            button_Kendaraan.isSelected = true
            button_Electronic.isSelected = false

            val viewModel = ViewModelProvider(this).get(ViewModelHome::class.java)
            viewModel.product.observe(this){
                val listdataproduct : MutableList<GetBuyerProductItem> = mutableListOf()
                if (it.isNotEmpty()){
                    for (i in it.indices){
                        for (j in it[i].categories.indices){
                            val hasilcetgory = it[i].categories[j].name
                            if(hasilcetgory == "kendaraan" || hasilcetgory == "electronic" || hasilcetgory == "elektonik"){
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
        searchView.setOnKeyListener(View.OnKeyListener { v, keyCode, event  ->
            if(keyCode == KeyEvent.KEYCODE_ENTER){
                val cari = searchView.toString()
                val viewModel = ViewModelProvider(this).get(ViewModelHome::class.java)
                viewModel.searchproduct(cari)
                adapterHome = AdapterHome{
                    val pindahdata = Intent(applicationContext, AddProductSellerActivity::class.java)
                    pindahdata.putExtra("detailproduk",it)
                    startActivity(pindahdata)
                }
                rv_homeProduk.layoutManager=GridLayoutManager(this,2)
                rv_homeProduk.adapter=adapterHome
                return@OnKeyListener true
            }
            false
        })
        }
}
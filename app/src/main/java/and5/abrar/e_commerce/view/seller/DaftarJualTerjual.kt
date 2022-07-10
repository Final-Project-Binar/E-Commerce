package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.network.ApiClient
import and5.abrar.e_commerce.view.AkunSayaActivity
import and5.abrar.e_commerce.view.HomeActivity
import and5.abrar.e_commerce.view.adapter.AdapterProductSeller
import and5.abrar.e_commerce.view.adapter.AdapterTerjual
import and5.abrar.e_commerce.view.buyer.NotifikasiBuyerActivity
import and5.abrar.e_commerce.viewmodel.ViewModelProductSeller
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_daftar_jual_seller.IV_penjual_product
import kotlinx.android.synthetic.main.activity_daftar_jual_seller.TV_kota_product
import kotlinx.android.synthetic.main.activity_daftar_jual_seller.TV_nama_product
import kotlinx.android.synthetic.main.activity_daftar_jual_terjual.*

@AndroidEntryPoint
class DaftarJualTerjual : AppCompatActivity() {
    private lateinit var apiClient: ApiClient
    private lateinit var adapter : AdapterTerjual
    private lateinit var  userManager: UserManager
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
                Toast.makeText(this, "Kamu Sedang Berada Di Daftar Jual", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener false
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_jual_terjual)
        userManager = UserManager(this)
        val botnav = findViewById<BottomNavigationView>(R.id.navigation)
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi)
        val viewModelSeller = ViewModelProvider(this)[ViewModelProductSeller::class.java]
        viewModelSeller.getorderstatus(userManager.fetchAuthToken().toString())
        cardView_diminatiSeller.setOnClickListener {
            startActivity(Intent(this, DaftarJualDiminatiSellerActivity::class.java))
        }
        cardView_productSeller.setOnClickListener {
            startActivity(Intent(this, DaftarJualActivity::class.java))
        }
        initView()
    }
    private fun initView(){
        val viewModelDataSeller = ViewModelProvider(this)[ViewModelProductSeller::class.java]
        viewModelDataSeller.getSeller(token = userManager.fetchAuthToken().toString())
        viewModelDataSeller.seller.observe(this){
            TV_nama_product.text = it.fullName
            TV_kota_product.text = it.city
            Glide.with(applicationContext).load(it.imageUrl).into(IV_penjual_product)
        }
        initRecyclerView()
    }

    private fun initRecyclerView(){
        userManager = UserManager(this)
        val viewModelProductSeller = ViewModelProvider(this)[ViewModelProductSeller::class.java]
        viewModelProductSeller.getorderstatus(token = userManager.fetchAuthToken().toString())
        adapter = AdapterTerjual(){
        }
        rvTerjual.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvTerjual.adapter = adapter
        viewModelProductSeller.dataorder.observe(this){
            if (it.isNotEmpty()){
                adapter.setDataOrder(it)
                adapter.notifyDataSetChanged()
            }
        }
    }
}
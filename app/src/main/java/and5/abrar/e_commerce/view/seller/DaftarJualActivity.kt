@file:Suppress("RemoveEmptyParenthesesFromLambdaCall", "DEPRECATION")

package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.view.AkunSayaActivity
import and5.abrar.e_commerce.view.HomeActivity
import and5.abrar.e_commerce.view.LoginActivity
import and5.abrar.e_commerce.view.adapter.AdapterProductSeller
import and5.abrar.e_commerce.view.buyer.NotifikasiBuyerActivity
import and5.abrar.e_commerce.viewmodel.ViewModelProductSeller
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_daftar_jual_seller.*
import kotlinx.android.synthetic.main.activity_daftar_jual_seller.daftarHistory
import kotlinx.android.synthetic.main.activity_daftar_jual_seller.daftar_jualEdit
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@AndroidEntryPoint
class DaftarJualActivity : AppCompatActivity() {

    private lateinit var adapter : AdapterProductSeller
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
                userManager.ceklogin.asLiveData().observe(this){
                    if (it == true){
                        startActivity(Intent(this, LengkapiDetailProductActivity::class.java))
                    } else {
                        Toast.makeText(applicationContext, "Anda Belum Login", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
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
        setContentView(R.layout.activity_daftar_jual_seller)
        userManager = UserManager(this)
        val botnav = findViewById<BottomNavigationView>(R.id.navigation)
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi)
        val viewModelSeller = ViewModelProvider(this)[ViewModelProductSeller::class.java]
        viewModelSeller.getSeller(userManager.fetchAuthToken().toString())
        initView()
        editSeller()
        addProduct()
        cardView_diminatiSeller.setOnClickListener {
            startActivity(Intent(this, DaftarJualDiminatiSellerActivity::class.java))
        }
        daftarTerjuall.setOnClickListener {
            startActivity(Intent(this,DaftarJualTerjual::class.java))
        }
        daftarHistory.setOnClickListener {
            startActivity(Intent(this,DaftarJualHistory::class.java))
        }

    }

     fun initView(){
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
        viewModelProductSeller.getAllSellerProduct("available", token = userManager.fetchAuthToken().toString())
        adapter = AdapterProductSeller(){
            val clickedproduct = Bundle()
            clickedproduct.putSerializable("detailorder",it)
            val pindah = Intent(this,EditProduct::class.java)
            pindah.putExtras(clickedproduct)
            startActivity(pindah)
        }
        rvProductSeller.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvProductSeller.adapter = adapter

        viewModelProductSeller.sellerProduct.observe(this) {
            if (it.isNotEmpty()){
                adapter.setDataProductSeller(it)
                adapter.notifyDataSetChanged()
            }

        }
    }

    private fun editSeller(){
        daftar_jualEdit.setOnClickListener {
            startActivity(Intent(this, AkunSayaActivity::class.java))
        }
    }
    private fun addProduct(){
        image_tambah_produk.setOnClickListener {
            startActivity(Intent(this, LengkapiDetailProductActivity::class.java))
        }
    }
}
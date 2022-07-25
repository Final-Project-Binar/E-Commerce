package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.view.AkunSayaActivity
import and5.abrar.e_commerce.view.HomeActivity
import and5.abrar.e_commerce.view.LoginActivity
import and5.abrar.e_commerce.view.adapter.AdapterDiminati
import and5.abrar.e_commerce.view.buyer.NotifikasiBuyerActivity
import and5.abrar.e_commerce.viewmodel.ViewModelProductSeller
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_daftar_jual_diminati_seller.*
import kotlinx.android.synthetic.main.activity_daftar_jual_diminati_seller.daftarHistory
import kotlinx.android.synthetic.main.activity_daftar_jual_diminati_seller.daftar_jualEdit
import kotlinx.android.synthetic.main.activity_daftar_jual_seller.*
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@Suppress("DEPRECATION")
@AndroidEntryPoint
class DaftarJualDiminatiSellerActivity : AppCompatActivity() {
    private lateinit var  userManager: UserManager
    private lateinit var adapter : AdapterDiminati
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
        setContentView(R.layout.activity_daftar_jual_diminati_seller)
        userManager = UserManager(this)
        val botnav = findViewById<BottomNavigationView>(R.id.navigation)
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi)
        val viewModelSeller = ViewModelProvider(this)[ViewModelProductSeller::class.java]
        viewModelSeller.getSeller(userManager.fetchAuthToken().toString())
        daftar_jual_product.setOnClickListener {
            startActivity(Intent(this,DaftarJualActivity::class.java))
        }
        daftarTerjual.setOnClickListener {
            startActivity(Intent(this,DaftarJualTerjual::class.java))
        }
        daftar_jualEdit.setOnClickListener {
            startActivity(Intent(this,AkunSayaActivity::class.java))
        }
        daftarHistory.setOnClickListener {
            startActivity(Intent(this,DaftarJualHistory::class.java))
        }
        initView()
    }

    private fun initView(){
        val viewModelDataSeller = ViewModelProvider(this)[ViewModelProductSeller::class.java]
        viewModelDataSeller.getSeller(token = userManager.fetchAuthToken().toString())
        viewModelDataSeller.seller.observe(this){
            TV_nama.text = it.fullName
            diminati_profileKota.text = it.city
            Glide.with(applicationContext).load(it.imageUrl).into(IV_penjual)
        }

        initRecyclerView()
    }

    private fun initRecyclerView(){
        userManager = UserManager(this)
        val viewModelProductSeller = ViewModelProvider(this)[ViewModelProductSeller::class.java]

        viewModelProductSeller.getOrder("pending", "terima", token = userManager.fetchAuthToken().toString())

        adapter = AdapterDiminati{
                val pindah = Intent(applicationContext,InfoPenawaranActivity::class.java)
                pindah.putExtra("detailnotif", it)
                startActivity(pindah)
        }
        rv_diminati.layoutManager = LinearLayoutManager(this)
        rv_diminati.adapter = adapter

        viewModelProductSeller.diminati.observe(this){
            if (it.isNotEmpty()){
                for (i in it.indices){
                    if (it[i].status == "pending" || it[i].status == "terima"){
                        adapter.setDataOrder(it)
                        adapter.notifyDataSetChanged()
                        kalaukosongDiminati.isInvisible = true
                    }
                }

            } else {
                kalaukosongDiminati.isVisible = true
            }
        }
    }
}
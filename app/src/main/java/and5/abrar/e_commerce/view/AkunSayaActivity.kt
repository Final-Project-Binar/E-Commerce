package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.view.buyer.NotifikasiBuyerActivity
import and5.abrar.e_commerce.view.seller.AddProductSellerActivity
import and5.abrar.e_commerce.view.seller.DaftarJualActivity
import and5.abrar.e_commerce.view.seller.LengkapiDetailProductActivity
import and5.abrar.e_commerce.viewmodel.ViewModelProductSeller
import and5.abrar.e_commerce.viewmodel.ViewModelUser
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_akun_saya.*
import kotlinx.android.synthetic.main.activity_daftar_jual_seller.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@AndroidEntryPoint
class AkunSayaActivity : AppCompatActivity() {

    private lateinit var  userManager: and5.abrar.e_commerce.datastore.UserManager

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
                Toast.makeText(this, "Kamu Sedang Berada Di Akun", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener false
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
        setContentView(R.layout.activity_akun_saya)
        val botnav = findViewById<BottomNavigationView>(R.id.navigation)
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi)
        userManager = and5.abrar.e_commerce.datastore.UserManager(this)

        val viewModelDataSeller = ViewModelProvider(this)[ViewModelProductSeller::class.java]
        viewModelDataSeller.getSeller(token = userManager.fetchAuthToken().toString())
        viewModelDataSeller.seller.observe(this) {
            Glide.with(applicationContext).load(it.imageUrl).into(icon_foto)
        }

        userManager.ceklogin.asLiveData().observe(this){
            if (it == true){
                akunsaya_login.isInvisible = true
            }else{
                akunsaya_login.isVisible = true
            }
        }
        keluar()
        ubahAkun()

    }

    private fun keluar(){
        val dataUserManager = and5.abrar.e_commerce.datastore.UserManager(this)
        keluar_akun.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("KONFIRMASI LOGOUT")
                .setMessage("Anda Yakin Ingin Logout ?")

                .setPositiveButton("YA"){ dialogInterface: DialogInterface, i: Int ->
                    Toast.makeText(this@AkunSayaActivity, "Berhasil Keluar", Toast.LENGTH_SHORT).show()
                    GlobalScope.launch {
                        dataUserManager.setBoolean(false)
                        dataUserManager.logout()
                        dataUserManager.clearToken()
                        startActivity(Intent(this@AkunSayaActivity, HomeActivity::class.java))
                        finish()
                    }
                }
                .setNegativeButton("TIDAK"){ dialogInterface: DialogInterface, i: Int ->
                    Toast.makeText(this, "Tidak Jadi Keluar", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }

                .setNeutralButton("NANTI"){ dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                    Toast.makeText(this, "Tidak Jadi Logout", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }

    private fun ubahAkun(){
        ubah_akun.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
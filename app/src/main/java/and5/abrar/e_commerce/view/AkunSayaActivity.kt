package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.view.buyer.NotifikasiBuyerActivity
import and5.abrar.e_commerce.view.seller.AddProductSellerActivity
import and5.abrar.e_commerce.view.seller.DaftarJualActivity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_akun_saya.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class AkunSayaActivity : AppCompatActivity() {

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
                startActivity(Intent(this, AddProductSellerActivity::class.java))
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
                    GlobalScope.launch {
                        dataUserManager.logout()
                        startActivity(Intent(this@AkunSayaActivity, HomeActivity::class.java))
                        finish()
                        Toast.makeText(this@AkunSayaActivity, "Berhasil Keluar", Toast.LENGTH_SHORT).show()
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
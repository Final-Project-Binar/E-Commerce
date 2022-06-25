package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.view.buyer.NotifikasiBuyerActivity
import and5.abrar.e_commerce.view.seller.AddProductSellerActivity
import and5.abrar.e_commerce.view.seller.DaftarJualActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_akun_saya.*

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
        akunsaya_btnkeluar.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}
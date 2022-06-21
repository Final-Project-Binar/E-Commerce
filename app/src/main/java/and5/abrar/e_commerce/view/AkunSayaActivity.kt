package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.view.seller.DaftarJualActivity
import and5.abrar.e_commerce.view.seller.LengkapiDetailProductActivity
import and5.abrar.e_commerce.view.seller.NotifikasiSellerActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class AkunSayaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_akun_saya)

        val bottomNavigation = BottomNavigationView.OnNavigationItemSelectedListener { item->
            when (item.itemId) {
                R.id.dashboard -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.notifikasi -> {
                    startActivity(Intent(this, NotifikasiSellerActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.jual -> {
                    // tambahin kondisi jika blum melengkapi akun
                    startActivity(Intent(this, LengkapiDetailProductActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.daftar_jual -> {
                    startActivity(Intent(this, DaftarJualActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.akun -> {
                    startActivity(Intent(this, AkunSayaActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

        val botNav = findViewById<BottomNavigationView>(R.id.navigation)
        botNav.setOnNavigationItemSelectedListener(bottomNavigation)
    }
}
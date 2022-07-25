@file:Suppress("RedundantOverride")

package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.viewmodel.ViewModelProductSeller
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_product_seller.*

@AndroidEntryPoint
class AddProductSellerActivity : AppCompatActivity() {

    private lateinit var  userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_seller)
        userManager = UserManager(this)
        val viewModelDataSeller = ViewModelProvider(this)[ViewModelProductSeller::class.java]
        viewModelDataSeller.getSeller(token = userManager.fetchAuthToken().toString())
        viewModelDataSeller.seller.observe(this) {
            TV_nama.text = "Nama Seller" + it.fullName
            seller_kota.text = "Kota Seller" + it.city
            Glide.with(this@AddProductSellerActivity).load(it.imageUrl).into(IV_penjual)
        }
        userManager.harga.asLiveData().observe(this){
            addProduct_harga.text = "Rp.$it"
        }
        userManager.name.asLiveData().observe(this){
            addProduct_namaproduk.text = "Nama Produk : $it"
        }
        userManager.deskripsi.asLiveData().observe(this){
            addProduct_deskripsi.text = it
        }
        userManager.kategori.asLiveData().observe(this){
            addProduct_category.text = it
        }
        userManager.gambar.asLiveData().observe(this){
            Glide.with(this).load(it).into(add_gambar)
        }

        Glide.with(this).load(userManager.datasellerimage()).into(IV_penjual)
        TV_nama.text = userManager.datasellerusername()
        seller_kota.text = userManager.datasellerkota()
        addProductSeller_btnTerbit.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
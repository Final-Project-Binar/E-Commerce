package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.model.produkseller.Category
import and5.abrar.e_commerce.room.Diminati
import and5.abrar.e_commerce.room.DiminatiDatabase
import and5.abrar.e_commerce.viewmodel.ViewModelHome
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide

import kotlinx.android.synthetic.main.activity_add_product_seller.*
import kotlinx.android.synthetic.main.item_product_home.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call

class AddProductSellerActivity : AppCompatActivity() {

    private lateinit var  userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_seller)
        userManager = UserManager(this)
        userManager.name.asLiveData().observe(this){
            addProduct_namaproduk.text = it
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
package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem

import and5.abrar.e_commerce.model.produkseller.Category
import and5.abrar.e_commerce.viewmodel.ViewModelHome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_product_seller.*
import kotlinx.android.synthetic.main.item_product_home.view.*

class AddProductSellerActivity : AppCompatActivity() {
    private var idproduct : Int = 0
    private lateinit var listCategory: List<Category>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_add_product_seller)
        val detailproduct = intent.extras!!.getSerializable("detailproduk") as GetBuyerProductItem?
        idproduct = detailproduct?.id!!

    }
    fun getdata(){
        val detailproduct = intent.extras!!.getSerializable("detailproduk") as GetBuyerProductItem?
        val viewModelDataProduct = ViewModelProvider(this)[ViewModelHome::class.java]
        viewModelDataProduct.detailproduct(idproduct)
        viewModelDataProduct.detail.observe(this){
            Glide.with(this).load(it.imageUrl).into(add_gambar)
            addProduct_namaproduk.text = it.name
            addProduct_category.text = it.categories.toString()
            addProduct_harga.text = "Rp"+it.basePrice
            addProduct_deskripsi.text = it.description
        }
    }
    private fun setCategory(listCategory : List<Category>){
        this.listCategory = listCategory
    }
}
@file:Suppress("RedundantOverride", "RedundantOverride", "RedundantOverride")

package and5.abrar.e_commerce.view.buyer

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.notifikasi.GetNotifikasiItem
import and5.abrar.e_commerce.model.orderbuyer.PostBuyerOrder
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.model.wishlist.WishListBuyer
import and5.abrar.e_commerce.network.ApiClient
import and5.abrar.e_commerce.view.HomeActivity
import and5.abrar.e_commerce.view.LoginActivity
import and5.abrar.e_commerce.viewmodel.BuyerOrderViewModel
import and5.abrar.e_commerce.viewmodel.ViewModelHome
import and5.abrar.e_commerce.viewmodel.ViewModelWishList
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_product_buyer.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.custom_dialog_hargatawar_buyer.view.*
import kotlinx.android.synthetic.main.item_notifikasi_buyer.view.*
import kotlinx.coroutines.DelicateCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@DelicateCoroutinesApi
@AndroidEntryPoint
class AddProductBuyerActivity : AppCompatActivity() {
    private lateinit var userManager: UserManager
    private lateinit var apiClient: ApiClient
    private lateinit var list: List<WishListBuyer>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_buyer)
        userManager = UserManager(this)
        apiClient = ApiClient()
        back.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        detailData()
        postFavorite()
    }

    @SuppressLint("SetTextI18n")
    private fun detailData(){
        userManager = UserManager(this)
        val dataProduct = intent.extras!!.getSerializable("detailproduk") as GetBuyerProductItem?
        val dataProductnotif = intent.extras!!.getSerializable("detailnotif") as GetNotifikasiItem?
        if (dataProduct != null) {
            val viewModel = ViewModelProvider(this)[ViewModelHome::class.java]
            viewModel.detailproduct(dataProduct.id)
            viewModel.detail.observe(this){
                Glide.with(this@AddProductBuyerActivity).load(it.user.imageUrl).override(45,45)
                    .into(IV_penjual)
                addBuyer_kota.text = it.user.city
                TV_nama.text = it.user.fullName
            }
            Glide.with(this)
                .load(dataProduct.imageUrl)
                .override(400,350)
                .into(tv_imgdetailproduct)
            tv_judulproductdetail.text = dataProduct.name
            tv_acesorisproductdetail.text = dataProduct.categories.toString()
            tv_hargaproductdetail.text = dataProduct.basePrice.toString()
            tv_deskripsidetail.text = dataProduct.description
            tv_acesorisproductdetail.text = ""
            if (dataProduct.categories.isNotEmpty()){
                for (i in dataProduct.categories.indices){
                    if (dataProduct.categories.lastIndex == 0){
                        tv_acesorisproductdetail.text = dataProduct.categories[i].name
                        break
                    }
                    if (i == 0) {
                        tv_acesorisproductdetail.text = dataProduct.categories[i].name + ","
                    } else if (i != dataProduct.categories.lastIndex && i > 0){
                        tv_acesorisproductdetail.text = tv_acesorisproductdetail.text.toString() +
                        dataProduct.categories[i].name  + ","
                    } else {
                        tv_acesorisproductdetail.text = tv_acesorisproductdetail.text.toString() +
                        dataProduct.categories[i].name
                    }
                }
            } else {
                tv_acesorisproductdetail.text = "Lainnya"
            }
            if (dataProductnotif != null){
                addProductBuyer_btnTertarik.text = "Menunggu Respon Penjual"
            }else {
                addProductBuyer_btnTertarik.setOnClickListener {
                    userManager.ceklogin.asLiveData().observe(this){
                        if (it == true){
                            iniDialogTawarHarga()
                        } else {
                            Toast.makeText(this, "Anda Belum Login", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                    }

                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun iniDialogTawarHarga(){
        userManager = UserManager(this)
        val dialog = BottomSheetDialog(this)
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_hargatawar_buyer, null)
        val detailBarang = intent.extras!!.getSerializable("detailproduk") as GetBuyerProductItem
        val  btnTawaran = dialogView.ca_hargatawar_btnok
        dialogView.customDialog_namaProduk.text = detailBarang.name
        dialogView.custom_hargaproduk.text = "Harga : Rp. ${detailBarang.basePrice}"
        Glide.with(dialogView.customDialog_gambarProduk.context)
            .load(detailBarang.imageUrl)
            .error(R.drawable.ic_launcher_background)
            .into(dialogView.customDialog_gambarProduk)
        dialogView.custum_Categoriproduct.text = ""
        if (detailBarang.categories.isNotEmpty()){
            for (i in detailBarang.categories.indices){
                if (detailBarang.categories.lastIndex == 0) {
                    dialogView.custum_Categoriproduct.text =
                        "Kategori: ${detailBarang.categories[i].name}"
                    break
                }
                if (i == 0) {
                    dialogView.custum_Categoriproduct.text =
                        "Kategori: ${detailBarang.categories[i].name}, "
                } else if (i != detailBarang.categories.lastIndex && i > 0) {
                    dialogView.custum_Categoriproduct.text =
                        dialogView.custum_Categoriproduct.text.toString() + detailBarang.categories[i].name + ","
                } else {
                    dialogView.custum_Categoriproduct.text =
                        dialogView.custum_Categoriproduct.text.toString() +
                                detailBarang.categories[i].name
                }
            }
        } else {
            dialogView.custum_Categoriproduct.text = "Kategori Tidak ditemukan"
        }

        btnTawaran.setOnClickListener {
            val productId = detailBarang.id
            val edtTawar = dialogView.ca_hargatawar.text.toString().toInt()
            if (edtTawar.toString().isNotEmpty()) {
                val buyerOrderViewModel = ViewModelProvider(this)[BuyerOrderViewModel::class.java]
                    buyerOrderViewModel.postBuyerOrder(userManager.fetchAuthToken().toString(), PostBuyerOrder(productId, edtTawar))
                Toast.makeText(this, "Tawaran sudah dikirim", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
        dialog.setCancelable(true)
        dialog.setContentView(dialogView)
        dialog.show()
    }

    private fun postFavorite(){
        userManager = UserManager(this)
        val buyerWishList = ViewModelProvider(this)[ViewModelWishList::class.java]

        imageWishList.setOnClickListener {
            userManager.ceklogin.asLiveData().observe(this){
                if (it == true){
                    val detailBarangd = intent.extras!!.getSerializable("detailproduk") as GetBuyerProductItem
                    val productID = detailBarangd.id
                    buyerWishList.postWishListBuyer(userManager.fetchAuthToken().toString(), productID)
                    imageWishList.setImageResource(R.drawable.favorite_click)
                    Toast.makeText(this, "Berhasil Menambahkan Wish List Anda", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Anda Belum Login", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
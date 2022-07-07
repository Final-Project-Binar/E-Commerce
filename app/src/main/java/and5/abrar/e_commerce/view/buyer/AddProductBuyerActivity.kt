package and5.abrar.e_commerce.view.buyer

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.notifikasi.GetNotifikasiItem
import and5.abrar.e_commerce.model.orderbuyer.PostBuyerOrder
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.view.HomeActivity
import and5.abrar.e_commerce.viewmodel.BuyerOrderViewModel
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_product_buyer.*
import kotlinx.android.synthetic.main.custom_dialog_hargatawar_buyer.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@AndroidEntryPoint
class AddProductBuyerActivity : AppCompatActivity() {
    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_buyer)
        back.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        detailData()
    }

    @SuppressLint("SetTextI18n")
    private fun detailData(){
        val dataProduct = intent.extras!!.getSerializable("detailproduk") as GetBuyerProductItem?
        val dataProductnotif = intent.extras!!.getSerializable("detailnotif") as GetNotifikasiItem?
        if (dataProduct != null) {
            Glide.with(this)
                .load(dataProduct.imageUrl)
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
            }else if(dataProduct != null) {

                addProductBuyer_btnTertarik.setOnClickListener {
                    if(userManager.fetchAuthToken().toString() != null){
                        iniDialogTawarHarga()
                    }else{
                        Toast.makeText(this, "Anda Belom Login", Toast.LENGTH_SHORT).show()
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
        dialogView.customDialog_namaProduk.text = detailBarang!!.name
        dialogView.custom_hargaproduk.text = "Harga : Rp. ${detailBarang.basePrice}"
        Glide.with(dialogView.customDialog_gambarProduk.context)
            .load(detailBarang.imageUrl)
            .error(R.drawable.ic_launcher_background)
            .into(dialogView.customDialog_gambarProduk)
        dialogView.custum_Categoriproduct.text = ""
        if (detailBarang.categories!!.isNotEmpty()){
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
                    buyerOrderViewModel.postBuyerOrder(userManager.fetchAuthToken().toString(), PostBuyerOrder(productId!!, edtTawar))
                Toast.makeText(this, "Tawaran sudah dikirim", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
        dialog.setCancelable(true)
        dialog.setContentView(dialogView)
        dialog.show()
    }
}
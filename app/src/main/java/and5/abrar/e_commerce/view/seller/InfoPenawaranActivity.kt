package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import android.app.AlertDialog
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.orderseller.GetOrderSellerItem
import and5.abrar.e_commerce.view.adapter.AdapterDiminati
import and5.abrar.e_commerce.view.adapter.AdapterOrderSeller
import and5.abrar.e_commerce.viewmodel.ViewModelInfoPenawarSeller
import and5.abrar.e_commerce.viewmodel.ViewModelProductSeller
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RadioButton
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_daftar_jual_diminati_seller.*
import kotlinx.android.synthetic.main.activity_daftar_jual_seller.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_info_penawaran.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.custom_dialog_seller_28.*
import kotlinx.android.synthetic.main.custom_dialog_seller_28.view.*
import kotlinx.android.synthetic.main.item_infopenawar.view.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class InfoPenawaranActivity : AppCompatActivity() {
    private lateinit var userManager: UserManager
    private lateinit var adapterInfo : AdapterOrderSeller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_penawaran)

        infoPenawar()
        btnVisible()

    }

    private fun btnVisible(){

    }

    private fun infoPenawar(){
        userManager = UserManager(this)
        val detailInfo = intent.getParcelableExtra<GetOrderSellerItem>("detailnotif")
        val viewModelNotifikasiId = ViewModelProvider(this)[ViewModelInfoPenawarSeller::class.java]

        buyerName_infoPenawar.text = detailInfo?.user?.fullName
        location_infoPenawar.text = detailInfo?.user?.city


        viewModelNotifikasiId.getInfoPenawar(userManager.fetchAuthToken().toString(), detailInfo!!.id)

        viewModelNotifikasiId.sellerInfoPenawar.observe(this){
            if (it != null){
                infopenawar_namaProduk.text = it.productName
                infopenawar_harga.text = "Rp. ${it.basePrice}"
                infopenawar_tawar.text = "Rp. ${it.price}"
                infopenawar_waktu.text = it.updatedAt
                Glide.with(applicationContext).load(it.imageProduct).into(gambarInfoPenawarProdukBuyer)

                when (it.status) {
                    "pending" -> {
                        btn_InfoPenawarTolak.text = "Tolak"
                        btn_InfoPenawarTerima.text = "Terima"
                    }
                    else -> {
                        btn_InfoPenawarTolak.text = "Status"
                        btn_InfoPenawarTerima.text = "Hubungi"
                    }
                }


                if (it.status == "pending") {
                    val accepted : RequestBody = "accepted".toRequestBody("accepted".toMediaTypeOrNull())
                    val declined : RequestBody = "declined".toRequestBody("declined".toMediaTypeOrNull())
                    btn_InfoPenawarTerima.setOnClickListener {
                        viewModelNotifikasiId.patchInfoPenawar(
                            userManager.fetchAuthToken().toString(),
                            detailInfo.id,
                            accepted
                        )
                    }

                    btn_InfoPenawarTolak.setOnClickListener {
                        viewModelNotifikasiId.patchInfoPenawar(
                            userManager.fetchAuthToken().toString(),
                            detailInfo.id,
                            declined
                        )
                    }

                    // ubah status
                } else if (it.status == "accepted" || it.status == "declined"){
//                    btn_InfoPenawarTolak.setOnClickListener{
//                    val alertA = LayoutInflater.from(this).inflate(R.layout.custom_dialog_seller_28, null, false)
//                        val alertB = AlertDialog.Builder(this)
//                            .setView(alertA)
//                            .create()
//                            .show()
//
//                        if (view is RadioButton) {
//                            // Is the button now checked?
//                            val checked = (view as RadioButton).isChecked
//
//                            // Check which radio button was clicked
//                            when (view.getId()) {
//                                R.id.berhasil_terjual ->
//                                    if (checked) {
//                                        alertA.btn_kirim.setOnClickListener {
//                                            viewModelNotifikasiId.patchInfoPenawar(
//                                                userManager.fetchAuthToken().toString(),
//                                                detailInfo.id,
//                                                berhasilTerjual
//                                            )
//                                        }
//                                    }
//                                R.id.batalkan_transaksi ->
//                                    if (checked) {
//                                        alertA.btn_kirim.setOnClickListener {
//                                            viewModelNotifikasiId.patchInfoPenawar(
//                                                userManager.fetchAuthToken().toString(),
//                                                detailInfo.id,
//                                                batalkanTransaksi
//                                            )
//                                        }
//                                    }
//                            }
//                        }
//                    }

                }

            }
        }

    }


    override fun onResume() {
        super.onResume()
        infoPenawar()
    }

}
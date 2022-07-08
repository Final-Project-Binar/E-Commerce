package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.orderseller.GetOrderSellerItem
import and5.abrar.e_commerce.view.adapter.AdapterOrderSeller
import and5.abrar.e_commerce.viewmodel.ViewModelInfoPenawarSeller
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_daftar_jual_diminati_seller.*
import kotlinx.android.synthetic.main.activity_daftar_jual_seller.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_info_penawaran.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.custom_dialog_hargatawar_buyer.*
import kotlinx.android.synthetic.main.custom_dialog_infopenawarharga_seller.*
import kotlinx.android.synthetic.main.custom_dialog_infopenawarharga_seller.view.*
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
                infopenawar_statusproduk.text = it.status
                Glide.with(applicationContext).load(it.product.imageUrl).into(gambarInfoPenawarProdukBuyer)

                when (it.status) {
                    "pending" -> {
                        btn_InfoPenawarTolak.text = "Tolak"
                        btn_InfoPenawarTerima.text = "Terima"
                    }
                    "terima" -> {
                        btn_InfoPenawarTolak.text = "Status"
                        btn_InfoPenawarTerima.text = "Hubungi"
                    }
                    else -> {
                        btn_InfoPenawarTolak.text = "Status"
                        btn_InfoPenawarTerima.text = "Hubungi"
                    }
                }


                when (it.status) {
                    "pending" -> {
                        val accepted : RequestBody = "terima".toRequestBody("terima".toMediaTypeOrNull())
                        val declined : RequestBody = "declined".toRequestBody("declined".toMediaTypeOrNull())
                        btn_InfoPenawarTerima.setOnClickListener {
                            viewModelNotifikasiId.patchInfoPenawar(
                                userManager.fetchAuthToken().toString(),
                                detailInfo.id,
                                accepted
                            )
                            recreate()
                        }

                        btn_InfoPenawarTolak.setOnClickListener {
                            viewModelNotifikasiId.patchInfoPenawar(
                                userManager.fetchAuthToken().toString(),
                                detailInfo.id,
                                declined
                            )
                            recreate()
                        }


                    }
                    "terima" -> {
                        btn_InfoPenawarTerima.setOnClickListener {
                            val alertt = LayoutInflater.from(this).inflate(
                                R.layout.custom_dialog_infopenawarharga_seller,
                                null,
                                false
                            )
                            AlertDialog.Builder(this)
                                .setView(alertt)
                                .create()
                                .show()

                            alertt.customDialog_NamaPenjual.text = detailInfo.user.fullName
                            alertt.customDialog_Kotapenjual.text = detailInfo.user.city
                            viewModelNotifikasiId.sellerInfoPenawar.observe(this) {
                                Glide.with(applicationContext).load(it.product.imageUrl)
                                    .into(alertt.customDialog_gambarProduk)
                                alertt.customDialog_namaProduk.text = it.productName
                                alertt.cd_harga.text = "Rp. ${it.basePrice}"
                                alertt.cd_hargatawar.text = "Rp. ${it.price}"

                            }

                            alertt.ca_hargatawar_wa.setOnClickListener {
                                val url =
                                    "https://api.whatsapp.com/send?phone= ${detailInfo.user.phoneNumber}"
                                val i = Intent(Intent.ACTION_VIEW)
                                i.data = Uri.parse(url)
                                startActivity(i)
                            }
                        }

                        // ubah status

                        btn_InfoPenawarTolak.setOnClickListener {

                            val alertd = LayoutInflater.from(this)
                                .inflate(R.layout.custom_dialog_seller_28, null, false)
                            AlertDialog.Builder(this)
                                .setView(alertd)
                                .create()
                                .show()

                            alertd.btn_kirim.setOnClickListener {
                                val accepted: RequestBody = "accepted".toRequestBody("accepted".toMediaTypeOrNull())
                                val declined: RequestBody = "declined".toRequestBody("declined".toMediaTypeOrNull())
                                if (view is RadioButton){
                                    val checked = (view as RadioButton).isChecked

                                    when (view.id){
                                        R.id.berhasil_terjual ->
                                            if (checked) {
                                                viewModelNotifikasiId.patchInfoPenawar(
                                                    userManager.fetchAuthToken().toString(),
                                                    detailInfo.id,
                                                    accepted
                                                )
                                            }
                                        R.id.batalkan_transaksi ->
                                            if (checked) {
                                                viewModelNotifikasiId.patchInfoPenawar(
                                                    userManager.fetchAuthToken().toString(),
                                                    detailInfo.id,
                                                    declined
                                                )
                                            }
                                    }
                                }
                                recreate()
                            }

                        }


                    }

                    "declined", "accepted" -> {
                        btn_InfoPenawarTerima.setOnClickListener {
                            val alertt = LayoutInflater.from(this).inflate(
                                R.layout.custom_dialog_infopenawarharga_seller,
                                null,
                                false
                            )
                            AlertDialog.Builder(this)
                                .setView(alertt)
                                .create()
                                .show()

                            alertt.customDialog_NamaPenjual.text = detailInfo.user.fullName
                            alertt.customDialog_Kotapenjual.text = detailInfo.user.city
                            viewModelNotifikasiId.sellerInfoPenawar.observe(this) {
                                Glide.with(applicationContext).load(it.product.imageUrl)
                                    .into(alertt.customDialog_gambarProduk)
                                alertt.customDialog_namaProduk.text = it.productName
                                alertt.cd_harga.text = "Rp. ${it.basePrice}"
                                alertt.cd_hargatawar.text = "Rp. ${it.price}"

                            }

                            alertt.ca_hargatawar_wa.setOnClickListener {
                                val url =
                                    "https://api.whatsapp.com/send?phone= ${detailInfo.user.phoneNumber}"
                                val i = Intent(Intent.ACTION_VIEW)
                                i.data = Uri.parse(url)
                                startActivity(i)
                            }
                        }

                        btn_InfoPenawarTolak.setOnClickListener {

                            val alertd = LayoutInflater.from(this)
                                .inflate(R.layout.custom_dialog_seller_28, null, false)
                            AlertDialog.Builder(this)
                                .setView(alertd)
                                .create()
                                .show()

                            alertd.btn_kirim.setOnClickListener {
                                val accepted: RequestBody = "accepted".toRequestBody("accepted".toMediaTypeOrNull())
                                val declined: RequestBody = "declined".toRequestBody("declined".toMediaTypeOrNull())
                                if (view is RadioButton){
                                    val checked = (view as RadioButton).isChecked

                                    when (view.id){
                                        R.id.berhasil_terjual ->
                                            if (checked) {
                                                viewModelNotifikasiId.patchInfoPenawar(
                                                    userManager.fetchAuthToken().toString(),
                                                    detailInfo.id,
                                                    accepted
                                                )
                                            }
                                        R.id.batalkan_transaksi ->
                                            if (checked) {
                                                viewModelNotifikasiId.patchInfoPenawar(
                                                    userManager.fetchAuthToken().toString(),
                                                    detailInfo.id,
                                                    declined
                                                )
                                            }
                                    }
                                }
                                recreate()
                            }

                        }



                    }
                }

            }
        }

    }



}
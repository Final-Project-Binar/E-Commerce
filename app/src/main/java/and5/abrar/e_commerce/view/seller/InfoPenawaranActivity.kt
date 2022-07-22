@file:Suppress("NestedLambdaShadowedImplicitParameter", "NestedLambdaShadowedImplicitParameter",
    "NestedLambdaShadowedImplicitParameter", "NestedLambdaShadowedImplicitParameter",
    "NestedLambdaShadowedImplicitParameter", "NestedLambdaShadowedImplicitParameter",
    "NestedLambdaShadowedImplicitParameter", "NestedLambdaShadowedImplicitParameter"
)

package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.orderseller.GetOrderSellerItem
import and5.abrar.e_commerce.viewmodel.ViewModelInfoPenawarSeller
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_info_penawaran.*
import kotlinx.android.synthetic.main.custom_dialog_infopenawarharga_seller.view.*
import kotlinx.android.synthetic.main.custom_dialog_seller_28.view.*
import kotlinx.coroutines.DelicateCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@DelicateCoroutinesApi
@AndroidEntryPoint
class InfoPenawaranActivity : AppCompatActivity() {
    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_penawaran)

        infoPenawar()
        backProductDiminati()
    }


    private fun infoPenawar() {
        userManager = UserManager(this)
        val detailInfo = intent.getParcelableExtra<GetOrderSellerItem>("detailnotif")
        val viewModelNotifikasiId = ViewModelProvider(this)[ViewModelInfoPenawarSeller::class.java]

        buyerName_infoPenawar.text = detailInfo?.user?.fullName
        location_infoPenawar.text = detailInfo?.user?.city

        viewModelNotifikasiId.getInfoPenawar(
            userManager.fetchAuthToken().toString(),
            detailInfo!!.id
        )

        viewModelNotifikasiId.sellerInfoPenawar.observe(this) {
            if (it != null) {
                Glide.with(applicationContext).load(it.user.imageurl)
                    .override(80,80)
                    .into(imageViewInfoPenawaran)
                infopenawar_namaProduk.text = "Nama : Product"+it.productName
                infopenawar_harga.text = "Harga : Rp. ${it.basePrice}"
                infopenawar_tawar.text = "Ditawar : Rp. ${it.price}"
                infopenawar_waktu.text = it.updatedAt
                infopenawar_statusproduk.text = it.status
                Glide.with(applicationContext).load(it.product.imageUrl)
                    .into(gambarInfoPenawarProdukBuyer)
                if(it.status == "declined"){
                    btn_InfoPenawarTolak.isInvisible = true
                    btn_InfoPenawarTerima.isInvisible = true
                    info_status.text = "ANDA SUDAH MENOLAK TAWARAN INI"
                }
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
                        val accepted: RequestBody =
                            "terima".toRequestBody("terima".toMediaTypeOrNull())
                        val declined: RequestBody =
                            "declined".toRequestBody("declined".toMediaTypeOrNull())
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
//                            val alertt = LayoutInflater.from(this).inflate(
//                                R.layout.custom_dialog_infopenawarharga_seller,
//                                null,
//                                false
//                            )
//                            AlertDialog.Builder(this)
//                                .setView(alertt)
//                                .create()
//                                .show()

                            val dialog = BottomSheetDialog(this)
                            val dialogView = layoutInflater.inflate(R.layout.custom_dialog_infopenawarharga_seller, null)

                            dialogView.customDialog_NamaPenjual.text = detailInfo.user.fullName
                            dialogView.customDialog_Kotapenjual.text = detailInfo.user.city
                            viewModelNotifikasiId.sellerInfoPenawar.observe(this) {
                                Glide.with(applicationContext).load(it.product.imageUrl)
                                    .into(dialogView.customDialog_gambarProduk)
                                dialogView.customDialog_namaProduk.text = it.productName
                                dialogView.cd_harga.text = "Rp. ${it.basePrice}"
                                dialogView.cd_hargatawar.text = "Rp. ${it.price}"

                            }

                            dialogView.ca_hargatawar_wa.setOnClickListener {
                                val url =
                                    "https://api.whatsapp.com/send?phone= ${detailInfo.user.phoneNumber}"
                                val i = Intent(Intent.ACTION_VIEW)
                                i.data = Uri.parse(url)
                                startActivity(i)
                            }

                            dialog.setCancelable(true)
                            dialog.setContentView(dialogView)
                            dialog.show()
                        }

                        // ubah status
                        btn_InfoPenawarTolak.setOnClickListener {

//                            val alertd = LayoutInflater.from(this)
//                                .inflate(R.layout.custom_dialog_seller_28, null, false)
//
//                            val acceptedRadio = alertd.berhasil_terjual
//                            val declinedRadio = alertd.batalkan_transaksi
//
//                            val alertB = AlertDialog.Builder(this)
//                                .setView(alertd)
//                                .create()

                            val dialog = BottomSheetDialog(this)
                            val dialogView = layoutInflater.inflate(R.layout.custom_dialog_seller_28, null)

                            val acceptedRadio = dialogView.berhasil_terjual
                            val declinedRadio = dialogView.batalkan_transaksi

                            dialogView.btn_kirim.setOnClickListener {
                                val accepted: RequestBody = "accepted".toRequestBody("accepted".toMediaTypeOrNull())
                                val declined: RequestBody = "declined".toRequestBody("declined".toMediaTypeOrNull())

                                if (acceptedRadio!!.isChecked) {
                                    viewModelNotifikasiId.patchInfoPenawar(
                                        userManager.fetchAuthToken().toString(),
                                        detailInfo.id,
                                        accepted
                                    )
                                    recreate()
                                } else if (declinedRadio!!.isChecked) {
                                    viewModelNotifikasiId.patchInfoPenawar(
                                        userManager.fetchAuthToken().toString(),
                                        detailInfo.id,
                                        declined
                                    )
                                    recreate()
                                } else {
                                    Toast.makeText(this, "Pilih Salah Satu", Toast.LENGTH_SHORT).show()
                                }
                            }

                            dialog.setCancelable(true)
                            dialog.setContentView(dialogView)
                            dialog.show()
                        }


                    }

                    "declined", "accepted" -> {
                        btn_InfoPenawarTerima.setOnClickListener {
//                            val alertt = LayoutInflater.from(this).inflate(
//                                R.layout.custom_dialog_infopenawarharga_seller,
//                                null,
//                                false
//                            )
//                            AlertDialog.Builder(this)
//                                .setView(alertt)
//                                .create()
//                                .show()

                            val dialog = BottomSheetDialog(this)
                            val dialogView = layoutInflater.inflate(R.layout.custom_dialog_infopenawarharga_seller, null)

                            dialogView.customDialog_NamaPenjual.text = detailInfo.user.fullName
                            dialogView.customDialog_Kotapenjual.text = detailInfo.user.city
                            viewModelNotifikasiId.sellerInfoPenawar.observe(this) {
                                Glide.with(applicationContext).load(it.product.imageUrl)
                                    .into(dialogView.customDialog_gambarProduk)
                                dialogView.customDialog_namaProduk.text = it.productName
                                dialogView.cd_harga.text = "Rp. ${it.basePrice}"
                                dialogView.cd_hargatawar.text = "Rp. ${it.price}"

                            }

                            dialogView.ca_hargatawar_wa.setOnClickListener {
                                val url =
                                    "https://api.whatsapp.com/send?phone= ${detailInfo.user.phoneNumber}"
                                val i = Intent(Intent.ACTION_VIEW)
                                i.data = Uri.parse(url)
                                startActivity(i)
                            }

                            dialog.setCancelable(true)
                            dialog.setContentView(dialogView)
                            dialog.show()
                        }

                        //ubah status
                        btn_InfoPenawarTolak.setOnClickListener {

//                            val alertd = LayoutInflater.from(this)
//                                .inflate(R.layout.custom_dialog_seller_28, null, false)
//
//                            val acceptedRadio = alertd.berhasil_terjual
//                            val declinedRadio = alertd.batalkan_transaksi
//
//                            val alertB = AlertDialog.Builder(this)
//                                .setView(alertd)
//                                .create()

                            val dialog = BottomSheetDialog(this)
                            val dialogView = layoutInflater.inflate(R.layout.custom_dialog_seller_28, null)

                            val acceptedRadio = dialogView.berhasil_terjual
                            val declinedRadio = dialogView.batalkan_transaksi

                            dialogView.btn_kirim.setOnClickListener {
                                val accepted: RequestBody = "accepted".toRequestBody("accepted".toMediaTypeOrNull())
                                val declined: RequestBody = "declined".toRequestBody("declined".toMediaTypeOrNull())

                                if (acceptedRadio!!.isChecked) {
                                    viewModelNotifikasiId.patchInfoPenawar(
                                        userManager.fetchAuthToken().toString(),
                                        detailInfo.id,
                                        accepted
                                    )
                                    recreate()
                                } else if (declinedRadio!!.isChecked) {
                                    viewModelNotifikasiId.patchInfoPenawar(
                                        userManager.fetchAuthToken().toString(),
                                        detailInfo.id,
                                        declined
                                    )
                                    recreate()
                                } else {
                                    Toast.makeText(this, "Pilih Salah Satu", Toast.LENGTH_SHORT).show()
                                }
                            }

                            dialog.setCancelable(true)
                            dialog.setContentView(dialogView)
                            dialog.show()
                        }

                    }
                }
            }
        }
    }


    private fun backProductDiminati(){
        kembali_keproductdiminati.setOnClickListener {
            startActivity(Intent(this, DaftarJualDiminatiSellerActivity::class.java))
            finish()
        }
    }

}
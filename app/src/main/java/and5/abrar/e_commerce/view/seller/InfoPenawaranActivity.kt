@file:Suppress("NestedLambdaShadowedImplicitParameter")

package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.orderseller.GetOrderSellerItem
import and5.abrar.e_commerce.viewmodel.ViewModelInfoPenawarSeller
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
                    .error(R.drawable.ic_baseline_account_circle_24)
                    .override(80,80)
                    .into(imageViewInfoPenawaran)
                infopenawar_namaProduk.text = "Nama Produk : ${it.productName}"
                infopenawar_harga.text = "Harga : Rp. ${it.basePrice}"
                infopenawar_tawar.text = "Ditawar : Rp. ${it.price}"
                infopenawar_waktu.text = it.updatedAt
                infopenawar_statusproduk.text = it.status
                Glide.with(applicationContext).load(it.product.imageUrl)
                    .into(gambarInfoPenawarProdukBuyer)
                if(it.status == "declined"){
                    info_status.text = "Kamu Telah Menolak Penawaran Ini"
                    btn_InfoPenawarTolak.isInvisible = true
                    btn_InfoPenawarTerima.isInvisible = true
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
                            AlertDialog.Builder(this)
                                .setTitle("KONFIRMASI Terima")
                                .setMessage("Anda Yakin Ingin Terima Penawaran Ini ?")

                                .setPositiveButton("YA"){ _: DialogInterface, _: Int ->
                                    Toast.makeText(this, "Berhasil Diterima", Toast.LENGTH_SHORT).show()
                                    viewModelNotifikasiId.patchInfoPenawar(
                                        userManager.fetchAuthToken().toString(),
                                        detailInfo.id,
                                        accepted
                                    )
                                    recreate()
                                }
                                .setNegativeButton("TIDAK"){ dialogInterface: DialogInterface, _: Int ->
                                    Toast.makeText(this, "Tidak Jadi Diterima", Toast.LENGTH_SHORT).show()
                                    dialogInterface.dismiss()
                                }
                                .show()

                        }

                        btn_InfoPenawarTolak.setOnClickListener {
                            AlertDialog.Builder(this)
                                .setTitle("KONFIRMASI TOLAK")
                                .setMessage("Anda Yakin Ingin Menolak Penawaran Ini ?")

                                .setPositiveButton("YA"){ _: DialogInterface, _: Int ->
                                    Toast.makeText(this, "Berhasil Ditolak", Toast.LENGTH_SHORT).show()
                                    viewModelNotifikasiId.patchInfoPenawar(
                                        userManager.fetchAuthToken().toString(),
                                        detailInfo.id,
                                        declined
                                    )
                                    recreate()
                                }
                                .setNegativeButton("TIDAK"){ dialogInterface: DialogInterface, _: Int ->
                                    Toast.makeText(this, "Tidak Jadi Ditolak", Toast.LENGTH_SHORT).show()
                                    dialogInterface.dismiss()
                                }
                                .show()

                        }


                    }
                    "terima" -> {
                        btn_InfoPenawarTerima.setOnClickListener {

                            val dialog = BottomSheetDialog(this)
                            val dialogView = layoutInflater.inflate(R.layout.custom_dialog_infopenawarharga_seller, null)

                            dialogView.customDialog_NamaPenjual.text = "Nama Pembeli : ${detailInfo.user.fullName}"
                            dialogView.customDialog_Kotapenjual.text = "Kota : ${detailInfo.user.city}"
                            viewModelNotifikasiId.sellerInfoPenawar.observe(this) {
                                Glide.with(applicationContext).load(it.product.imageUrl)
                                    .into(dialogView.customDialog_gambarProduk)
                                Glide.with(applicationContext).load(it.user.imageurl)
                                    .into(dialogView.customDialog_gambarPenjual)
                                dialogView.customDialog_namaProduk.text = "Nama Produk : ${it.productName}"
                                dialogView.cd_harga.text = "Harga : Rp. ${it.basePrice}"
                                dialogView.cd_hargatawar.text = "Ditawar : Rp. ${it.price}"

                            }

                            dialogView.ca_hargatawar_wa.setOnClickListener {
                                val message = "Haloo Kak ${detailInfo.user.fullName}," +
                                        "Terimakasih telah melakukan penawaran pada product kami,\n"+
                                        "Product yang anda Tawar sbb:\n"+
                                        "Nama Product : ${detailInfo.product.name}\n"+
                                        "Harga Product : Rp. ${detailInfo.product.basePrice}\n"+
                                        "Harga Tawar : Rp. ${detailInfo.price}\n"+
                                        "Kami telah menerima penawaran nya, mohon segera konfirmasi kelanjutanya "

                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(
                                            String.format(
                                                "https://api.whatsapp.com/send?phone=%s&text=%s",
                                                detailInfo.user.phoneNumber,
                                                message
                                            )
                                        )
                                    )
                                )

                            }

                            dialog.setCancelable(true)
                            dialog.setContentView(dialogView)
                            dialog.show()
                        }

                        // ubah status
                        btn_InfoPenawarTolak.setOnClickListener {


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
                                    Toast.makeText(this, "Berhasil Diperbarui", Toast.LENGTH_SHORT).show()
                                    recreate()
                                } else if (declinedRadio!!.isChecked) {
                                    viewModelNotifikasiId.patchInfoPenawar(
                                        userManager.fetchAuthToken().toString(),
                                        detailInfo.id,
                                        declined
                                    )
                                    Toast.makeText(this, "Berhasil Diperbarui", Toast.LENGTH_SHORT).show()
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
                                val message = "Haloo Kak ${detailInfo.user.fullName}," +
                                        "Terimakasih telah melakukan penawaran pada product kami,\n"+
                                        "Product yang anda Tawar sbb:\n"+
                                        "Nama Product : ${detailInfo.product.name}\n"+
                                        "Harga Product : Rp. ${detailInfo.product.basePrice}\n"+
                                        "Harga Tawar : Rp. ${detailInfo.price}\n"+
                                        "Kami telah menerima penawaran nya, mohon segera konfirmasi kelanjutanya "

                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(
                                            String.format(
                                                "https://api.whatsapp.com/send?phone=%s&text=%s",
                                                detailInfo.user.phoneNumber,
                                                message
                                            )
                                        )
                                    )
                                )
                            }

                            dialog.setCancelable(true)
                            dialog.setContentView(dialogView)
                            dialog.show()
                        }

                        //ubah status
                        btn_InfoPenawarTolak.setOnClickListener {

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
                                    Toast.makeText(this, "Berhasil Diperbarui", Toast.LENGTH_SHORT).show()
                                    recreate()
                                } else if (declinedRadio!!.isChecked) {
                                    viewModelNotifikasiId.patchInfoPenawar(
                                        userManager.fetchAuthToken().toString(),
                                        detailInfo.id,
                                        declined
                                    )
                                    Toast.makeText(this, "Berhasil Diperbarui", Toast.LENGTH_SHORT).show()
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
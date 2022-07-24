package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.produkseller.GetDataProductSellerItem
import and5.abrar.e_commerce.view.seller.DaftarJualActivity
import and5.abrar.e_commerce.view.seller.EditProduct
import and5.abrar.e_commerce.viewmodel.ViewModelProductSeller
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product_seller.view.*

class AdapterProductSeller(private  var onClick : (GetDataProductSellerItem)->Unit) : RecyclerView.Adapter<AdapterProductSeller.ViewHolder> () {

    private var dataProductSeller : List<GetDataProductSellerItem>? = null

    fun setDataProductSeller(list: List<GetDataProductSellerItem>){
        this.dataProductSeller = list
    }
    private lateinit var  userManager: UserManager

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product_seller, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvJudul_product_seller.text = dataProductSeller!![position].name


        holder.itemView.tvKategori_product_seller.text = dataProductSeller!![position].location


        holder.itemView.tvHarga_product_seller.text = "Rp. ${dataProductSeller!![position].basePrice}"
        holder.itemView.tvStatus_product_seller.text = "Status : ${dataProductSeller!![position].status}"

        Glide.with(holder.itemView.context)
            .load(dataProductSeller!![position].imageUrl)
            .into(holder.itemView.imageProductSeller)

        holder.itemView.button_delete_card.setOnClickListener {
            userManager = UserManager(holder.itemView.context)
            val viewModelProductSeller = ViewModelProvider(holder.itemView.context as DaftarJualActivity)[ViewModelProductSeller::class.java]
            AlertDialog.Builder(it.context)
                .setTitle("KONFIRMASI HAPUS")
                .setMessage("Anda Yakin Ingin Menghapus Data Produk Ini ?")

                .setPositiveButton("YA"){ _: DialogInterface, _: Int ->
                    Toast.makeText(it.context, "Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                    viewModelProductSeller.deleteProduct(userManager.fetchAuthToken().toString(), dataProductSeller!![position].id)
                    (holder.itemView.context as DaftarJualActivity).initView()
                }
                .setNegativeButton("TIDAK"){ dialogInterface: DialogInterface, _: Int ->
                    Toast.makeText(it.context, "Tidak Jadi Dihapus", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }
                .show()
        }

        holder.itemView.button_edit_card.setOnClickListener {
            onClick(dataProductSeller!![position])
        }
    }

    override fun getItemCount(): Int {
        return if (dataProductSeller.isNullOrEmpty()) {
            0
        } else {
            dataProductSeller!!.size
        }
    }

}
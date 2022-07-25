@file:Suppress("unused")

package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.orderseller.GetOrderSellerItem
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product_seller.view.*

class AdapterTerjual(private  var onClick :(GetOrderSellerItem)->Unit) : RecyclerView.Adapter<AdapterTerjual.ViewHolder>() {
    private var dataOrder : List<GetOrderSellerItem>? = null
    fun setDataOrder(list : List<GetOrderSellerItem>){
        this.dataOrder = list
    }
    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product_seller, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvJudul_product_seller.text = "Nama Produk : ${dataOrder!![position].productName}"
        holder.itemView.tvKategori_product_seller.text = "Kota : ${dataOrder!![position].product.location}"
        holder.itemView.tvHarga_product_seller.text = "Harga : Rp. ${dataOrder!![position].basePrice}"
        holder.itemView.tvStatus_product_seller.text = "status : ${dataOrder!![position].product.status}"
        holder.itemView.textTanggalUpdate.text = dataOrder!![position].updatedAt
        Glide.with(holder.itemView.context)
            .load(dataOrder!![position].product.imageUrl)
            .into(holder.itemView.imageProductSeller)
        holder.itemView.button_edit_card.isInvisible = true
        holder.itemView.button_delete_card.isInvisible = true
        holder.itemView.textTanggalUpdate.isVisible = true
    }

    override fun getItemCount(): Int {
        return if (dataOrder.isNullOrEmpty()) {
            0
        } else {
            dataOrder!!.size
        }
    }
}
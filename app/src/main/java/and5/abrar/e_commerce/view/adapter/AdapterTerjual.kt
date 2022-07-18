package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.orderseller.GetOrderSellerItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvJudul_product_seller.text = dataOrder!![position].productName
        holder.itemView.tvKategori_product_seller.text = dataOrder!![position].product.location
        holder.itemView.tvHarga_product_seller.text = "Rp. ${dataOrder!![position].basePrice}"
        Glide.with(holder.itemView.context)
            .load(dataOrder!![position].product.imageUrl)
            .into(holder.itemView.imageProductSeller)
    }

    override fun getItemCount(): Int {
        return if (dataOrder.isNullOrEmpty()) {
            0
        } else {
            dataOrder!!.size
        }
    }
}
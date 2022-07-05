package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.orderseller.GetOrderSellerItem
import and5.abrar.e_commerce.room.Diminati
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_productdiminati.view.*

class AdapterDiminati():RecyclerView.Adapter<AdapterDiminati.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)

    private var listDiminati:List<GetOrderSellerItem>? = null
    fun setDataOrder(list : List<GetOrderSellerItem>){
        this.listDiminati = list
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_productdiminati, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(listDiminati!![position].product.imageUrl)
            .into(holder.itemView.diminati_image)
        with(holder.itemView) {
            with(listDiminati!![position]) {
            diminati_status.text = status
                if (productName != null){
                    diminati_product.text = productName.toString()
                }else{
                    diminati_product.text = product.name
                }
            if (basePrice != null){
                diminati_harga.text = basePrice.toString()
            }else{
                diminati_harga.text = product.basePrice.toString()
            }
                if (price != null){
                    diminati_tawar.text = price.toString()
                }else{
                    diminati_tawar.text = ""
                }

            diminati_tanggal.text = updatedAt
            }
        }
    }

    override fun getItemCount(): Int {
        return if (listDiminati.isNullOrEmpty()) {
            0
        } else {
            listDiminati!!.size
        }
    }
}
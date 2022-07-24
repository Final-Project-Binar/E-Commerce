package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.orderseller.GetOrderSellerItem
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_productdiminati.view.*

class AdapterDiminati(private var onClick : (GetOrderSellerItem)->Unit):RecyclerView.Adapter<AdapterDiminati.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)

    private var listDiminati:List<GetOrderSellerItem>? = null
    fun setDataOrder(list : List<GetOrderSellerItem>){
        this.listDiminati = list
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_productdiminati, parent, false)
        return ViewHolder(viewItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(listDiminati!![position].product.imageUrl)
            .into(holder.itemView.diminati_image)
        with(holder.itemView) {
            with(listDiminati!![position]) {
                diminati_status.text = status
                diminati_product.text = "Nama Produk : $productName"
                diminati_harga.text = "Harga : Rp."+basePrice.toString()
                diminati_tawar.text = "Ditawar : Rp." +price.toString()
                diminati_tanggal.text = updatedAt
            }
        }

        holder.itemView.keInformasiPenawar.setOnClickListener {
            onClick(listDiminati!![position])
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
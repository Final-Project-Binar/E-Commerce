@file:Suppress("RemoveEmptyClassBody", "unused")

package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.ordersellerid.GetSellerOrderId
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.info_penawar_info.view.*

class AdapterOrderSeller(private var onClick : (GetSellerOrderId)->Unit): RecyclerView.Adapter<AdapterOrderSeller.ViewHolder>() {

    private var dataProductOrderSeller : List<GetSellerOrderId>? = null

    fun setProductOrderSeller(produk : List<GetSellerOrderId>){
        this.dataProductOrderSeller = produk
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.info_penawar_info, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(dataProductOrderSeller!![position].imageProduct)
            .into(holder.itemView.imageViewPenawaran)

        with(holder.itemView) {
            with(dataProductOrderSeller!![position]) {
                textJudulPenawaran.text = productName
            }
        }
    }

    override fun getItemCount(): Int {
        return if (dataProductOrderSeller == null){
            0
        }else{
            dataProductOrderSeller!!.size
        }
    }
}
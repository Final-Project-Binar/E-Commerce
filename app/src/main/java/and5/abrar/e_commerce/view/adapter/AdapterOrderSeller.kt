package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.orderseller.GetSellerOrderProductIdItem
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_info_penawaran.view.*
import kotlinx.android.synthetic.main.item_infopenawar.view.*
import kotlinx.android.synthetic.main.item_product_home.view.*

class AdapterOrderSeller(private var onClick : (GetSellerOrderProductIdItem)->Unit): RecyclerView.Adapter<AdapterOrderSeller.ViewHolder>() {
    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)
    private var dataProductOrderSeller : List<GetSellerOrderProductIdItem>? = null

    fun setProductOrderSeller(produk : List<GetSellerOrderProductIdItem>){
        this.dataProductOrderSeller = produk
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewitem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_infopenawar,parent, false)
        return ViewHolder(viewitem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.infopenawar_namaProduk.text = dataProductOrderSeller!![position].product.name

        Glide.with(holder.itemView.context)
            .load(dataProductOrderSeller!![position].product.imageUrl)
            .into(holder.itemView.gambarInfoPenawarProdukBuyer)
    }

    override fun getItemCount(): Int {
        return if (dataProductOrderSeller == null){
            0
        }else{
            dataProductOrderSeller!!.size
        }
    }
}
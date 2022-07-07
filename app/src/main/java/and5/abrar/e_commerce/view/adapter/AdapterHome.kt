package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.banner.GetBannerItem
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.item_product_home.view.*

class AdapterHome(private var onClick : (GetBuyerProductItem)->Unit):RecyclerView.Adapter<AdapterHome.ViewHolder>() {
    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)
    private var dataProduk : List<GetBuyerProductItem>? = null
    fun setProduk(produk : List<GetBuyerProductItem>){
        this.dataProduk = produk
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewitem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_home,parent, false)
        return ViewHolder(viewitem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.cardProduct.setOnClickListener {
            onClick(dataProduk!![position])
        }
        holder.itemView.tvJudul_product.text = dataProduk!![position].name
        Glide.with(holder.itemView.context)
            .load(dataProduk!![position].imageUrl)
            .into(holder.itemView.imageProduct)
        val category = dataProduk!![position].categories
        holder.itemView.tvKategori_product.text = "masih kosong"
        if(category.isNotEmpty()){
            for (i in category.indices){
                if(category.lastIndex == 0){
                    holder.itemView.tvKategori_product.text = category[i].name
                    break
                }
                if (i==0){
                    holder.itemView.tvKategori_product.text = category[i].name + ", "
                }else if (i != category.lastIndex && i>0){
                    holder.itemView.tvKategori_product.text.toString() + category[i].name + ", "
                }else {
                    holder.itemView.tvKategori_product.text.toString() + category[i].name
                }
            }
        }
        holder.itemView.tvHarga_product.text = dataProduk!![position].basePrice.toString()
    }

    override fun getItemCount(): Int {
        return if (dataProduk == null){
            0
        }else{
            dataProduk!!.size
        }
    }
}
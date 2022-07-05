package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.produkseller.Category
import and5.abrar.e_commerce.model.produkseller.GetDataProductSellerItem
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product_home.view.*
import kotlinx.android.synthetic.main.item_product_seller.view.*

class AdapterProductSeller(private  var onClick : (GetDataProductSellerItem)->Unit) : RecyclerView.Adapter<AdapterProductSeller.ViewHolder> () {

    private var dataProductSeller : List<GetDataProductSellerItem>? = null

    fun setDataProductSeller(list: List<GetDataProductSellerItem>){
        this.dataProductSeller = list
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product_seller, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvJudul_product_seller.text = dataProductSeller!![position].name
        holder.itemView.tvKategori_product_seller.text = dataProductSeller!![position].location
//        holder.itemView.tvKategori_product_seller.text = dataProductSeller!![position].categories.name

//        val category = dataProductSeller!![position].categories
//        holder.itemView.tvKategori_product_seller.text = "masih kosong"
//        if(category.isNotEmpty()){
//            for (i in category.indices){
//                if(category.lastIndex == 0){
//                    holder.itemView.tvKategori_product_seller.text = category[i].name
//                    break
//                }
//                if (i==0){
//                    holder.itemView.tvKategori_product_seller.text = category[i].name + ", "
//                }else if (i != category.lastIndex && i>0){
//                    holder.itemView.tvKategori_product_seller.text.toString() + category[i].name + ", "
//                }else {
//                    holder.itemView.tvKategori_product_seller.text.toString() + category[i].name
//                }
//            }
//        }

        holder.itemView.tvHarga_product_seller.text = "Rp. ${dataProductSeller!![position].basePrice.toString()}"
        Glide.with(holder.itemView.context).load(dataProductSeller!![position].imageUrl).into(holder.itemView.imageProductSeller)


        holder.itemView.cardProductSeller.setOnClickListener {
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
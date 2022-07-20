package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.wishlist.WishListBuyer
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_layout_wishlist.view.*

class AdapterWishList(private  var onClick : (WishListBuyer)->Unit) : RecyclerView.Adapter<AdapterWishList.ViewHolder> () {

    private var dataFavorite : List<WishListBuyer>? = null

    fun setDataFavorite(list: List<WishListBuyer>){
        this.dataFavorite = list
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_wishlist, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(dataFavorite!![position].product.imageUrl).into(holder.itemView.imageViewWishList)
        holder.itemView.nameProduct_Wishlist.text = dataFavorite!![position].product.name
        holder.itemView.hargaWishList.text = dataFavorite!![position].product.basePrice.toString()

    }

    override fun getItemCount(): Int {
        return if (dataFavorite.isNullOrEmpty()) {
            0
        } else {
            dataFavorite!!.size
        }
    }

}
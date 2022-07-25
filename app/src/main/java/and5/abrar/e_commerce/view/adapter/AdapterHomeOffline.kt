@file:Suppress("KotlinDeprecation")

package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.room.Offline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product_home.view.*

class AdapterHomeOffline(private var dataoffline : List<Offline>):RecyclerView.Adapter<AdapterHomeOffline.ViewHolder>() {
    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewitem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_home,parent, false)
        return ViewHolder(viewitem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvJudul_product.text = dataoffline[position].nama
        holder.itemView.tvKategori_product.text = dataoffline[position].category
        holder.itemView.tvHarga_product.text = dataoffline[position].harga
        Glide.with(holder.itemView.context).load(dataoffline[position].image).into(holder.itemView.imageProduct)
    }

    override fun getItemCount(): Int {
        return dataoffline.size
    }
}
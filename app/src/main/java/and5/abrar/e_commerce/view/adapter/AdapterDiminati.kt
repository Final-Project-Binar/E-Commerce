package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.room.Diminati
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_productdiminati.view.*

class AdapterDiminati(private val listDiminati:List<Diminati>):RecyclerView.Adapter<AdapterDiminati.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_productdiminati, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(listDiminati[position].image)
            .into(holder.itemView.diminati_image)
        with(holder.itemView) {
            with(listDiminati[position]) {
            diminati_status.text = status
            diminati_product.text = namaProdcut
            diminati_harga.text = harga
            diminati_tawar.text = tawar
            diminati_tanggal.text = tanggal
            }
        }
    }

    override fun getItemCount(): Int {
        return listDiminati.size
    }
}
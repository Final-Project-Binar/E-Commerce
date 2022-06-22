package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.notifikasi.GetNotifikasiItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_notifikasi_buyer.view.*

class AdapterNotifikasiBuyer(private  var onClick : (GetNotifikasiItem)->Unit):RecyclerView.Adapter<AdapterNotifikasiBuyer.ViewHolder>() {
    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)
    private var dataNotif : List<GetNotifikasiItem>? = null

    fun setNotif (notif : List<GetNotifikasiItem>){
        this.dataNotif = notif
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewitem = LayoutInflater.from(parent.context).inflate(R.layout.item_notifikasi_buyer,parent, false)
        return ViewHolder(viewitem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            with(dataNotif!![position]){
                cardNotifikasiBuyer.setOnClickListener {
                    onClick(dataNotif!![position])
                }
                Glide.with(holder.itemView.context)
                    .load(dataNotif!![position].imageUrl)
                    .into(holder.itemView.gambarProdukBuyer)

                notikasiBuyer_statusproduk.text = status
                notifikasiBuyer_tawar.text = bidPrice.toString()
                notikasiBuyer_waktu.text = transactionDate
                notikasiBuyer_waktu.text = updatedAt

            }
        }
    }

    override fun getItemCount(): Int {
        return if (dataNotif == null){
            0
        }else{
            dataNotif!!.size
        }
    }
}
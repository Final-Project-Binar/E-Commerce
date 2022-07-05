package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.model.notifikasi.GetNotifikasiItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_notifikasi_buyer.view.*

class AdapterNotifikasiBuyer(private var dataNotif : List<GetNotifikasiItem>,
                             private  var onClick : (GetNotifikasiItem)->Unit):RecyclerView.Adapter<AdapterNotifikasiBuyer.ViewHolder>() {
    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewitem = LayoutInflater.from(parent.context).inflate(R.layout.item_notifikasi_buyer,parent, false)
        return ViewHolder(viewitem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            with(dataNotif[position]){
                cardNotifikasiBuyer.setOnClickListener {
                    onClick(dataNotif[position])
                }
                Glide.with(holder.itemView.context)
                    .load(dataNotif[position].imageUrl)
                    .error(R.drawable.ic_launcher_background)
                    .override(75,75)
                    .into(holder.itemView.gambarProdukBuyer)
                if (read == false){
                    notikasiBuyer_alert.setImageResource(R.drawable.ic_baseline_circle_24)
                }else if (read == true){
                    notikasiBuyer_alert.setImageResource(R.drawable.ic_baseline_circle_ijo)
                }
                notifikasiBuyer_namaProduk.text = productName
                notikasiBuyer_statusproduk.text = status
                notifikasiBuyer_harga.text =  basePrice
                notifikasiBuyer_tawar.text = "Ditawar Rp $bidPrice"
                notikasiBuyer_waktu.text = transactionDate

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
package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.produkseller.DeleteSellerProduct
import and5.abrar.e_commerce.model.produkseller.GetDataProductSellerItem
import and5.abrar.e_commerce.network.ApiClient
import and5.abrar.e_commerce.network.ApiService
import and5.abrar.e_commerce.viewmodel.ViewModelProductSeller
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_product_seller.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterProductSeller(private  var onClick : (GetDataProductSellerItem)->Unit) : RecyclerView.Adapter<AdapterProductSeller.ViewHolder> () {

    private var dataProductSeller : List<GetDataProductSellerItem>? = null

    fun setDataProductSeller(list: List<GetDataProductSellerItem>){
        this.dataProductSeller = list
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product_seller, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvJudul_product_seller.text = dataProductSeller!![position].name


        holder.itemView.tvKategori_product_seller.text = dataProductSeller!![position].location


        holder.itemView.tvHarga_product_seller.text = "Rp. ${dataProductSeller!![position].basePrice}"

        Glide.with(holder.itemView.context)
            .load(dataProductSeller!![position].imageUrl)
            .into(holder.itemView.imageProductSeller)

        holder.itemView.button_edit_card.setOnClickListener {
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
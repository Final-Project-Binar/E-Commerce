@file:Suppress("SimplifyBooleanWithConstants", "SimplifyBooleanWithConstants")

package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.orderbuyer.GetBuyerOrder
import and5.abrar.e_commerce.view.buyer.OrderBuyer
import and5.abrar.e_commerce.viewmodel.ViewModelHome
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_order_buyer.view.*

class AdapterOrderBuyer(private var dataNotif : List<GetBuyerOrder>,
                              private var onClick : (GetBuyerOrder)->Unit):RecyclerView.Adapter<AdapterOrderBuyer.ViewHolder>()  {
    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)

    private lateinit var  userManager: UserManager
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewitem = LayoutInflater.from(parent.context).inflate(R.layout.item_order_buyer,parent, false)
        return ViewHolder(viewitem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            with(dataNotif[position]) {
                Glide.with(holder.itemView.context)
                    .load(dataNotif[position].imageProduct)
                    .error(R.drawable.ic_launcher_background)
                    .override(75, 75)
                    .into(holder.itemView.imageVieworder)
                hargaOrder.text = "Harga : $basePrice"
                nameProduct_order.text = "Produk : $productName"
                hagatawaran.text = "Harga Tawar : $price"
                statusorder.text = "Status : $status"
                imageDeleteOrder.setOnClickListener {
                    userManager = UserManager(holder.itemView.context)
                    val viewModel = ViewModelProvider(holder.itemView.context as OrderBuyer)[ViewModelHome::class.java]
                    AlertDialog.Builder(it.context).setTitle("Hapus Data").setMessage("Yakin Hapus Data?")
                        .setPositiveButton("ya"){ _: DialogInterface, _: Int ->
                            viewModel.deleteOrder(userManager.fetchAuthToken().toString(),id)
                            Toast.makeText(it.context, "Orderan Berhasil Dihapus",Toast.LENGTH_SHORT).show()
                            (holder.itemView.context as OrderBuyer).fetchorder()
                        }
                        .setNegativeButton("Tidak"){
                                dialogInterface: DialogInterface, _: Int ->
                            dialogInterface.dismiss()
                        }
                        .show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataNotif.size
    }
}
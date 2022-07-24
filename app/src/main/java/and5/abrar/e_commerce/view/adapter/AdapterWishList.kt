@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused"
)

package and5.abrar.e_commerce.view.adapter

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.wishlist.GetWishListItemItem
import and5.abrar.e_commerce.model.wishlist.WishListBuyer
import and5.abrar.e_commerce.view.buyer.WishListActivity
import and5.abrar.e_commerce.view.seller.DaftarJualActivity
import and5.abrar.e_commerce.viewmodel.ViewModelProductSeller
import and5.abrar.e_commerce.viewmodel.ViewModelWishList
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_layout_wishlist.view.*
import kotlinx.android.synthetic.main.item_product_seller.view.*

class AdapterWishList(private  var onClick : (GetWishListItemItem)->Unit) : RecyclerView.Adapter<AdapterWishList.ViewHolder> () {

    private var dataFavorite : List<GetWishListItemItem>? = null

    private lateinit var  userManager: UserManager

    fun setDataFavorite(list: List<GetWishListItemItem>){
        this.dataFavorite = list
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_wishlist, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(dataFavorite!![position].product.imageUrl).error(R.drawable.ic_baseline_account_circle_24).into(holder.itemView.imageViewWishList)
        holder.itemView.nameProduct_Wishlist.text = "Nama Produk : ${dataFavorite!![position].product.name}"
        holder.itemView.hargaWishList.text = "Harga : Rp. ${dataFavorite!![position].product.basePrice}"
        holder.itemView.textView10.text = "Kota : ${dataFavorite!![position].product.location}"

        holder.itemView.imageDeleteWishList.setOnClickListener {
            userManager = UserManager(holder.itemView.context)
            val viewModelWishlist = ViewModelProvider(holder.itemView.context as WishListActivity)[ViewModelWishList::class.java]
            AlertDialog.Builder(it.context)
                .setTitle("KONFIRMASI HAPUS")
                .setMessage("Anda Yakin Ingin Menghapus Data Wishlist Ini ?")

                .setPositiveButton("YA"){ _: DialogInterface, _: Int ->
                    Toast.makeText(it.context, "Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                    viewModelWishlist.getDeleteWishlist(userManager.fetchAuthToken().toString(), dataFavorite!![position].id)
                    (holder.itemView.context as WishListActivity).recreate()
                }
                .setNegativeButton("TIDAK"){ dialogInterface: DialogInterface, _: Int ->
                    Toast.makeText(it.context, "Tidak Jadi Dihapus", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }
                .show()
        }
    }

    override fun getItemCount(): Int {
        return if (dataFavorite.isNullOrEmpty()) {
            0
        } else {
            dataFavorite!!.size
        }
    }

}
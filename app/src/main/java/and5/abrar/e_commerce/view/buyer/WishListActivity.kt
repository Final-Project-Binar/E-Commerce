package and5.abrar.e_commerce.view.buyer

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.view.adapter.AdapterWishList
import and5.abrar.e_commerce.viewmodel.ViewModelWishList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_wish_list.*

@AndroidEntryPoint
class WishListActivity : AppCompatActivity() {
    private lateinit var adapter : AdapterWishList
    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        userManager = UserManager(this)
        adapter = AdapterWishList {
            //
        }

        WishListRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        WishListRV.adapter = adapter

        val viewModelWishList = ViewModelProvider(this)[ViewModelWishList::class.java]
        viewModelWishList.buyerWishList.observe(this){
            if (it.isNotEmpty()){
                adapter.setDataFavorite(it)
                adapter.notifyDataSetChanged()
            }
        }
        viewModelWishList.getWishListBuyer(userManager.fetchAuthToken().toString())
    }
}
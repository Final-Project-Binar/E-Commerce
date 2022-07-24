package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.wishlist.GetWishListItemItem
import and5.abrar.e_commerce.model.wishlist.WishListBuyer
import and5.abrar.e_commerce.network.ApiService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelWishList @Inject constructor(api : ApiService) : ViewModel(){
    private val liveDataWishList = MutableLiveData<List<GetWishListItemItem>>()
    val buyerWishList : LiveData<List<GetWishListItemItem>> = liveDataWishList

    private val liveDataWishListDelete = MutableLiveData<GetWishListItemItem>()

    private val liveDataWishListPost = MutableLiveData<WishListBuyer>()

    private val apiService = api

    fun getDeleteWishlist(token : String, id: Int){
        apiService.deleteWishlist(token, id)
            .enqueue(object : Callback<GetWishListItemItem>{
                override fun onResponse(
                    call: Call<GetWishListItemItem>,
                    response: Response<GetWishListItemItem>
                ) {
                    if (response.isSuccessful){
                        liveDataWishListDelete.value = response.body()
                    } else {
                        //
                    }
                }

                override fun onFailure(call: Call<GetWishListItemItem>, t: Throwable) {
                    //
                }

            })
    }

    fun getWishListBuyer(token : String){
        apiService.getWishList(token)
            .enqueue(object : Callback<List<GetWishListItemItem>> {
                override fun onResponse(
                    call: Call<List<GetWishListItemItem>>,
                    response: Response<List<GetWishListItemItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataWishList.value = response.body()
                    } else {
                        //
                    }
                }

                override fun onFailure(call: Call<List<GetWishListItemItem>>, t: Throwable) {
                    //
                }

            })
    }

    fun postWishListBuyer(token: String, product_id: Int){
        apiService.postWishList(token, product_id)
            .enqueue(object : Callback<WishListBuyer> {
                override fun onResponse(
                    call: Call<WishListBuyer>,
                    response: Response<WishListBuyer>
                ) {
                    if (response.isSuccessful){
                        liveDataWishListPost.value = response.body()
                    } else {
                        //
                    }
                }

                override fun onFailure(call: Call<WishListBuyer>, t: Throwable) {
                    //
                }

            })
    }
}
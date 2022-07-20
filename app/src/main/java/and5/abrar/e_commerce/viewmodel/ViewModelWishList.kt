package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.wishlist.Product
import and5.abrar.e_commerce.model.wishlist.WishListBuyer
import and5.abrar.e_commerce.network.ApiService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelWishList @Inject constructor(api : ApiService) : ViewModel(){
    private val liveDataWishList = MutableLiveData<List<WishListBuyer>>()
    val buyerWishList : LiveData<List<WishListBuyer>> = liveDataWishList

    private val liveDataWishListPost = MutableLiveData<WishListBuyer>()
    val buyerWishListPost : LiveData<WishListBuyer> = liveDataWishListPost

    private val apiService = api

    fun getWishListBuyer(token : String){
        apiService.getWishList(token)
            .enqueue(object : Callback<List<WishListBuyer>> {
                override fun onResponse(
                    call: Call<List<WishListBuyer>>,
                    response: Response<List<WishListBuyer>>
                ) {
                    if (response.isSuccessful){
                        liveDataWishList.value = response.body()
                    } else {
                        //
                    }
                }

                override fun onFailure(call: Call<List<WishListBuyer>>, t: Throwable) {
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
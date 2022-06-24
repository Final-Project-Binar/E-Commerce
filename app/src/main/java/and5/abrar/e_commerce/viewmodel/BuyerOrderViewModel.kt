package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.orderbuyer.PostBuyerOrder
import and5.abrar.e_commerce.model.orderbuyer.PostBuyerOrderResponseItem
import and5.abrar.e_commerce.network.ApiClient
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
class BuyerOrderViewModel @Inject constructor(api : ApiService) : ViewModel(){
    private val liveDataBuyerOrder = MutableLiveData<PostBuyerOrderResponseItem>()
    val buyerOrder : LiveData<PostBuyerOrderResponseItem> = liveDataBuyerOrder
    private val apiService = api

    fun postBuyerOrder(token : String, postBuyerOrder: PostBuyerOrder){
        apiService.updateBidPrice(token, postBuyerOrder)
            .enqueue(object : Callback<PostBuyerOrderResponseItem> {
                override fun onResponse(
                    call: Call<PostBuyerOrderResponseItem>,
                    response: Response<PostBuyerOrderResponseItem>
                ) {
                    if (response.isSuccessful){
                        liveDataBuyerOrder.value = response.body()
                    } else {

                    }
                }

                override fun onFailure(call: Call<PostBuyerOrderResponseItem>, t: Throwable) {
                    
                }

            })
    }
}
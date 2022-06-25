package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.network.ApiClient
import and5.abrar.e_commerce.network.ApiService
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelHome @Inject constructor(apiService: ApiService): ViewModel() {
    private var liveDataProduct = MutableLiveData<List<GetBuyerProductItem>>()
    val product : LiveData<List<GetBuyerProductItem>> = liveDataProduct
    private val apiServices = apiService

    init {
        viewModelScope.launch {
            val dataproduct = apiService.getBuyerProduct()
            delay(2000)
            liveDataProduct.value = dataproduct
        }
    }



    fun searchproduct(search : String){
        apiServices.getBuyerProductSearch(search).enqueue(object : Callback<List<GetBuyerProductItem>>{
            override fun onResponse(
                call: Call<List<GetBuyerProductItem>>,
                response: Response<List<GetBuyerProductItem>>
            ) {
                if (response.isSuccessful){
                    liveDataProduct.value = response.body()
                }else{

                }
            }

            override fun onFailure(call: Call<List<GetBuyerProductItem>>, t: Throwable) {

            }

        })
    }

}
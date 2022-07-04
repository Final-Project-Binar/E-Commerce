package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.category.GetCategorySellerItem
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductResponseItem
import and5.abrar.e_commerce.model.produkseller.Category
import and5.abrar.e_commerce.network.ApiService
import and5.abrar.e_commerce.repository.ProductRepository
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private var liveDataDetail = MutableLiveData<GetBuyerProductResponseItem>()
    val detail : LiveData<GetBuyerProductResponseItem> = liveDataDetail



    private val apiServices = apiService

    init {
        viewModelScope.launch {
            val dataproduct = apiService.getBuyerProduct()
            delay(2000)
            liveDataProduct.value = dataproduct
        }
    }

    fun detailproduct(id : Int){
        apiServices.getdetailproduct(id).enqueue(object : Callback<GetBuyerProductResponseItem>{
            override fun onResponse(
                call: Call<GetBuyerProductResponseItem>,
                response: Response<GetBuyerProductResponseItem>
            ) {
                if (response.isSuccessful){
                    liveDataDetail.value = response.body()
                }else{

                }
            }

            override fun onFailure(call: Call<GetBuyerProductResponseItem>, t: Throwable) {
               //
            }

        })
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
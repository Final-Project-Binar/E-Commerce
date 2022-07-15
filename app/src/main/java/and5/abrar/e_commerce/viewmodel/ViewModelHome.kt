package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductResponse
import and5.abrar.e_commerce.network.ApiService
import and5.abrar.e_commerce.room.Offline
import and5.abrar.e_commerce.room.OfflineDao
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelHome @Inject constructor( apiService: ApiService, offlineDao: OfflineDao): ViewModel() {
    private var liveDataProduct = MutableLiveData<List<GetBuyerProductItem>>()
    val product : LiveData<List<GetBuyerProductItem>> = liveDataProduct

    private var liveDataDetail = MutableLiveData<GetBuyerProductResponse>()
    val detail : LiveData<GetBuyerProductResponse> = liveDataDetail

    private val livedataOffline = MutableLiveData<List<Offline>>()
    var offlinedata : LiveData<List<Offline>> = livedataOffline
    private val dao = offlineDao

    private val apiServices = apiService

    init {
        viewModelScope.launch {
            val dataproduct = apiService.getBuyerProduct()
            delay(2000)
            liveDataProduct.value = dataproduct
        }
    }


    fun insertOffline(offline: Offline){
        viewModelScope.launch {
            dao.insertOffline(offline)
        }
    }

    fun getOffline(){
        viewModelScope.launch {
           val dataoff = dao.getOffline()
            delay(4000)
            livedataOffline.value = dataoff
        }
    }

    fun deleteoffline(){
        viewModelScope.launch{
            dao.deleteoffline()
        }
    }

    fun detailproduct(id : Int){
        viewModelScope.launch {
            apiServices.getdetailproduct(id)
                .enqueue(object : Callback<GetBuyerProductResponse> {
                    override fun onResponse(
                        call: Call<GetBuyerProductResponse>,
                        response: Response<GetBuyerProductResponse>
                    ) {
                        if (response.isSuccessful) {
                            liveDataDetail.postValue(response.body())
                        } else {
                            liveDataDetail.postValue(null)
                        }
                    }
                    override fun onFailure(call: Call<GetBuyerProductResponse>, t: Throwable) {
                        liveDataDetail.postValue(null)
                    }
                })
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
                }
            }
            override fun onFailure(call: Call<List<GetBuyerProductItem>>, t: Throwable) {
            }
        })
    }
}
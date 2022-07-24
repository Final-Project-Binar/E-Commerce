package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.orderbuyer.DeleteOrder
import and5.abrar.e_commerce.model.orderbuyer.GetBuyerOrder
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductResponse
import and5.abrar.e_commerce.network.ApiService
import and5.abrar.e_commerce.room.Offline
import and5.abrar.e_commerce.room.OfflineDao
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
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

    private var dataOrder = MutableLiveData<List<GetBuyerOrder>>()
    val order : LiveData<List<GetBuyerOrder>> = dataOrder

    private val dao = offlineDao

    private val apiServices = apiService

    private val deleteorderbuyer = MutableLiveData<DeleteOrder>()
    init {
        viewModelScope.launch {
            val dataproduct = apiService.getBuyerProduct()
            delay(2000)
            liveDataProduct.value = dataproduct
        }
    }
    fun fetchbuyerorder(token : String){
        apiServices.getbuyerorder(token).enqueue(object : Callback<List<GetBuyerOrder>>{
            override fun onResponse(
                call: Call<List<GetBuyerOrder>>,
                response: Response<List<GetBuyerOrder>>
            ) {
                if(response.isSuccessful){
                    dataOrder.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<GetBuyerOrder>>, t: Throwable) {
                //
            }

        })
    }

    fun insertOffline(offline: Offline){
        viewModelScope.launch {
            dao.insertOffline(offline)
        }
    }

    fun deleteoffline(){
        viewModelScope.launch{
            dao.deleteoffline()
        }
    }

    fun deleteOrder(token: String,id: Int){
        apiServices.deleteorder(token,id).enqueue(object : Callback<DeleteOrder>{
            override fun onResponse(call: Call<DeleteOrder>, response: Response<DeleteOrder>) {
                if (response.isSuccessful){
                    deleteorderbuyer.value = response.body()
                }
            }

            override fun onFailure(call: Call<DeleteOrder>, t: Throwable) {
                //
            }

        })
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
                        }
                    }
                    override fun onFailure(call: Call<GetBuyerProductResponse>, t: Throwable) {
                        //
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
                }else if(response.body().isNullOrEmpty()){
                   liveDataProduct.value = null
                }
            }
            override fun onFailure(call: Call<List<GetBuyerProductItem>>, t: Throwable) {
                liveDataProduct.value = null
            }
        })
    }

    fun Context?.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT) =
        this?.let { Toast.makeText(it, textId, duration).show() }

}
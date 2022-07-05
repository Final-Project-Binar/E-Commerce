package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.orderseller.GetSellerOrderProductIdItem
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
class ViewModelNotifikasiSeller @Inject constructor(api: ApiService) : ViewModel() {
    private val liveDataPenawar = MutableLiveData<List<GetSellerOrderProductIdItem>>()
    val sellerInfoPenawar: LiveData<List<GetSellerOrderProductIdItem>> = liveDataPenawar
    private val apiServices = api

//    fun getInfoPenawar(token: String, id_product: Int) {
//        apiServices.getInfoPenawar(token, id_product)
//            .enqueue(object : Callback<List<GetSellerOrderProductIdItem>> {
//                override fun onResponse(
//                    call: Call<List<GetSellerOrderProductIdItem>>,
//                    response: Response<List<GetSellerOrderProductIdItem>>
//                ) {
//                    if (response.isSuccessful) {
//                        liveDataPenawar.value = response.body()
//                    } else {
//                        //
//                    }
//                }
//
//                override fun onFailure(call: Call<List<GetSellerOrderProductIdItem>>, t: Throwable) {
//                    //
//                }
//
//            })
//    }
}
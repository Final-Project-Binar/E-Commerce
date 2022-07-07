package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.ordersellerid.GetSellerOrderId
import and5.abrar.e_commerce.model.ordersellerid.PathSellerOrderId
import and5.abrar.e_commerce.model.user.GetUser
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
class ViewModelInfoPenawarSeller @Inject constructor(api: ApiService) : ViewModel() {
    private val liveDataPenawar = MutableLiveData<GetSellerOrderId>()
    val sellerInfoPenawar: LiveData<GetSellerOrderId> = liveDataPenawar

    private val liveDataUbahStatus = MutableLiveData<PathSellerOrderId>()
    val sellerInfoPenawarStatus: LiveData<PathSellerOrderId> = liveDataUbahStatus

    private val apiServices = api

    fun patchInfoPenawar(token: String, id: Int, status: RequestBody) {
        apiServices.patchStatus(token, id, status)
            .enqueue(object : Callback<PathSellerOrderId>{
                override fun onResponse(
                    call: Call<PathSellerOrderId>,
                    response: Response<PathSellerOrderId>
                ) {
                    if (response.isSuccessful){
                        liveDataUbahStatus.value = response.body()
                    } else {
                        //
                    }
                }

                override fun onFailure(call: Call<PathSellerOrderId>, t: Throwable) {
                    //
                }

            })
    }



    fun getInfoPenawar(token: String, id: Int) {
        apiServices.getInfoPenawar(token, id)
            .enqueue(object : Callback<GetSellerOrderId>{
                override fun onResponse(
                    call: Call<GetSellerOrderId>,
                    response: Response<GetSellerOrderId>
                ) {
                    if (response.isSuccessful){
                        liveDataPenawar.value = response.body()
                    } else {
                        //
                    }
                }

                override fun onFailure(call: Call<GetSellerOrderId>, t: Throwable) {
                    //
                }

            })
    }
}
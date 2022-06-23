package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.produkseller.GetDataProductSellerItem
import and5.abrar.e_commerce.model.produkseller.GetUserResponse
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
class ViewModelProductSeller @Inject constructor(api: ApiService) : ViewModel() {
    private val liveDataSellerProduct = MutableLiveData<List<GetDataProductSellerItem>>()
    val sellerProduct: LiveData<List<GetDataProductSellerItem>> = liveDataSellerProduct

    private val liveDataSeller = MutableLiveData<GetUserResponse>()
    val seller: LiveData<GetUserResponse> = liveDataSeller

    val apiServices = api

    fun getAllSellerProduct(token: String) {
        apiServices.getProductSeller(token)
            .enqueue(object : Callback<List<GetDataProductSellerItem>> {
                override fun onResponse(
                    call: Call<List<GetDataProductSellerItem>>,
                    response: Response<List<GetDataProductSellerItem>>
                ) {
                    if (response.isSuccessful) {
                        liveDataSellerProduct.value = response.body()
                    }
                }

                override fun onFailure(call: Call<List<GetDataProductSellerItem>>, t: Throwable) {
                    //
                }

            })
    }


    fun getSeller(token: String) {
        apiServices.getSellerData(token)
            .enqueue(object : Callback<GetUserResponse> {
                override fun onResponse(
                    call: Call<GetUserResponse>,
                    response: Response<GetUserResponse>
                ) {
                    if (response.isSuccessful) {
                        liveDataSeller.value = response.body()
                    } else {
                        //
                    }
                }

                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                    //
                }

            })
    }
}
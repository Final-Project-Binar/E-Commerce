package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.banner.GetBannerItem
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
class ViewModelBanner @Inject constructor(api: ApiService) : ViewModel() {
    private val liveDataBanner = MutableLiveData<List<GetBannerItem>>()
    val sellerBanner: LiveData<List<GetBannerItem>> = liveDataBanner
    private val apiServices = api

    fun getBanner() {
        apiServices.getBannerItem()
            .enqueue(object : Callback<List<GetBannerItem>> {
                override fun onResponse(
                    call: Call<List<GetBannerItem>>,
                    response: Response<List<GetBannerItem>>
                ) {
                    if (response.isSuccessful) {
                        liveDataBanner.value = response.body()
                    } else {
                        //
                    }
                }

                override fun onFailure(call: Call<List<GetBannerItem>>, t: Throwable) {
                    //
                }

            })
    }
}
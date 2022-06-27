package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.produkseller.GetDataProductSellerItem
import and5.abrar.e_commerce.model.produkseller.GetUserResponse
import and5.abrar.e_commerce.model.produkseller.PostSellerProduct
import and5.abrar.e_commerce.network.ApiService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    private val livedataJualProduct = MutableLiveData<PostSellerProduct>()
    val jualproduct : LiveData<PostSellerProduct> = livedataJualProduct

    private val apiServices = api

    fun jualproduct(
        token :String,
        nama : RequestBody,
        desc : RequestBody,
        harga : RequestBody,
        category : List<Int>,
        lokasi :RequestBody,
        image:MultipartBody.Part){
        apiServices.tambahproduct(token, nama, desc, harga, category, lokasi, image)
            .enqueue(object : Callback<PostSellerProduct>{
                override fun onResponse(
                    call: Call<PostSellerProduct>,
                    response: Response<PostSellerProduct>
                ) {
                    if (response.isSuccessful) {
                        livedataJualProduct.value = response.body()
                    }
                }
                override fun onFailure(call: Call<PostSellerProduct>, t: Throwable) {
                    //
                }
            })
    }

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
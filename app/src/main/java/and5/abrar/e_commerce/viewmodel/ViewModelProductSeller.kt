package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.category.GetCategorySellerItem
import and5.abrar.e_commerce.model.orderseller.GetOrderSellerItem
import and5.abrar.e_commerce.model.produkseller.*
import and5.abrar.e_commerce.network.ApiService
import and5.abrar.e_commerce.repository.ProductRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelProductSeller @Inject constructor(private var productRepository: ProductRepository, api: ApiService) : ViewModel() {
    private val liveDataSellerProduct = MutableLiveData<List<GetDataProductSellerItem>>()
    val sellerProduct: LiveData<List<GetDataProductSellerItem>> = liveDataSellerProduct

    private val liveDataSeller = MutableLiveData<GetUserResponse>()
    val seller: LiveData<GetUserResponse> = liveDataSeller

    private val livedataJualProduct = MutableLiveData<PostSellerProduct>()

    private val livedataorder = MutableLiveData<List<GetOrderSellerItem>>()
    val dataorder : LiveData<List<GetOrderSellerItem>> = livedataorder

    private val liveDiminati = MutableLiveData<List<GetOrderSellerItem>>()
    val diminati : LiveData<List<GetOrderSellerItem>> = liveDiminati

    private val updateProduct = MutableLiveData<PutSellerProduct>()

    private val getProduct = MutableLiveData<GetDataProductSellerItem>()
    val getproduk : LiveData<GetDataProductSellerItem> = getProduct

    private val deleteProduct = MutableLiveData<DeleteSellerProduct>()

    var sellerCategory = MutableLiveData<List<GetCategorySellerItem>>()

    private val apiServices = api

    fun deleteProduct(
        token: String,
        id: Int
    ){
        apiServices.deleteProduct(
            token,
            id
        ).enqueue(object : Callback<DeleteSellerProduct>{
            override fun onResponse(
                call: Call<DeleteSellerProduct>,
                response: Response<DeleteSellerProduct>
            ) {
                if(response.isSuccessful){
                    deleteProduct.value = response.body()
                }
            }

            override fun onFailure(call: Call<DeleteSellerProduct>, t: Throwable) {
                //
            }

        })
    }

    fun updateProductnc(
        token: String,
        id:Int,
        nama:RequestBody,
        Deskripsi:RequestBody,
        harga:RequestBody,
        lokasi: RequestBody,
        image :MultipartBody.Part
    ){
        apiServices.updateProductimagenocategory(
            token,
            id,
            nama,
            Deskripsi,
            harga,
            lokasi,
            image
        ).enqueue(object : Callback<PutSellerProduct>{
            override fun onResponse(
                call: Call<PutSellerProduct>,
                response: Response<PutSellerProduct>
            ) {
                if(response.isSuccessful){
                    updateProduct.value = response.body()
                }
            }

            override fun onFailure(call: Call<PutSellerProduct>, t: Throwable) {
                //
            }

        })
    }


    fun updateproductgambar(
        token: String,
        id:Int,
        nama:RequestBody,
        Deskripsi:RequestBody,
        harga:RequestBody,
        kategori:RequestBody,
        lokasi: RequestBody,
        image :MultipartBody.Part){
        apiServices.updateProductimage(
            token,
            id,
            nama,
            Deskripsi,
            harga,
            kategori,
            lokasi,
            image).enqueue(object : Callback<PutSellerProduct>{
            override fun onResponse(
                call: Call<PutSellerProduct>,
                response: Response<PutSellerProduct>
            ) {
                if(response.isSuccessful){
                    updateProduct.value = response.body()
                }
            }

            override fun onFailure(call: Call<PutSellerProduct>, t: Throwable) {
               //
            }

        })
    }

    fun updateProductngnc(
        token: String,
        id: Int,
        nama:RequestBody,
        Deskripsi:RequestBody,
        harga:RequestBody,
        lokasi: RequestBody
    ){apiServices.updateProductnoImagenocategory(
        token,
        id,
        nama,
        Deskripsi,
        harga,
        lokasi
    ).enqueue(object : Callback<PutSellerProduct>{
        override fun onResponse(
            call: Call<PutSellerProduct>,
            response: Response<PutSellerProduct>
        ) {
            if(response.isSuccessful){
                updateProduct.value = response.body()
            }
        }

        override fun onFailure(call: Call<PutSellerProduct>, t: Throwable) {
            //
        }

    })

    }
    fun updateproduct(token: String,
                      id: Int,
                      nama:RequestBody,
                      Deskripsi:RequestBody,
                      harga:RequestBody,
                      kategori:RequestBody,
                      lokasi: RequestBody){
    apiServices.updateProductnoImage(
        token,
        id,
        nama,
        Deskripsi,
        harga,
        kategori,
        lokasi).enqueue(object : Callback<PutSellerProduct>{
        override fun onResponse(
            call: Call<PutSellerProduct>,
            response: Response<PutSellerProduct>
        ) {
            if(response.isSuccessful){
                updateProduct.value = response.body()
            }
        }
        override fun onFailure(call: Call<PutSellerProduct>, t: Throwable) {
            //
        }

    })
    }

    fun getProductid(token : String, id : Int){
        apiServices.getProductid(token,id).enqueue(object : Callback<GetDataProductSellerItem>{
            override fun onResponse(
                call: Call<GetDataProductSellerItem>,
                response: Response<GetDataProductSellerItem>
            ) {
                if(response.isSuccessful){
                    getProduct.value = response.body()
                }
            }

            override fun onFailure(call: Call<GetDataProductSellerItem>, t: Throwable) {
                //
            }

        })
    }

    fun getorderstatus(token : String){
        apiServices.getOrderSellerStatus(token).enqueue(object : Callback<List<GetOrderSellerItem>>{
            override fun onResponse(
                call: Call<List<GetOrderSellerItem>>,
                response: Response<List<GetOrderSellerItem>>
            ) {
                if(response.isSuccessful){
                    livedataorder.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<GetOrderSellerItem>>, t: Throwable) {
               //
            }

        })
    }

    fun getorderstatusall(token : String,status: String){
        apiServices.getOrderSellerStatus(token,status).enqueue(object : Callback<List<GetOrderSellerItem>>{
            override fun onResponse(
                call: Call<List<GetOrderSellerItem>>,
                response: Response<List<GetOrderSellerItem>>
            ) {
                if(response.isSuccessful){
                    livedataorder.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<GetOrderSellerItem>>, t: Throwable) {
                //
            }

        })
    }

    fun getOrder(status: String, status1: String, token: String){
        apiServices.getOrderSeller(status, status1, token).enqueue(object : Callback<List<GetOrderSellerItem>>{
            override fun onResponse(
                call: Call<List<GetOrderSellerItem>>,
                response: Response<List<GetOrderSellerItem>>
            ) {
                if (response.isSuccessful) {
                    liveDiminati.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<GetOrderSellerItem>>, t: Throwable) {
                  //
            }

        })
    }

    fun jualproduct(
        token :String,
        nama : RequestBody,
        desc : RequestBody,
        harga : RequestBody,
        category : RequestBody,
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
    fun getSellerCategory(){
        viewModelScope.launch {
            val category = productRepository.getSellerCategory()
            sellerCategory.value = category
        }
    }
    fun getAllSellerProduct(status: String, token: String) {
        apiServices.getProductSeller(status, token)
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
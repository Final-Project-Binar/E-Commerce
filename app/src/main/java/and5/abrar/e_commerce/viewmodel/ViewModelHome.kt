package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.model.produkseller.GetProdukSellerItem
import and5.abrar.e_commerce.network.ApiClient
import and5.abrar.e_commerce.network.ApiClient2
import and5.abrar.e_commerce.network.ApiService
import and5.abrar.e_commerce.utils.Resource
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelHome @Inject constructor(apiService: ApiService): ViewModel() {
    private var liveDataProduct = MutableLiveData<List<GetBuyerProductItem>>()
    val product : LiveData<List<GetBuyerProductItem>> = liveDataProduct

    init {
        viewModelScope.launch {
            val dataproduct = apiService.getBuyerProduct()
            delay(2000)
            liveDataProduct.value = dataproduct
        }
    }
}
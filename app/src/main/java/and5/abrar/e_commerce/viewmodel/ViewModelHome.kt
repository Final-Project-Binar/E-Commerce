package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.produkbuyer.GetBuyerProductItem
import and5.abrar.e_commerce.network.ApiService
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
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
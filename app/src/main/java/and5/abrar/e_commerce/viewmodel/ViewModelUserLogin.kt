package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.login.GetLogin
import and5.abrar.e_commerce.network.ApiService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
//
//@HiltViewModel
//class ViewModelUserLogin @Inject constructor(api : ApiService) : ViewModel(){
//    private val liveDataUser = MutableLiveData<List<GetLogin>>()
//    val user : LiveData<List<GetLogin>> = liveDataUser
//    private val apiServices = api
//
//    init {
//        viewModelScope.launch {
//            val dataUser = api.getAllUser()
//            delay(2000)
//            liveDataUser.value = dataUser
//        }
//    }
//}
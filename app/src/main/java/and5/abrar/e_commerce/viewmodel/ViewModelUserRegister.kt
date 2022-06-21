//package and5.abrar.e_commerce.viewmodel
//
//import and5.abrar.e_commerce.model.register.PostUserRegister
//import and5.abrar.e_commerce.repository.RegisterRepository
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class ViewModelUserRegister @Inject constructor(private val registerRepository: RegisterRepository) : ViewModel(){
//    var registerLiveData : MutableLiveData<PostUserRegister> = MutableLiveData()
//
//    fun register(address: String, city: String, email: String, fullName: String, imageUrl: String, password: String, phoneNumber: Long) {
//        viewModelScope.launch {
//            registerRepository.postUser(address, city, email, fullName, imageUrl, password, phoneNumber, registerLiveData)
//        }
//
//    }
//
//}
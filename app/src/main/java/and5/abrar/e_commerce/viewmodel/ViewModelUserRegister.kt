package and5.abrar.e_commerce.viewmodel

import and5.abrar.e_commerce.model.register.PostUserResponse
import and5.abrar.e_commerce.network.repository.RegisterRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelUserRegister @Inject constructor(private val registerRepository: RegisterRepository) : ViewModel(){

    var registerLiveData : MutableLiveData<PostUserResponse> = MutableLiveData()


    fun register(email : String, password : String, fullname: String) {
        viewModelScope.launch {
            registerRepository.postUser(email, password, fullname, registerLiveData)
        }

    }

}
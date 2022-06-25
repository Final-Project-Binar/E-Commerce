package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.view.adapter.AdapterProductSeller
import and5.abrar.e_commerce.viewmodel.ViewModelProductSeller
import and5.abrar.e_commerce.viewmodel.ViewModelUser
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_daftar_jual_seller.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var adapter : AdapterProductSeller
    private lateinit var  userManager: UserManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        userManager = UserManager(this)
    }
    private fun updatedata(){
        val fname = tvLengkapi_Info_Akun.text.toString()
        val kota = profile_kota.text.toString()
        val alamat = etAlamat_profile.text.toString()
        val nohp = etNohp_profile.text.toString()
        val viewModelDataSeller = ViewModelProvider(this)[ViewModelUser::class.java]
//        viewModelDataSeller.userProfile(token = userManager.fetchAuthToken().toString(),fname,nohp,alamat,,city)
    }
}
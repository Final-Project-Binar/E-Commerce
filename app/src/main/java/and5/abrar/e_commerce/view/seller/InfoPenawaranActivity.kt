package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.notifikasi.GetNotifikasiItem
import and5.abrar.e_commerce.view.adapter.AdapterOrderSeller
import and5.abrar.e_commerce.view.adapter.AdapterProductSeller
import and5.abrar.e_commerce.viewmodel.ViewModelNotifikasiSeller
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_info_penawaran.*

@AndroidEntryPoint
class InfoPenawaranActivity : AppCompatActivity() {
    private lateinit var userManager: UserManager
    private lateinit var adapter : AdapterOrderSeller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_penawaran)


        infoPenawar()
    }

    private fun infoPenawar(){
        userManager = UserManager(this)
//        val detailInfo = intent.getParcelableExtra<GetNotifikasiItem>("detail_notifikasi")
//        val productId = detailInfo!!.productId
        val viewModelNotifikasiId = ViewModelProvider(this)[ViewModelNotifikasiSeller::class.java]


        adapter = AdapterOrderSeller {
            // action
        }

        rv_infoPenawaran.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_infoPenawaran.adapter = adapter

        viewModelNotifikasiId.sellerInfoPenawar.observe(this){
            if (it != null){
                adapter.setProductOrderSeller(it)
                adapter.notifyDataSetChanged()
            }
        }
//        viewModelNotifikasiId.getInfoPenawar(userManager.fetchAuthToken().toString(), detailInfo!!.productId)
    }
}
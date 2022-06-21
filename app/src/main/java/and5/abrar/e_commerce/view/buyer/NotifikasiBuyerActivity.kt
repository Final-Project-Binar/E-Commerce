package and5.abrar.e_commerce.view.buyer

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.notifikasi.GetNotifikasiItem
import and5.abrar.e_commerce.network.ApiClient
import and5.abrar.e_commerce.network.ApiClient2
import and5.abrar.e_commerce.utils.Status
import and5.abrar.e_commerce.view.adapter.AdapterNotifikasiBuyer
import and5.abrar.e_commerce.view.seller.InfoPenawaranActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_notifikasi_buyer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class NotifikasiBuyerActivity : AppCompatActivity() {
    private lateinit var  userManager: UserManager
    private lateinit var apiClient: ApiClient
    private lateinit var adapterNotifikasiBuyer: AdapterNotifikasiBuyer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifikasi_buyer)
        apiClient = ApiClient()
        userManager = UserManager(this)
        fetchnotif()
    }

    private fun fetchnotif(){
        apiClient.getApiService(this).getNotif().enqueue(object : Callback<GetNotifikasiItem>{
            override fun onResponse(
                call: Call<GetNotifikasiItem>,
                response: Response<GetNotifikasiItem>
            ) {
                if(response.isSuccessful){
                    adapterNotifikasiBuyer = AdapterNotifikasiBuyer {
                        val pindah = Intent(applicationContext,InfoPenawaranActivity::class.java)
                           pindah.putExtra("detailinfo", it)
                           startActivity(pindah)
                    }
//                    adapterNotifikasiBuyer.setNotif(response.body())
                    val lm = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
                    rv_notifikasiBuyer.layoutManager = lm
                    rv_notifikasiBuyer.adapter = adapterNotifikasiBuyer

                }else{
                    Toast.makeText(applicationContext,response.message(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<GetNotifikasiItem>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}
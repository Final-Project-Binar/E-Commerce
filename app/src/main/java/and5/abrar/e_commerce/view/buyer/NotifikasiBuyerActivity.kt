package and5.abrar.e_commerce.view.buyer

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.notifikasi.GetNotifikasiItem
import and5.abrar.e_commerce.network.ApiClient
import and5.abrar.e_commerce.view.adapter.AdapterNotifikasiBuyer
import and5.abrar.e_commerce.view.seller.InfoPenawaranActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_notifikasi_buyer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        apiClient.getApiService(this).getNotif(token = userManager.fetchAuthToken().toString()).enqueue(object : Callback<List<GetNotifikasiItem>>{
            override fun onResponse(
                call: Call<List<GetNotifikasiItem>>,
                response: Response<List<GetNotifikasiItem>>
            ) {
                if(response.isSuccessful){
                    adapterNotifikasiBuyer = AdapterNotifikasiBuyer(response.body()!!) {
                        val pindah = Intent(applicationContext,InfoPenawaranActivity::class.java)
                           pindah.putExtra("detailinfo", it)
                           startActivity(pindah)
                    }
                    rv_notifikasiBuyer.layoutManager = LinearLayoutManager(applicationContext)
                    rv_notifikasiBuyer.adapter = adapterNotifikasiBuyer

                }else{
                    Toast.makeText(applicationContext,userManager.fetchAuthToken(),Toast.LENGTH_LONG).show()
//                    Toast.makeText(applicationContext,response.message(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<GetNotifikasiItem>>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}
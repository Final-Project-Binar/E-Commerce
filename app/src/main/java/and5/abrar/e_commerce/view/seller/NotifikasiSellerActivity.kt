package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.notifikasi.GetNotifikasiItem
import and5.abrar.e_commerce.network.ApiClient
import and5.abrar.e_commerce.view.AkunSayaActivity
import and5.abrar.e_commerce.view.HomeActivity
import and5.abrar.e_commerce.view.adapter.AdapterNotifikasiSeller
import and5.abrar.e_commerce.view.buyer.NotifikasiBuyerActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_notifikasi_buyer.rv_notifikasiBuyer
import kotlinx.android.synthetic.main.activity_notifikasi_seller.*
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@DelicateCoroutinesApi
@Suppress("DEPRECATION")
class NotifikasiSellerActivity : AppCompatActivity() {
    private lateinit var  userManager: UserManager
    private lateinit var apiClient: ApiClient
    private lateinit var adapterNotifikasiSeller: AdapterNotifikasiSeller

    private val bottomNavigasi = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.notifikasi -> {
                Toast.makeText(this, "Kamu Sedang Berada Di Notifikasi", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener false
            }
            R.id.dashboard -> {
                startActivity(Intent(this, HomeActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.jual -> {
                startActivity(Intent(this, LengkapiDetailProductActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.akun -> {
                startActivity(Intent(this, AkunSayaActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.daftar_jual -> {
                startActivity(Intent(this, DaftarJualActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifikasi_seller)
        apiClient = ApiClient()
        userManager = UserManager(this)
        val botnav = findViewById<BottomNavigationView>(R.id.navigation)
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi)
        notifikasi_buyer.setOnClickListener {
            startActivity(Intent(this, NotifikasiBuyerActivity::class.java))
        }
        fetchnotifseller()
    }

    private fun fetchnotifseller(){
        apiClient.getApiService().getNotif(token = userManager.fetchAuthToken().toString()).enqueue(object :
            Callback<List<GetNotifikasiItem>> {
            override fun onResponse(
                call: Call<List<GetNotifikasiItem>>,
                response: Response<List<GetNotifikasiItem>>
            ) {
                if(response.isSuccessful){
                    adapterNotifikasiSeller = AdapterNotifikasiSeller(response.body()!!) {
                        apiClient.getApiService()
                            .patchNotif(token = userManager.fetchAuthToken().toString(),it.id)
                            .enqueue(object : Callback<GetNotifikasiItem> {
                                override fun onResponse(
                                    call: Call<GetNotifikasiItem>,
                                    response: Response<GetNotifikasiItem>
                                ) {
                                    if (response.isSuccessful){
                                        val livedata : MutableLiveData<GetNotifikasiItem> = MutableLiveData()
                                        livedata.postValue(response.body())
                                    }
                                }

                                override fun onFailure(
                                    call: Call<GetNotifikasiItem>,
                                    t: Throwable
                                ) {
                                    //
                                }

                            })
//                        val pindah = Intent(applicationContext,AddProductBuyerActivity::class.java)
//                           pindah.putExtra("detailnotif", it)
//                           startActivity(pindah)
                    }

                    rv_notifikasiBuyer.layoutManager = LinearLayoutManager(applicationContext)
                    rv_notifikasiBuyer.adapter = adapterNotifikasiSeller

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

    override fun onResume() {
        super.onResume()
        fetchnotifseller()
    }
}
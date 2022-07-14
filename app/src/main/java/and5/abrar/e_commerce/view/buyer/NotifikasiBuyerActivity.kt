package and5.abrar.e_commerce.view.buyer

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.notifikasi.GetNotifikasiItem
import and5.abrar.e_commerce.network.ApiClient
import and5.abrar.e_commerce.view.AkunSayaActivity
import and5.abrar.e_commerce.view.HomeActivity
import and5.abrar.e_commerce.view.adapter.AdapterNotifikasiBuyer
import and5.abrar.e_commerce.view.seller.DaftarJualActivity
import and5.abrar.e_commerce.view.seller.LengkapiDetailProductActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_notifikasi_buyer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotifikasiBuyerActivity : AppCompatActivity() {
    private lateinit var  userManager: UserManager
    private lateinit var apiClient: ApiClient
    private lateinit var adapterNotifikasiBuyer: AdapterNotifikasiBuyer
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
        setContentView(R.layout.activity_notifikasi_buyer)
        apiClient = ApiClient()
        userManager = UserManager(this)
        val botnav = findViewById<BottomNavigationView>(R.id.navigation)
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi)
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
                        apiClient.getApiService(applicationContext).patchNotif(token = userManager.fetchAuthToken().toString(),it.id)
                            .enqueue(object : Callback<GetNotifikasiItem>{
                                override fun onResponse(
                                    call: Call<GetNotifikasiItem>,
                                    response: Response<GetNotifikasiItem>
                                ) {
                                    if (response.isSuccessful ){
                                        var livedata : MutableLiveData<GetNotifikasiItem> = MutableLiveData()
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
                    }

                    rv_notifikasiBuyer.layoutManager = LinearLayoutManager(applicationContext)
                    rv_notifikasiBuyer.adapter = adapterNotifikasiBuyer

                }
            }

            override fun onFailure(call: Call<List<GetNotifikasiItem>>, t: Throwable) {
                //
            }

        })
    }

    override fun onResume() {
        super.onResume()
        fetchnotif()
    }
}
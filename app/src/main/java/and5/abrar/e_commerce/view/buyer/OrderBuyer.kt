package and5.abrar.e_commerce.view.buyer

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.view.AkunSayaActivity
import and5.abrar.e_commerce.view.HomeActivity
import and5.abrar.e_commerce.view.adapter.AdapterOrderBuyer
import and5.abrar.e_commerce.viewmodel.ViewModelHome
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_order_buyer.*
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@Suppress("DEPRECATION")
@AndroidEntryPoint
class OrderBuyer : AppCompatActivity() {
    private lateinit var  userManager: UserManager
    private lateinit var adapterOrderBuyer: AdapterOrderBuyer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_buyer)
        userManager = UserManager(this)
        back.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        fetchorder()
    }

    fun fetchorder(){
        val viewModel = ViewModelProvider(this)[ViewModelHome::class.java]
        viewModel.fetchbuyerorder(userManager.fetchAuthToken().toString())
        viewModel.order.observe(this){
            runOnUiThread {
                adapterOrderBuyer = AdapterOrderBuyer(it){}
                rv_orderbuyer.layoutManager = LinearLayoutManager(this@OrderBuyer)
                rv_orderbuyer.adapter = adapterOrderBuyer
            }
        }


    }
}
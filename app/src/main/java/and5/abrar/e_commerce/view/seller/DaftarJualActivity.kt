package and5.abrar.e_commerce.view.seller

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.datastore.UserManager
import and5.abrar.e_commerce.model.produkseller.GetDataProductSellerItem
import and5.abrar.e_commerce.network.ApiClient
import and5.abrar.e_commerce.view.adapter.AdapterProductSeller
import and5.abrar.e_commerce.viewmodel.ViewModelProductSeller
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_daftar_jual_seller.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.item_product_seller.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class DaftarJualActivity : AppCompatActivity() {
    private lateinit var apiClient: ApiClient
    private lateinit var adapter : AdapterProductSeller
    private lateinit var  userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_jual_seller)


        userManager = UserManager(this)

        val viewModelSeller = ViewModelProvider(this)[ViewModelProductSeller::class.java]
        viewModelSeller.getSeller(userManager.fetchAuthToken().toString())

        initView()
    }

    private fun initView(){
        val viewModelDataSeller = ViewModelProvider(this)[ViewModelProductSeller::class.java]
        viewModelDataSeller.getSeller(token = userManager.fetchAuthToken().toString())
        viewModelDataSeller.seller.observe(this){
            TV_nama_product.text = it.fullName
            TV_kota_product.text = it.city
            seller_daftar_product.text = it.address
            Glide.with(applicationContext).load(it.imageUrl).into(IV_penjual_product)
        }

        initRecyclerView()
    }

    private fun initRecyclerView(){
        userManager = UserManager(this)
        val viewModelProductSeller = ViewModelProvider(this)[ViewModelProductSeller::class.java]

        viewModelProductSeller.getAllSellerProduct(token = userManager.fetchAuthToken().toString())

        adapter = AdapterProductSeller()
        rvProductSeller.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvProductSeller.adapter = adapter

        viewModelProductSeller.sellerProduct.observe(this){
            if (it.isNotEmpty()){
                adapter.setDataProductSeller(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

//    private fun getProductSeller(){
//        apiClient = ApiClient()
//        userManager = UserManager(this)
//        apiClient.getApiService(this).getProductSeller(userManager.fetchAuthToken().toString())
//            .enqueue(object : Callback<List<GetDataProductSellerItem>>{
//                override fun onResponse(call: Call<List<GetDataProductSellerItem>>, response: Response<List<GetDataProductSellerItem>>) {
//                    if (response.isSuccessful){
//                        val dataProductSeller = response.body()
//                        val adapterProductSeller = AdapterProductSeller(dataProductSeller!!)
//                        val linear = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
//                        rvProductSeller.layoutManager = linear
//                        rvProductSeller.adapter = adapterProductSeller
//                    } else {
//                        Toast.makeText(this@DaftarJualActivity, response.message(), Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<List<GetDataProductSellerItem>>, t: Throwable) {
//                    Toast.makeText(this@DaftarJualActivity, t.message, Toast.LENGTH_SHORT).show()
//                }
//
//            })
//    }
}
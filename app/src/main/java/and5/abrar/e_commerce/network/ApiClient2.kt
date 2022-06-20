package and5.abrar.e_commerce.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient2 {
    private const val BASE_URL = "https://market-final-project.herokuapp.com/"

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    private  val logging : HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    private val clint = OkHttpClient.Builder().addInterceptor(logging).build()
    val instance : ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(clint)
            .build()
        retrofit.create(ApiService::class.java)
    }
}
package and5.abrar.e_commerce.di

import and5.abrar.e_commerce.network.ApiService
import and5.abrar.e_commerce.room.OfflineDao
import and5.abrar.e_commerce.room.OfflineDatabase
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://market-final-project.herokuapp.com/"

    private  val logging : HttpLoggingInterceptor
        get(){
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    private val client = OkHttpClient.Builder().addInterceptor(logging).build()

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit) : ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideofflinedatabse(@ApplicationContext context: Context) : OfflineDatabase =
        OfflineDatabase.getInstance(context)!!

    @Provides
    @Singleton
    fun provideofflineDao(offlineDatabase: OfflineDatabase): OfflineDao =
        offlineDatabase.offlineDao()
}
package and5.abrar.e_commerce.network

import and5.abrar.e_commerce.datastore.UserManager
import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(context: Context):Interceptor {
    private val userManager = UserManager(context)


    override fun intercept(chain: Interceptor.Chain): Response {
        val token = userManager.fetchAuthToken()
        return if(!token.isNullOrEmpty()) {
            val requestBuilder = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("access_token", token)
                .build()
            chain.proceed(requestBuilder)
        }else{
            val requestBuilder = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(requestBuilder)
        }
    }
        // Jika token ada di session manager, token sisipkan di request header
//        userManager.fetchAuthToken()?.let {
//            requestBuilder.addHeader("Content-Type", "application/json")
//            requestBuilder.addHeader("Accept", "application/json")
//            requestBuilder.addHeader("access_token", it)
//        }
//
//        return chain.proceed(requestBuilder.build())
}
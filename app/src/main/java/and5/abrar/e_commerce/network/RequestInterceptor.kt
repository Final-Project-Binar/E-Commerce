package and5.abrar.e_commerce.network

import and5.abrar.e_commerce.datastore.UserManager
import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(context: Context):Interceptor {
    private val userManager = UserManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Jika token ada di session manager, token sisipkan di request header
        userManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}
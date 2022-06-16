package and5.abrar.e_commerce.network

import and5.abrar.e_commerce.model.login.GetLogin
import retrofit2.http.POST

interface ApiService {
    @POST("datauserlogin")
    suspend fun getAllUser(): List<GetLogin>
}
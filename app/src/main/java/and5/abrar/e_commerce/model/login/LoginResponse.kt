package and5.abrar.e_commerce.model.login

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse (
    @SerializedName("status_code")
    var statusCode: Int,

    @SerializedName("access_token")
    var authToken: String,

    @SerializedName("user")
    var user: GetLogin
):Serializable

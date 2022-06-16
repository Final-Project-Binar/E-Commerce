package and5.abrar.e_commerce.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("status_code")
    var statusCode: Int,

    @SerializedName("auth_token")
    var authToken: String,

    @SerializedName("user")
    var user: GetLogin
)

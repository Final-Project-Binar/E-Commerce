package and5.abrar.e_commerce.model.login

import and5.abrar.e_commerce.model.user.GetUser
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse (
    @SerializedName("status_code")
    var statusCode: Int,

    @SerializedName("access_token")
    var authToken: String,

    @SerializedName("id")
    var id: String,

    @SerializedName("user")
    var user: GetUser
):Serializable

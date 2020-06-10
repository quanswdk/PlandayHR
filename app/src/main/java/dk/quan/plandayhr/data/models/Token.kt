package dk.quan.plandayhr.data.models

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("id_token")
    var idToken: String,

    @SerializedName("access_token")
    var accessToken: String,

    @SerializedName("expires_in")
    var expiresIn: Int,

    @SerializedName("token_type")
    var tokenType: String,

    @SerializedName("refresh_token")
    var refreshToken: String
)

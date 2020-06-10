package dk.quan.plandayhr.data.repositories

import dk.quan.plandayhr.data.PlandayAuthApi
import dk.quan.plandayhr.data.SaveApiRequest
import dk.quan.plandayhr.data.models.Token
import dk.quan.plandayhr.util.Constants.CLIENT_ID
import dk.quan.plandayhr.util.Constants.REFRESH_TOKEN

class AuthRepository(
    private val api: PlandayAuthApi
) : SaveApiRequest() {

    suspend fun authenticate(): Token {
        return apiRequest {
            api.authenticate(CLIENT_ID, "refresh_token", REFRESH_TOKEN)
        }
    }
}
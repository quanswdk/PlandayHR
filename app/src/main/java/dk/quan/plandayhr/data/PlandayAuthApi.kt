package dk.quan.plandayhr.data

import dk.quan.plandayhr.data.models.Token
import dk.quan.plandayhr.util.Constants.AUTH_URL
import dk.quan.plandayhr.util.Constants.BASE_AUTH_URL
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PlandayAuthApi {

    @FormUrlEncoded
    @POST(AUTH_URL)
    suspend fun authenticate(
        @Field("client_id") client_id: String,
        @Field("grant_type") grant_type: String,
        @Field("refresh_token") refresh_token: String
    ): Response<Token>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): PlandayAuthApi {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_AUTH_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(PlandayAuthApi::class.java)
        }
    }
}
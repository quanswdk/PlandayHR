package dk.quan.plandayhr.data

import dk.quan.plandayhr.data.models.Employee
import dk.quan.plandayhr.data.models.Employees
import dk.quan.plandayhr.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PlandayApi {

    @GET("/hr/v1/employees")
    suspend fun getEmployees(
        @Header("X-ClientId") clientId: String,
        @Query("offset") offset: Int = 0
    ): Response<Employees>

    @GET("/hr/v1/employees/{id}")
    suspend fun getEmployee(
        @Header("X-ClientId") clientId: String,
        @Path("id") id: Int
    ): Response<Employee>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor,
            authInterceptor: AuthInterceptor
        ): PlandayApi {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(authInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(PlandayApi::class.java)
        }
    }
}
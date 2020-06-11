package dk.quan.plandayhr.data

import androidx.lifecycle.LiveData
import dk.quan.plandayhr.data.models.Employee
import dk.quan.plandayhr.data.models.Employees
import dk.quan.plandayhr.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlandayApi {

    @GET("/hr/v1/employees")
    fun getEmployees(@Query("offset") offset: Int = 0): LiveData<Response<Employees>>

    @GET("/hr/v1/employees/{id}")
    fun getEmployee(@Path("id") id: Int): LiveData<Response<Employee>>

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
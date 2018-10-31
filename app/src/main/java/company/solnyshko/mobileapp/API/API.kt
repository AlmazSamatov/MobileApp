import company.solnyshko.mobileapp.API.AddressResponse
import company.solnyshko.mobileapp.API.LoginBody
import company.solnyshko.mobileapp.API.Parcels
import company.solnyshko.mobileapp.API.LoginResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import okhttp3.OkHttpClient


interface API {

    @POST("login/")
    fun login(@Body loginbody: LoginBody): Observable<LoginResponse>

    @GET("allParcels/")
    fun allParcels(): Observable<Parcels>

    @POST("nextAddress/")
    fun nextAddress(@Body delivery_operator_id: Int): Observable<AddressResponse>

    companion object Factory {
        fun create(access_token: String = ""): API {
            val httpClient = OkHttpClient.Builder()

            if (access_token.isNotEmpty())
                httpClient.addInterceptor { chain ->
                    val request = chain.request().newBuilder().addHeader("HTTP_X_TOKEN", access_token).build()
                    chain.proceed(request)
                }

            val retrofit = Retrofit.Builder()
                    //.baseUrl("http://ec2-18-216-94-240.us-east-2.compute.amazonaws.com:8000/")
                    .baseUrl("http://192.168.1.55:8080/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()

            return retrofit.create(API::class.java)
        }
    }

}
import company.solnyshko.mobileapp.API.*
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.http.*


interface API {

    @POST("login/")
    fun login(@Body loginbody: LoginBody): Observable<LoginResponse>

    @GET("allParcels/")
    fun allParcels(): Observable<Parcels>

    @POST("nextAddress/")
    fun nextAddress(@Body request: RequestWithID): Observable<AddressResponse>

    @POST("sendMessage/")
    fun sendMessage(@Body request: SendMessageRequest): Call<Response>

    @POST("getMessages/")
    fun getMessage(@Body request: RequestWithID): Observable<MessageResponse>

    @Headers("Connection: close")
    @POST("sendLocation/")
    fun sendLocation(@Body request: OperatorLocation):  Call<Response>


    companion object Factory {
        fun create(access_token: String = ""): API {
            val httpClient = OkHttpClient.Builder()

            if (access_token.isNotEmpty())
                httpClient.addInterceptor { chain ->
                    val request = chain.request().newBuilder().addHeader("HTTP_X_TOKEN", access_token).build()
                    chain.proceed(request)
                }

            val retrofit = Retrofit.Builder()
//                    .baseUrl("http://ec2-18-216-94-240.us-east-2.compute.amazonaws.com:8080/")
                    .baseUrl("http://ec2-18-216-94-240.us-east-2.compute.amazonaws.com:8080/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()

            return retrofit.create(API::class.java)
        }
    }

}
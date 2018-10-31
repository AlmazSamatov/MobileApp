import company.solnyshko.mobileapp.API.LoginBody
import company.solnyshko.mobileapp.API.Parcels
import company.solnyshko.mobileapp.API.Response
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface API {

    @POST("login/")
    fun login(@Body loginbody: LoginBody): Observable<Response>

    @GET("allParcels/")
    fun allParcels(): Observable<Parcels>

    companion object Factory {
        fun create(): API {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://10.240.16.148:8080/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(API::class.java)
        }
    }

}
import company.solnyshko.mobileapp.API.Response
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("login")
    fun login(@Query("login") login: String,
              @Query("password") password: String): Observable<Response>

    companion object Factory {
        fun create(): API {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://stepik.org/api/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(API::class.java)
        }
    }

}
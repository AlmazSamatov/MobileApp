package company.solnyshko.mobileapp.API
import company.solnyshko.mobileapp.ParcelList.Parcel

data class Response(val error: Int, val access_token: String)
data class LoginBody(val login: String, val password: String)
data class Parcels(val error: Int, val parcels: ArrayList<Parcel>)
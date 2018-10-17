package company.solnyshko.mobileapp.API

import company.solnyshko.mobileapp.ParcelList.Parcel

data class Response(val Error: String, val identifier: String)
data class Parcels(val Error: String, val Parcels: ArrayList<Parcel>)
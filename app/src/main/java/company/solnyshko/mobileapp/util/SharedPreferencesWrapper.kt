package company.solnyshko.mobileapp.util

import android.content.Context
import android.content.SharedPreferences
import company.solnyshko.mobileapp.ParcelList.Parcel
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class SharedPreferencesWrapper(context: Context) {

    val sharedPreferences: SharedPreferences
    private val NAME_OF_SHARED_PREF = "userData"
    private val gson = GsonBuilder().create()

    init {
        sharedPreferences = context.getSharedPreferences(NAME_OF_SHARED_PREF, Context.MODE_PRIVATE)
    }

    fun getString(key: String): String {
        return sharedPreferences.getString(key, "")
    }

    fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putParcelsToDeliver(parcels: List<Parcel>) {
        val editor = sharedPreferences.edit()
        editor.putString("parcelsToDeliver", gson.toJson(parcels))
        editor.apply()
    }

    fun getParcelsToDeliver(): List<Parcel> {
        val parcels = sharedPreferences.getString("parcelsToDeliver", "")
        if (parcels != "")
            return gson.fromJson(parcels, object : TypeToken<List<Parcel>>() {}.type)
        else
            return ArrayList<Parcel>()
    }

    fun putParcelsToPick(parcels: List<Parcel>) {
        val editor = sharedPreferences.edit()
        editor.putString("parcelsToPick", gson.toJson(parcels))
        editor.apply()
    }

    fun putMyParcel(parcel: Parcel) {
        val editor = sharedPreferences.edit()
        val json = sharedPreferences.getString("myParcels", "")
        if (json == ""){
            val parcelsList = arrayListOf<Parcel>(parcel)
            editor.putString("myParcels", gson.toJson(parcelsList))
            editor.apply()
        }else{
            var parcelsList : ArrayList<Parcel>? = null
            parcelsList = gson.fromJson(json, object : TypeToken<List<Parcel>>() {}.type)
            if (parcelsList != null) {
                parcelsList.add(parcel)
                editor.putString("myParcels", gson.toJson(parcelsList))
                editor.apply()
            }
        }
    }

    fun getParcelsToPick(): List<Parcel> {
        val parcels = sharedPreferences.getString("parcelsToPick", "")
        if (parcels != "")
            return gson.fromJson(parcels, object : TypeToken<List<Parcel>>() {}.type)
        else
            return ArrayList<Parcel>()
    }

    fun getMyParcels(): List<Parcel> {
        val parcels = sharedPreferences.getString("myParcels", "")
        if (parcels != "")
            return gson.fromJson(parcels, object : TypeToken<List<Parcel>>() {}.type)
        else
            return ArrayList<Parcel>()
    }

    fun deleteFromParcelToDeliver(p : Parcel) : Boolean {
        val json = sharedPreferences.getString("parcelsToDeliver", "")
        var parcelsList : ArrayList<Parcel>? = null
        parcelsList = gson.fromJson(json, object : TypeToken<List<Parcel>>() {}.type)
        if (parcelsList != null){
            val iterator = parcelsList.iterator()
            while (iterator.hasNext()){
                val element = iterator.next()
                if (element.type == p.type && element.icon == p.icon && element.isChecked == p.isChecked){
                    parcelsList.remove(element)
                    deleteAllParcelToDeliver()
                    putParcelsToDeliver(parcelsList)
                    return true
                }
            }
        }
        return false
    }

    fun deleteAllParcelToPick(){
        val editor = sharedPreferences.edit()
        editor.putString("parcelsToPick", "")
        editor.apply()
    }

    fun deleteAllParcelToDeliver(){
        val editor = sharedPreferences.edit()
        editor.putString("parcelsToDeliver", "")
        editor.apply()
    }

    fun deleteAllMyParcels(){
        val editor = sharedPreferences.edit()
        editor.putString("myParcels", "")
        editor.apply()
    }

    fun deleteFromParcelToPick(p : Parcel) : Boolean {
        val json = sharedPreferences.getString("parcelsToPick", "")
        var parcelsList : ArrayList<Parcel>? = null
        parcelsList = gson.fromJson(json, object : TypeToken<List<Parcel>>() {}.type)
        if (parcelsList != null){
            val iterator = parcelsList.iterator()
            while (iterator.hasNext()){
                val element = iterator.next()
                if (element.type == p.type && element.icon == p.icon && element.isChecked == p.isChecked){
                    parcelsList.remove(element)
                    deleteAllParcelToPick()
                    putParcelsToPick(parcelsList)
                    return true
                }
            }
        }
        return false
    }

    fun putAccessToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("accessToken", token)
        editor.apply()
    }

    fun getAccessToken(): String {
        return sharedPreferences.getString("accessToken", "")
    }

    fun putDestination(destination: String) {
        val editor = sharedPreferences.edit()
        editor.putString("destination", destination)
        editor.apply()
    }

    fun getDestination(): String {
        return sharedPreferences.getString("destination", "")
    }

    fun putId(operatorId: String) {
        val editor = sharedPreferences.edit()
        editor.putString("operatorId", operatorId)
        editor.apply()
    }

    fun getId(): String {
        return sharedPreferences.getString("operatorId", "")
    }

    fun setBreakTime() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("breakTime", true)
        editor.apply()
    }

    fun setWorkTime() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("breakTime", false)
        editor.apply()
    }

    fun isBreakTime(): Boolean {
        return sharedPreferences.getBoolean("breakTime", true)
    }

    fun saveMessage(msg: String) {

        val editor = sharedPreferences.edit()
        editor.putBoolean("msgs", false)
        editor.apply()
    }
}
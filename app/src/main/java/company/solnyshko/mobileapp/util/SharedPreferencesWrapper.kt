package company.solnyshko.mobileapp.util

import android.content.Context
import android.content.SharedPreferences
import company.solnyshko.mobileapp.ParcelList.Parcel
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class SharedPreferencesWrapper(context: Context) {

    private val sharedPreferences: SharedPreferences
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
        return gson.fromJson(parcels, object : TypeToken<List<Parcel>>() {}.type)
    }

    fun putParcelsToPick(parcels: List<Parcel>) {
        val editor = sharedPreferences.edit()
        editor.putString("parcelsToPick", gson.toJson(parcels))
        editor.apply()
    }

    fun getParcelsToPick(): List<Parcel> {
        val parcels = sharedPreferences.getString("parcelsToPick", "")
        return gson.fromJson(parcels, object : TypeToken<List<Parcel>>() {}.type)
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
        return sharedPreferences.getBoolean("breakTime", false)
    }

//    fun getMsgs(): List<Pair<String, Boolean>> {
//        val msgsOld = sharedPreferences.getString("msgs", "")
//        val msgsOld1 = gson.fromJson(msgsOld, object : TypeToken<List<Pair<String, Boolean>>>() {}.type)
//        sharedPreferences.getString("msgs", "")
//    }

    fun saveMessage(msg: String) {

        val editor = sharedPreferences.edit()
        editor.putBoolean("msgs", false)
        editor.apply()
    }
}
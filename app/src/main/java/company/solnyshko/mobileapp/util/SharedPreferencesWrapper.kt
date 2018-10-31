package company.solnyshko.mobileapp.util

import android.content.Context
import android.content.SharedPreferences
import company.solnyshko.mobileapp.ParcelList.Parcel
import com.google.gson.Gson
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
        editor.putString("accessToken", value)
        editor.apply()
    }

    fun putParcel(parcels: Parcel) {
        val editor = sharedPreferences.edit()
        editor.putString("parcels", gson.toJson(parcels))
        editor.apply()
    }

    fun getParcels(): List<Parcel> {
        val parcels = sharedPreferences.getString("parcels", "")
        return gson.fromJson(parcels, object : TypeToken<List<Parcel>>() {}.type)
    }

    fun getAccessToken(): String {
        return sharedPreferences.getString("accessToken", "")
    }
}
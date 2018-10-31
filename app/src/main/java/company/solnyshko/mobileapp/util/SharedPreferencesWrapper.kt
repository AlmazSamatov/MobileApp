package company.solnyshko.mobileapp.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesWrapper(context: Context) {

    private val sharedPreferences: SharedPreferences
    private val NAME_OF_SHARED_PREF = "userData"

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

    fun getAccessToken(): String {
        return sharedPreferences.getString("accessToken", "")
    }
}
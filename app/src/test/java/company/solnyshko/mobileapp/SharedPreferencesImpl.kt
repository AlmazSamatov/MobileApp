package company.solnyshko.mobileapp

import android.content.SharedPreferences

class SharedPreferencesImpl : SharedPreferences {
    private var editor = Editor()
    override fun contains(key: String?): Boolean {
        return editor.map.containsKey(key)
    }

    override fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return true
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        
    }

    override fun getInt(key: String?, defValue: Int): Int {
        return -1
    }

    override fun getAll(): MutableMap<String, *> {
        return editor.map
    }

    override fun edit(): SharedPreferences.Editor {
        return editor
    }

    override fun getLong(key: String?, defValue: Long): Long {
        return -1
    }

    override fun getFloat(key: String?, defValue: Float): Float {
        return Float.MIN_VALUE
    }

    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String> {
        return editor.map.keys
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        
    }

    override fun getString(key: String?, defValue: String?): String {
        return editor.map.getOrDefault(key!!, defValue!!)
    }
}

class Editor : SharedPreferences.Editor {

    var map = HashMap<String, String>()

    override fun clear(): SharedPreferences.Editor {
        return this
    }

    override fun putLong(key: String?, value: Long): SharedPreferences.Editor {
        return this
    }

    override fun putInt(key: String?, value: Int): SharedPreferences.Editor {
        return this
    }

    override fun remove(key: String?): SharedPreferences.Editor {
        return this
    }

    override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor {
        return this
    }

    override fun putStringSet(key: String?, values: MutableSet<String>?): SharedPreferences.Editor {
        return this
    }

    override fun commit(): Boolean {
        return true
    }

    override fun putFloat(key: String?, value: Float): SharedPreferences.Editor {
        return this
    }

    override fun apply() {
    }

    override fun putString(key: String?, value: String?): SharedPreferences.Editor {
        map.put(key!!, value!!)
        return this
    }

}
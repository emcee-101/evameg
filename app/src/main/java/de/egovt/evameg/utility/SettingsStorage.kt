package de.egovt.evameg.utility

import android.app.Application
import android.content.Context
import android.content.SharedPreferences



/**
 * Handles Key Value Store.
 *
 * @author Niklas Herzog
 */
class SettingsStorage {

    private val KEY_VALUE_STORE_FILE_NAME = "settings_kv"

    private var app: Application? = null

    fun KeyValueStore(application: Application) {
        this.app = application
    }

    private fun  getPreferences(): SharedPreferences? {
        return this.app?.getSharedPreferences(KEY_VALUE_STORE_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun writeBool(key: String, value: Boolean){

        this.getPreferences()!!.edit().putBoolean(key, value).apply()


    }

    fun getBool(key: String): Boolean {

        return this.getPreferences()!!.getBoolean(key, false)

    }

    fun writeString(key: String, value: String){

        this.getPreferences()!!.edit().putString(key, value).apply()

    }

    fun readString(key: String): String? {

        return this.getPreferences()!!.getString(key, "")

    }
}
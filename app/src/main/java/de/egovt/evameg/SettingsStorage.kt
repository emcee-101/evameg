package de.egovt.evameg

import android.app.Application
import android.content.Context
import android.content.SharedPreferences


class SettingsStorage {

    private val KEY_VALUE_STORE_FILE_NAME = "settings_kv"

    private var app: Application? = null

    fun KeyValueStore(application: Application) {
        this.app = application;
    }

    private fun  getPreferences(): SharedPreferences? {
        return this.app?.getSharedPreferences(KEY_VALUE_STORE_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun writeBool(key: String, value: Boolean){

        this.getPreferences()!!.edit().putBoolean(key, value).apply();


    }

    fun getBool(key: String): Boolean {

        return this.getPreferences()!!.getBoolean(key, false)

    }

    // TODO add for other types of data

}
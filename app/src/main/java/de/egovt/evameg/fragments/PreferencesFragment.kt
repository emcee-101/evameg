package de.egovt.evameg.fragments

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log

import androidx.preference.DropDownPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import de.egovt.evameg.R
import java.util.*

/**
 * Used for app settings like language.
 *
 * @author Niklas Herzog
 *
 */
class PreferencesFragment() : PreferenceFragmentCompat() {

    var languages:DropDownPreference? = null
    lateinit var momentaryContext : Context

    constructor(context:Context):this(){

        momentaryContext = context

    }

    /**
     * Display Options and add Listener for Changes
     */
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.preferences, rootKey)
        languages = findPreference("languages_dd")

        val vals : Array<CharSequence> = arrayOf("\uD83C\uDDE9\uD83C\uDDEA Deutsch", "\uD83C\uDDFA\uD83C\uDDF8 English")
        languages?.entries  = vals
        languages?.entryValues = arrayOf("de","en")

        languages?.setValueIndex(0)



        val sharedPreferences = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }

        val lang = sharedPreferences?.getString("languages_dd", "")
        Log.i("aa", "$lang")

        sharedPreferences?.registerOnSharedPreferenceChangeListener { sharPrefs: SharedPreferences, key: String ->
            changeLang(
                sharPrefs,
                key
            )
        }

    }

    /**
     * Change Language
     */
    private fun changeLang(sharPrefs: SharedPreferences, key: String){

        Log.i("aaaa", "Change Lang invoked with following key: $key")

        val locale = sharPrefs?.getString("languages_dd", "")?.let { Locale(it) }
        Locale.setDefault(locale)
        val resources: Resources = requireActivity().resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.getDisplayMetrics())


    }


}
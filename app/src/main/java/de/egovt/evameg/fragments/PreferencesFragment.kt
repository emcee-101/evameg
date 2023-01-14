package de.egovt.evameg.fragments

import android.os.Bundle
import android.util.Log

import androidx.preference.DropDownPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import de.egovt.evameg.R



class PreferencesFragment() : PreferenceFragmentCompat() {

    var languages:DropDownPreference? = null



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.preferences, rootKey)
        languages = findPreference("languages_dd")

        // todo use strings
        // todo make options fully usable
        // fun setEntries(entries: Array<CharSequence!>): Unit {}
        val vals : Array<CharSequence> = arrayOf("\uD83C\uDDE9\uD83C\uDDEASE GERMANN LANGUAGE","\uD83C\uDDFA\uD83C\uDDF8plain ol' american")
        languages?.entries  = vals
        languages?.entryValues = arrayOf("de","en")

        // todo add setting to set up screen, so that the initial value can be the one set there
        languages?.setValueIndex(0)

        val sharedPreferences = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        val lang = sharedPreferences?.getString("languages_dd", "")
        Log.i("aa", "$lang")

    }


}
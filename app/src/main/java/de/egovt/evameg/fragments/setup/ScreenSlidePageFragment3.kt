package de.egovt.evameg.fragments.setup

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import de.egovt.evameg.R
import de.egovt.evameg.utility.SettingsStorage
import de.egovt.evameg.utility.UI.back
import de.egovt.evameg.utility.UI.cont
import java.util.*

/**
 * Fragment integrated in Setup Activity.
 *
 * Includes a chooser for the language this app is supposed to use.
 *
 * @author Niklas Herzog
 *
 */
class ScreenSlidePageFragment3() : Fragment() {

    lateinit var momentaryContext : Context
    lateinit var application : Application
    lateinit var spinningDoctor : Spinner
    lateinit var sharedPrefs : SettingsStorage

    val options = arrayOf("\uD83C\uDDE9\uD83C\uDDEA Deutsch", "\uD83C\uDDFA\uD83C\uDDF8 English")


    constructor(context:Context, application : Application):this(){
        this.application = application
        momentaryContext = context
        if (this::momentaryContext.isInitialized) { Log.i("aaaaaa", "Context initialised") }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_setup3, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinningDoctor = view.findViewById(R.id.spinner3)

        // continue to next fragment object
        val contButton: Button? = getView()?.findViewById(R.id.welcome_button_lang_yes)
        contButton?.setOnClickListener { cont(activity?.findViewById(R.id.viewPagerSetup)) }

        val backButton: Button? = getView()?.findViewById(R.id.welcome_button_lang_no)
        backButton?.setOnClickListener { back(activity?.findViewById(R.id.viewPagerSetup)) }


        val adapter : ArrayAdapter<String> = ArrayAdapter(momentaryContext, android.R.layout.simple_spinner_item, options)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinningDoctor.adapter = adapter;

        spinningDoctor.onItemSelectedListener = itemSelector()

        }

            inner class itemSelector : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    when(p2){
                        0 -> {changeLang("de")}
                        1 -> {changeLang("en")}
                        else -> {}

                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }


                private fun changeLang(lang : String){

                    Log.i("aaaa", "Change Lang invoked with following lang: $lang")

                    val locale = Locale(lang)

                    Locale.setDefault(locale)
                    val resources: Resources = requireActivity().resources
                    val config: Configuration = resources.configuration
                    config.setLocale(locale)
                    resources.updateConfiguration(config, resources.getDisplayMetrics())



                    sharedPrefs = SettingsStorage()
                    sharedPrefs.KeyValueStore(application)
                    sharedPrefs.writeString("languages_dd", lang)

                }
            }

}
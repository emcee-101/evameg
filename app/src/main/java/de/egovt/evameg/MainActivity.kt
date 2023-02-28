package de.egovt.evameg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import de.egovt.evameg.Fragments.Home
import de.egovt.evameg.activities.SetupActivity
import de.egovt.evameg.activities.test_activity
import de.egovt.evameg.databinding.ActivityMainBinding
import de.egovt.evameg.fragments.HistoryFragment
import de.egovt.evameg.fragments.MapViewFragment
import de.egovt.evameg.fragments.PreferencesFragment
import de.egovt.evameg.fragments.Profile
import de.egovt.evameg.utility.SettingsStorage

/**
 * Activity that displays the fragments for the App.
 * Also checks if this is the first launch and refers to the SetupActivity if that is the case.
 *
 * @author Niklas Herzog, Mohammad Zidane
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_overflow, menu)
        return true
    }

    /**
     *  Overflow menu function that refers to Preferences Screen
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.settings_panel_overflow -> {

                Log.i("aaaa", "Settings called")
                replaceFragment(PreferencesFragment(this) as Fragment)
                true
            }
            else -> {Log.d("helphelphelp", "Unknown Option registered"); false}
        }
    }

    fun checkIfNotFirstLaunch():Boolean{

        // CHECK IN KEY VALUE STORE IF FIRST START
        val sets = SettingsStorage()
        sets.KeyValueStore(application)
        return sets.getBool("notFirstStart")

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // if NOT NOT first launch - its confusing but the double negation was necessary
        if (!checkIfNotFirstLaunch()) {

            startActivity(Intent(this, SetupActivity::class.java))

        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())
        

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(Home())
                R.id.mapview -> replaceFragment(MapViewFragment())
                R.id.profile ->replaceFragment(Profile())
                R.id.history ->replaceFragment(HistoryFragment())
                else -> {startActivity(Intent(this, test_activity::class.java))}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
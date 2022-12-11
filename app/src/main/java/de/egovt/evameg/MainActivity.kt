package de.egovt.evameg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import de.egovt.evameg.Fragments.Home
import de.egovt.evameg.activities.ProfileActivity
import de.egovt.evameg.activities.SetupActivity
import de.egovt.evameg.databinding.ActivityMainBinding
import de.egovt.evameg.fragments.MapViewFragment
import de.egovt.evameg.utility.SettingsStorage

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // CHECK IN KEY VALUE STORE IF FIRST START
        val sets = SettingsStorage()
        sets.KeyValueStore(application)
        val notFirstStart:Boolean = sets.getBool("notFirstStart")
        Log.i("This is not the first start: ", notFirstStart.toString())

        // saved value across rebuilds and restarts
        if (!notFirstStart) {

            startActivity(Intent(this, SetupActivity::class.java))

        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(Home())
                R.id.mapview -> replaceFragment(MapViewFragment())
                R.id.profile -> startActivity(Intent(this, ProfileActivity::class.java))
                else -> {}
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
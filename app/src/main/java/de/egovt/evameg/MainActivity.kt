package de.egovt.evameg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.util.Log

import android.widget.Button
import androidx.fragment.app.Fragment
import de.egovt.evameg.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(Home())
                R.id.profile -> startActivity(Intent(this, ProfileActivity::class.java))
                else -> {}
            }
            true
        }

        val newApplication = findViewById<FloatingActionButton>(R.id.new_application)
        newApplication.setOnClickListener {
            val Intent = Intent(this, NewApplication::class.java)
            startActivity(Intent)
        }

        // CHECK IN KEY VALUE STORE IF FIRST START
        val sets = SettingsStorage()
        sets.KeyValueStore(application)
        val notFirstStart:Boolean = sets.getBool("notFirstStart")
        Log.i("This is not the first start: ", notFirstStart.toString())

        // saved value across rebuilds and restarts
        if (!notFirstStart) {

            startActivity(Intent(this, SetupActivity::class.java))

        }
        val MapButton: Button =findViewById(R.id.mp_button)
        MapButton.setOnClickListener {
            //Navigate from one Activity to an other
            startActivity(Intent(this, MapviewActivity::class.java))
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
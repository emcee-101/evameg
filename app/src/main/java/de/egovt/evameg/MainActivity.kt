package de.egovt.evameg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.util.Log

import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        // saved value acroos rebuilds and restarts
        if (!notFirstStart) {

            startActivity(Intent(this, SetupActivity::class.java))

        }


        val profileButton: Button =findViewById(R.id.button)
        profileButton.setOnClickListener {
            //Navigate from one Activity to an other
          startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
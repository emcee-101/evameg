package de.egovt.evameg

import android.content.SharedPreferences
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import de.egovt.evameg.SettingsStorage
import android.widget.Button

class MainActivity : AppCompatActivity() {

    fun referToSetup(){
        val referrer: Intent = Intent(this, SetupActivity::class.java)
        startActivity(referrer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newApplication = findViewById<FloatingActionButton>(R.id.new_application)
        newApplication.setOnClickListener {
            val Intent = Intent(this, NewApplication::class.java)
            startActivity(Intent)
        }

        // CHECK IN KEY VALUE STORE IF FIRST START
        val sets:SettingsStorage = SettingsStorage();
        sets.KeyValueStore(application);
        val notFirstStart:Boolean = sets.getBool("notFirstStart");
        Log.i("a", notFirstStart.toString());

        // IF APP STARTED FOR FIRST TIME, referToSetup

        // THIS WORKS, BUT IS SAVED ACROSS REBUILDS! -> MAY BLOCK TESTS
        if (!notFirstStart) {

            // INTENT -> SetupActivity
            referToSetup();


        }
        // needed to test SETUP
        // else referToSetup();



        val profileButton: Button =findViewById(R.id.button)
        profileButton.setOnClickListener {
            //Navigate from one Activity to an other
          startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
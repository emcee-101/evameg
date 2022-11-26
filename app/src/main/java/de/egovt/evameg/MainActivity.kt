package de.egovt.evameg

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import de.egovt.evameg.SettingsStorage

import android.widget.Button


class MainActivity : AppCompatActivity() {

    fun referToSetup(){
        val referrer: Intent = Intent(this, SetupActivity::class.java)
        startActivity(referrer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        // Log.i("a", "MESSAGE");

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
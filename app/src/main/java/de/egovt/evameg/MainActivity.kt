package de.egovt.evameg

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import de.egovt.evameg.SettingsStorage


class MainActivity : AppCompatActivity() {

    fun referToSetup(){
        val referrer: Intent = Intent(this, SetupActivity::class.java)
        startActivity(referrer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        // Log.i("a", "MESSAGE");



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sets:SettingsStorage = SettingsStorage();
        sets.KeyValueStore(application);
        val notFirstStart:Boolean = sets.getBool("notFirstStart");
        Log.i("a", notFirstStart.toString());

        // if the app is started for the first time
        // double negation to allow for a negative default value (ask Niklas, if you dont know what that means)
        // THIS WORKS, BUT IS SAVED ACROSS REBUILDS! -> BLOCKS TESTS
        if (!notFirstStart) {
                // TODO REMEMBER TO SET THE VALUE TO POSITIVE AFTER THAT
                // store.writeIntValue(STORE_KEY_COUNTER, 42);
            // INTENT -> SetupActivity
            referToSetup();

            // TODO REMOVE ELSE CLAUSE WHEN NO TESTING IS NEEDED ANYMORE, TAUTOLOGY needed to guarantee testability
        } else referToSetup();


    }
}
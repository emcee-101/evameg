package de.egovt.evameg

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import de.egovt.evameg.SettingsStorage


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sets:SettingsStorage = SettingsStorage();
        sets.KeyValueStore(application);
        val notFirstStart:Boolean = sets.getBool("notFirstStart");

        // if the app is started for the first time
        // double negation to allow for a negative default value (ask Niklas, if you dont know what that means)
        if (!notFirstStart) {
                // REMEMBER TO SET THE VALUE TO POSITIVE AFTER THAT
                // store.writeIntValue(STORE_KEY_COUNTER, 42);

            // INTENT -> SetupActivity

        }


    }
}
package de.egovt.evameg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class SetupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

    // TODO ADD VIEWPAGE! -> needs Fragments tough


        val Butt: Button = findViewById(R.id.test_button);
        Butt.setOnClickListener {
           // val referrer: Intent = Intent(this, MainActivity::class.java)
          //  startActivity(referrer)
            Butt.text="BINBONG"
            val sets:SettingsStorage = SettingsStorage();
            sets.KeyValueStore(application);
            sets.writeBool("notFirstStart", true);
        }
    }
}

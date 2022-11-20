package de.egovt.evameg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newApplication = findViewById<FloatingActionButton>(R.id.new_application)
        newApplication.setOnClickListener {
            val Intent = Intent(this, NewApplication::class.java)
            startActivity(Intent)
        }

    }
}
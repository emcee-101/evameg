package de.egovt.evameg


import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import de.egovt.evameg.fragments.setup.ScreenSlidePageFragment1
import de.egovt.evameg.fragments.setup.ScreenSlidePageFragment2
import de.egovt.evameg.utility.customFragmentStateAdapter


class SetupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)


        Log.i("aaa", "Setup")

        val viewPagerInLayout:ViewPager2 = findViewById(R.id.viewPagerSetup);

        // Resource: https://medium.com/@huseyinozkoc/hello-welcome-to-my-article-dear-android-developers-and-dear-fox-a4c483f8a4ac

        val myAdapter: customFragmentStateAdapter = customFragmentStateAdapter(supportFragmentManager, lifecycle
        );

        myAdapter.addFragment(ScreenSlidePageFragment1());
        myAdapter.addFragment(ScreenSlidePageFragment2());

        viewPagerInLayout.orientation = ViewPager2.ORIENTATION_HORIZONTAL;

        viewPagerInLayout.adapter = myAdapter;


        // TODO Intent  to refer back to main

    }


    fun referToMain(){
        val referrer: Intent = Intent(this, MainActivity::class.java)
        startActivity(referrer)
    }


    fun onLeaving(){

        // TODO include in Flow

        // set Value to indicate complete app Setup to true
        val sets:SettingsStorage = SettingsStorage();
        sets.KeyValueStore(application);
        sets.writeBool("notFirstStart", true);

        referToMain();

    }
}

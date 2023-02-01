package de.egovt.evameg.activities


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import de.egovt.evameg.Fragments.setup.ScreenSlidePageFragment1
import de.egovt.evameg.Fragments.setup.ScreenSlidePageFragment2
import de.egovt.evameg.MainActivity
import de.egovt.evameg.R
import de.egovt.evameg.fragments.setup.ScreenSlidePageFragment3
import de.egovt.evameg.utility.SettingsStorage
import de.egovt.evameg.utility.UI.back
import de.egovt.evameg.utility.UI.customFragmentStateAdapter

/**
 * Activity that is displayed the first time the App is run.
 *
 * Displays swipeable fragments found in the evameg/fragments/setup directory
 *
 */
class SetupActivity : AppCompatActivity() {


    /**
     * Sets up the ViewPager2 to display the fragments and handles presses of the "Back" Button.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        Log.i("aaa", "Setup started")

        val viewPagerInLayout:ViewPager2 = findViewById(R.id.viewPagerSetup)
        val myAdapter = customFragmentStateAdapter(supportFragmentManager, lifecycle)

        myAdapter.addFragment(ScreenSlidePageFragment1())
        myAdapter.addFragment(ScreenSlidePageFragment3(application, application))
        myAdapter.addFragment(ScreenSlidePageFragment2())

        // Prepare Slider
        viewPagerInLayout.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPagerInLayout.adapter = myAdapter


        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {

            override fun handleOnBackPressed(){

                back(viewPagerInLayout)

            }

        })

    }

    fun referToMain(){
        onLeaving()
        val referrer = Intent(this, MainActivity::class.java)
        startActivity(referrer)
    }

    /**
     * Set Value that remembers if this was ran before to true, so that it doesnt get displayed next time.
     *
     */
    private fun onLeaving(){

        // set Value to indicate complete app Setup to true
        val sets = SettingsStorage()
        sets.KeyValueStore(application)
        sets.writeBool("notFirstStart", true)

        Log.i("aaa", "notFirstStart Value was set to ${sets.getBool("notFirstStart").toString()}")

    }




}

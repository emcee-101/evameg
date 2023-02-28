package de.egovt.evameg.activities

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import de.egovt.evameg.fragments.MapViewFragment
import de.egovt.evameg.utility.DB.DbHelper


/**
 * To test integration of the map in another activity. Not to be used in final App.
 *
 * @author Niklas Herzog
 */
class test_activity : AppCompatActivity() {

    lateinit var placeHolderForMap : FrameLayout
    lateinit var myMapFragment : Fragment
    lateinit var testButton : Button
    lateinit var testText : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(de.egovt.evameg.R.layout.test_activity)


        testButton = findViewById<Button>(de.egovt.evameg.R.id.test_button)
        testText = findViewById<TextView>(de.egovt.evameg.R.id.testText)

        val db :DbHelper = DbHelper(this)

        testText.text = db.readProposalData().count().toString()

        /*
        placeHolderForMap = findViewById<FrameLayout>(de.egovt.evameg.R.id.placeholder)

        testButton.setOnClickListener {

            dispatchMap("standesamt")

        }
        */
    }

    private fun dispatchMap(type:String){

        val ft : FragmentTransaction = supportFragmentManager.beginTransaction()
        myMapFragment = MapViewFragment(type) { x: Int -> receiveCallback(x) }
        ft.replace(de.egovt.evameg.R.id.placeholder, myMapFragment)
        ft.commit()

    }

    private fun receiveCallback(id:Int){

        val newString : String = "The ID of chosen Value is: $id"
        testText.text = newString

        val ft : FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.remove(myMapFragment)
        ft.commit()

    }


}
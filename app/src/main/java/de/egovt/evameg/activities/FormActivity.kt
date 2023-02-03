package de.egovt.evameg.activities

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import de.egovt.evameg.fragments.MapViewFragment

import de.egovt.evameg.R

class FormActivity : AppCompatActivity() {

    lateinit var myMapFragment : Fragment

    private val requiredFields = mapOf<Int, Array<Int>>(
        R.string.new_id_card        to arrayOf(R.string.office_location, R.string.body_height, R.string.eye_color),
        R.string.register_marriage  to arrayOf(R.string.office_location, R.string.partner_fullname),
        R.string.driving_licence_replacement   to arrayOf(),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        val matterOfConcernId  = intent.getIntExtra("mot_id",0)
        println("############################################$matterOfConcernId")
        val matterOfConcernName = intent.getStringExtra("mot_string") as String

        genericFormLayout(matterOfConcernName)
        for (key in requiredFields.keys){
            if (key != matterOfConcernId) continue
            for (item in requiredFields[key]!!){
                if (item == R.string.office_location){
                    addLocationChooser(getString(item))
                    continue
                }
                addEditText(getString(item))
            }

            break
        }
    }
    private fun genericFormLayout(title : String){
        val editLinearLayout = findViewById<LinearLayout>(R.id.container)

        val FormTitle = TextView(this)
        FormTitle.layoutParams  = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            )
        FormTitle.setPadding(10, 20, 0, 50)
        FormTitle.text = title
        FormTitle.textSize = 20f
        editLinearLayout.addView(FormTitle)
    }
    private fun addEditText(hint : String?){
        val editLinearLayout = findViewById<LinearLayout>(R.id.container)
        val editTextLayout = TextInputLayout( this,null, R.style.TextInputLayout)
        editTextLayout.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
        editTextLayout.setBoxCornerRadii(3f, 3f, 3f, 3f)
        editTextLayout.id = View.generateViewId()
        editTextLayout.helperText = getString(R.string.required)
        editTextLayout.counterMaxLength = 29
        editTextLayout.isCounterEnabled = true
        editTextLayout.setPadding(0, 40, 20, 0)

        val editText = TextInputEditText(editTextLayout.context)
        editText.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.hint = hint
        editTextLayout.addView(editText)
        editLinearLayout?.addView(editTextLayout)

    }
    private fun addLocationChooser(hint : String?){
        val editLinearLayout = findViewById<LinearLayout>(R.id.container)
        val editTextLayout = TextInputLayout( this,null, R.style.TextInputLayout)
        val placeHolderForMap = FrameLayout(this)
        placeHolderForMap.id = View.generateViewId()
        editTextLayout.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
        editTextLayout.setBoxCornerRadii(3f, 3f, 3f, 3f)
        editTextLayout.id = View.generateViewId()
        editTextLayout.helperText = getString(R.string.required)
        editTextLayout.counterMaxLength = 29
        editTextLayout.isCounterEnabled = true
        editTextLayout.setPadding(0, 40, 20, 0)
        val editText = TextInputEditText(editTextLayout.context)
        editText.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.focusable = View.NOT_FOCUSABLE
        editText.setOnClickListener {dispatchMap(placeHolderForMap.id,editText,"standesamt",)}
        editText.hint = hint //"Open Location Chooser..."
        editTextLayout.addView(editText)
        editLinearLayout?.addView(editTextLayout)

    }
    private fun dispatchMap(parentID:Int, editText: TextInputEditText, type:String){

        //val ft : FragmentTransaction = supportFragmentManager.beginTransaction()
        myMapFragment = MapViewFragment(type) { x: Int -> receiveCallback(x,editText) }
        (myMapFragment as MapViewFragment).show((this as AppCompatActivity).supportFragmentManager,"showPopUp")
        //ft.replace(parentID, myMapFragment)
        //ft.commit()

    }

    private fun receiveCallback(id:Int, editText: TextInputEditText){

        editText.setText("The chosen location ID is: $id")
        //val ft : FragmentTransaction = supportFragmentManager.beginTransaction()
        //ft.remove(myMapFragment)
        //ft.commit()

    }

}
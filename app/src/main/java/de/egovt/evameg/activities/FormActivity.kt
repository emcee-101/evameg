package de.egovt.evameg.activities

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import de.egovt.evameg.fragments.MapViewFragment
import de.egovt.evameg.R
import de.egovt.evameg.utility.DB.DbHelper
import de.egovt.evameg.utility.ProposalData
import java.text.DateFormat.getDateInstance
import java.util.*

/**
 * @author Mohammad Zidane
 */
class FormActivity : AppCompatActivity() {

    // mappings for the office type as in the databank
    val locationTypeMap = mapOf(
        R.string.location_finanzamt to "finanzamt",
        R.string.location_foreigners_office to "auslaenderamt",
        R.string.location_sozialamt to "sozialamt",
        R.string.location_standesamt to "standesamt",
    )
    private lateinit var myMapFragment : Fragment
    val db = DbHelper(this)

    // mapping every Proposal/Application to its required fields
    private val requiredFields = mapOf<Int, Array<Int>>(
        R.string.new_id_card        to arrayOf(R.string.location_standesamt, R.string.body_height, R.string.eye_color),
        R.string.register_marriage  to arrayOf(R.string.location_finanzamt,R.string.tax_id, R.string.partner_fullname, R.string.partner_birthdate, R.string.partner_tax_id),
        R.string.new_passport to arrayOf(R.string.location_standesamt,R.string.body_height, R.string.eye_color, R.string.old_passport_number),
        R.string.new_aufenthaltstitel  to arrayOf(R.string.location_foreigners_office, R.string.body_height, R.string.eye_color),
        R.string.driving_licence_replacement   to arrayOf(R.string.location_standesamt, R.string.driver_license_id, R.string.driver_license_expiry_date),
        R.string.apply_ElektronischeLohnsteuerkarte to arrayOf(R.string.location_finanzamt, R.string.tax_id, R.string.employeeID),
        R.string.extend_passport to arrayOf(R.string.location_standesamt, R.string.passport_number),
        R.string.register_new_residence to arrayOf(R.string.location_standesamt, R.string.new_street_address, R.string.new_postal_code, R.string.city, R.string.state, R.string.country),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        // Getting the chosen Proposal/Application from the previous application
        val matterOfConcernId  = intent.getIntExtra("mot_id",0)
        val matterOfConcernName = intent.getStringExtra("mot_string") as String
        genericFormLayout(matterOfConcernName)
        for (key in requiredFields.keys){
            if (key != matterOfConcernId) continue
            for (item in requiredFields[key]!!){
                // Fields where the input is the location of an office will be handeled by
                // map location chooser
                if (item in listOf(R.string.location_standesamt,R.string.location_finanzamt, R.string.location_sozialamt, R.string.location_foreigners_office)){
                    addLocationChooser(item)
                    continue
                }
                // the rest are standard text input fields (EditText)
                addEditText(getString(item))
            }
            break
        }
        val signedInAsText = findViewById<TextView>(R.id.signedInAsText)
        val userFullName = "${db.readUserData()[0].firstName} ${db.readUserData()[0].lastName}"
        val userBirthDate = db.readUserData()[0].dateOfBirth
        signedInAsText.text = getString(R.string.signedInAs, userFullName)
        val submitBtn = findViewById<Button>(R.id.submitBtn)
        submitBtn.setOnClickListener {

            WineDialog(this).show(getString(R.string.submitApplicationPromptTitle),
                getString(R.string.submitApplicationPromptMsg, userFullName,userBirthDate)) {

                if (it == WineDialog.ResponseType.YES){
                    val currentDate =  getDateInstance().format(Date()).toString()
                    val proposalData = ProposalData(matterOfConcernName,currentDate,"processing")
                    insertProposalData(proposalData)
                    Toast.makeText(applicationContext,getString(R.string.applicationSentMsg),Toast.LENGTH_LONG).show()
                }
            }
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
    // add EditText programmatically
    private fun addEditText(hint : String?){
        val editLinearLayout = findViewById<LinearLayout>(R.id.container)
        val editTextLayout = TextInputLayout( this,null, R.style.TextInputLayout)
        editTextLayout.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
        editTextLayout.setBoxCornerRadii(3f, 3f, 3f, 3f)
        editTextLayout.id = View.generateViewId()
        //editTextLayout.helperText = getString(R.string.required)
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
    // location chooser is a special EditText that calls location chooser dialog fragment and
    // is not directly editable by the user
    private fun addLocationChooser(location : Int?){
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
        editText.setOnClickListener {dispatchMap(editText,locationTypeMap[location],)}
        editText.hint = getString(location!!) //"Open Location Chooser..."
        editTextLayout.addView(editText)
        editLinearLayout?.addView(editTextLayout)

    }
    private fun dispatchMap(editText: TextInputEditText, type: String?){

        //val ft : FragmentTransaction = supportFragmentManager.beginTransaction()
        myMapFragment = MapViewFragment(type!!) { x: Int -> receiveCallback(x,editText) }
        (myMapFragment as MapViewFragment).show((this as AppCompatActivity).supportFragmentManager,"showPopUp")
        //ft.replace(parentID, myMapFragment)
        //ft.commit()

    }

    private fun receiveCallback(id:Int, editText: TextInputEditText){

        editText.setText("$id")
        //val ft : FragmentTransaction = supportFragmentManager.beginTransaction()
        //ft.remove(myMapFragment)
        //ft.commit()

    }

    /**
     * Calls Database to read all Proposals history
     *
     * @return list of Proposals
     */
    private fun insertProposalData(proposalData : ProposalData){

        val db = DbHelper(this)
        db.insertProposalData(proposalData)
    }
    class WineDialog(context: Context) : AlertDialog.Builder(context) {

        lateinit var onResponse: (r : ResponseType) -> Unit

        enum class ResponseType {
            YES, NO
        }

        fun show(title: String, message: String, listener: (r : ResponseType) -> Unit) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            onResponse = listener

            // performing positive action
            builder.setPositiveButton(R.string.submit) { _, _ ->
                onResponse(ResponseType.YES)

            }

            // performing negative action
            builder.setNegativeButton(R.string.cancel) { _, _ ->
                onResponse(ResponseType.NO)
            }

            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()

            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

}
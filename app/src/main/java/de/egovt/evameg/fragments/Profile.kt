package de.egovt.evameg.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import de.egovt.evameg.R
import de.egovt.evameg.utility.DB.DbHelper
import de.egovt.evameg.utility.UserProfileData
import de.egovt.evameg.utility.UI.ProfileEditor

/**
 * A Fragment that displays the user profile
 *
 *  Queries firstname, lastname, date of birth, domicile, postal code and street over the ID.
 *
 */
class Profile : Fragment() {


    // for Permissions
    private lateinit var thisProfileView : View
    private lateinit var momentaryProfileContext : Context
    private lateinit var editor : ProfileEditor

    private var profile : UserProfileData? = null

    private var textViewId: TextView? = null
    private var textViewFirstName: TextView? = null
    private var textViewLastName: TextView? = null
    private var textViewDateOfBirth: TextView? = null
    private var textViewWohnort: TextView? = null
    private var textViewPostalCode: TextView? = null
    private var textViewStreet: TextView? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        thisProfileView = inflater.inflate(R.layout.activity_profile, container, false)
        return thisProfileView

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        momentaryProfileContext = context

    }


    /**
     * button that opens layout dialog popup, where users can input their user data.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editor = ProfileEditor(momentaryProfileContext, layoutInflater)

        val dataEditButton: Button = thisProfileView.findViewById(R.id.button_edit_Data)
        dataEditButton.setOnClickListener{
            showEditTextDialog()
        }
    }

    /**
     reads user data, return it in textview
     */
    override fun onResume() {
        super.onResume()

        textViewId = thisProfileView.findViewById(R.id.textViewId)
        textViewFirstName = thisProfileView.findViewById(R.id.textView_firstName)
        textViewLastName = thisProfileView.findViewById(R.id.textView_lastName)
        textViewDateOfBirth = thisProfileView.findViewById(R.id.textView_dateOfBirth)
        textViewWohnort = thisProfileView.findViewById(R.id.textView_wohnort)
        textViewPostalCode = thisProfileView.findViewById(R.id.textView_postalCode)
        textViewStreet = thisProfileView.findViewById(R.id.textView_street)

        refreshStrings()

    }

    /**
     * user insert data in edit text fields which is transformed in text view, then saved as user profile data.
     * if success, inserted in db.
     */
    private fun showEditTextDialog() {

        // call refresh after Dialog ended
        editor.showDataDialog { refreshStrings() }
    }

    private fun refreshStrings() {

        var db = DbHelper(momentaryProfileContext)

        profile = db.readUserData()[0]


        textViewId?.text =          profile!!.id.toString()
        textViewFirstName?.text =   profile!!.firstName
        textViewLastName?.text =    profile!!.lastName
        textViewDateOfBirth?.text = profile!!.dateOfBirth
        textViewWohnort?.text =     profile!!.wohnort
        textViewPostalCode?.text =  profile!!.postalCode
        textViewStreet?.text =      profile!!.street

    }
}


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


class Profile : Fragment() {
    // TODO: Rename and change types of parameters

   // for Permissions
    private lateinit var thisProfileView : View
    private lateinit var momentaryProfileContext : Context
    var db = DbHelper(context)
    val textViewId: TextView = thisProfileView.findViewById(R.id.textViewId)
    val textViewFirstName: TextView = thisProfileView.findViewById(R.id.textView_firstName)
    val textViewLastName: TextView = thisProfileView.findViewById(R.id.textView_lastName)
    val textViewDateOfBirth: TextView = thisProfileView.findViewById(R.id.textView_dateOfBirth)
    val textViewWohnort: TextView = thisProfileView.findViewById(R.id.textView_wohnort)
    val textViewPostalCode: TextView = thisProfileView.findViewById(R.id.textView_postalCode)
    val textViewStreet: TextView = thisProfileView.findViewById(R.id.textView_street)
    //private var param1: String? = null
   // private var param2: String? = null

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        thisProfileView=inflater.inflate(R.layout.activity_profile, container, false)// thisProfileView=Inflater. ... R.layout.activityProfile
        return thisProfileView

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        momentaryProfileContext = context

    }



     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

}

override fun onStart() {
    super.onStart()
    val dataEditButton: Button = thisProfileView.findViewById(R.id.button_edit_Data)
    dataEditButton.setOnClickListener{
        showEditTextDialog()
    }



}

override fun onResume() {
    super.onResume()

    var data=db.readUserData()
    when {
        data.isNotEmpty() -> {
            for (i in 0..(data.size - 1)) {

                textViewId.append(data[i].id.toString())
                textViewFirstName.append(data[i].firstName)
                textViewLastName.append(data[i].lastName)
                textViewDateOfBirth.append(data[i].dateOfBirth)
                textViewWohnort.append(data[i].wohnort)
                textViewPostalCode.append(data[i].postalCode)
                textViewStreet.append(data[i].street)
            }
        }
    }
}
fun showEditTextDialog() {

    val builder = AlertDialog.Builder(momentaryProfileContext)
    val inflater: LayoutInflater = layoutInflater
    val dialogLayout: View = inflater.inflate(R.layout.layout_dialog_popup, null)
    val editTextFirstName: EditText = dialogLayout.findViewById(R.id.edit_firstName)
    val editTextLastName: EditText = dialogLayout.findViewById(R.id.edit_lastName)
    val editTextDateOfBirth: EditText = dialogLayout.findViewById(R.id.edit_dateOfBirth)
    val editTextWohnort: EditText = dialogLayout.findViewById(R.id.edit_wohnort)
    val editTextPostalCode: EditText = dialogLayout.findViewById(R.id.edit_postalCode)
    val editTextStreet: EditText = dialogLayout.findViewById(R.id.edit_street)



    var userProfileData = UserProfileData(
        editTextFirstName.text.toString(),
        editTextLastName.text.toString(),
        editTextDateOfBirth.text.toString(),
        editTextWohnort.text.toString(),
        editTextPostalCode.text.toString(), //oder hinter toString().toInt?
        editTextStreet.text.toString()

    )


    with(builder) {
        setTitle(R.string.builder_profile_activity_title)
        setPositiveButton(R.string.profile_builder_ok) { dialog, which ->
            textViewFirstName.text=editTextFirstName.text.toString()
            textViewLastName.text=editTextLastName.text.toString()
            textViewDateOfBirth.text=editTextDateOfBirth.text.toString()
            textViewWohnort.text=editTextWohnort.text.toString()
            textViewPostalCode.text=editTextPostalCode.text.toString()
            textViewStreet.text=editTextStreet.text.toString()

            userProfileData.firstName=textViewFirstName.text.toString()
            userProfileData.lastName=textViewLastName.text.toString()
            userProfileData.dateOfBirth=textViewDateOfBirth.text.toString()
            userProfileData.wohnort=textViewWohnort.text.toString()
            userProfileData.postalCode=textViewPostalCode.text.toString()
            userProfileData.street=textViewStreet.text.toString()

            Toast.makeText(context, R.string.profile_builder_toast_success, Toast.LENGTH_SHORT).show()
            db.insertUserData(userProfileData)

        }


        setNegativeButton(R.string.profile_builder_return) { dialog, which ->
            Log.d("Main", "Negative button clicked")
        }
        setView(dialogLayout)
        show()
    }
}


}
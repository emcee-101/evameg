package de.egovt.evameg.utility.UI

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import de.egovt.evameg.R
import de.egovt.evameg.utility.DB.DbHelper
import de.egovt.evameg.utility.UserProfileData


/**
 * Dialog that is shown when User Data is inserted in the Database.
 *
 * @author Celina Ludwigs, Niklas Herzog
 */
class ProfileEditor() {

        private lateinit var inflater : LayoutInflater
        private lateinit var context : Context

        constructor(context:Context, inflater: LayoutInflater) : this (){
            this.inflater = inflater
            this.context = context
        }

        /**
         * user insert data in edit text fields which is transformed in text view, then saved as user profile data.
         * if success, inserted in db.
         */
        public fun showDataDialog(callback : ()->Unit) {

            // inflate layout and get EditTexts
            val dialogLayout: View = inflater.inflate(R.layout.layout_dialog_popup, null)

            val editTextFirstName: EditText = dialogLayout.findViewById(R.id.edit_firstName)
            val editTextLastName: EditText = dialogLayout.findViewById(R.id.edit_lastName)
            val editTextDateOfBirth: EditText = dialogLayout.findViewById(R.id.edit_dateOfBirth)
            val editTextWohnort: EditText = dialogLayout.findViewById(R.id.edit_wohnort)
            val editTextPostalCode: EditText = dialogLayout.findViewById(R.id.edit_postalCode)
            val editTextStreet: EditText = dialogLayout.findViewById(R.id.edit_street)

            // read old data
            var db = DbHelper(context)
            val profile : UserProfileData = db.readUserData()[0]

            // insert into edits to make changes easier
            editTextFirstName.text.append(profile.firstName)
            editTextLastName.text.append(profile.lastName)
            editTextDateOfBirth.text.append(profile.dateOfBirth)
            editTextWohnort.text.append(profile.wohnort)
            editTextPostalCode.text.append(profile.postalCode)
            editTextStreet.text.append(profile.street)

            var userProfileData = UserProfileData(
                editTextFirstName.text.toString(),
                editTextLastName.text.toString(),
                editTextDateOfBirth.text.toString(),
                editTextWohnort.text.toString(),
                editTextPostalCode.text.toString(),
                editTextStreet.text.toString()

            )

            val builder = AlertDialog.Builder(context)

            // build the editor dialog
            with(builder) {
                setTitle(R.string.builder_profile_activity_title)
                setPositiveButton(R.string.profile_builder_ok) { dialog, which ->

                    // put new data in structure
                    userProfileData.firstName = editTextFirstName.text.toString()
                    userProfileData.lastName = editTextLastName.text.toString()
                    userProfileData.dateOfBirth = editTextDateOfBirth.text.toString()
                    userProfileData.wohnort = editTextWohnort.text.toString()
                    userProfileData.postalCode = editTextPostalCode.text.toString()
                    userProfileData.street = editTextStreet.text.toString()

                    //insert the data in db
                    db.insertUserData(userProfileData)
                    Toast.makeText(context, R.string.profile_builder_toast_success, Toast.LENGTH_SHORT).show()

                    // call the callback if passed
                    if(callback != null) callback()

                }


                setNegativeButton(R.string.profile_builder_return) { dialog, which ->
                    Log.d("Main", "Negative button clicked")
                }
                setView(dialogLayout)
                show()
            }
        }
}
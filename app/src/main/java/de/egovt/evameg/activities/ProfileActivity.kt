package de.egovt.evameg.activities

import android.app.AlertDialog
import android.app.ProgressDialog.show

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.egovt.evameg.utility.DbHelper
import de.egovt.evameg.R
import de.egovt.evameg.utility.UserProfileData
import de.egovt.evameg.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    var db = DbHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //if-Abfrage? Ziel:immer die Daten in Textview anzeigen lassen, wenn sie in der DB sind



    }

    override fun onStart() {
        super.onStart()
        val dataEditButton: Button = findViewById(R.id.button_edit_Data)
        dataEditButton.setOnClickListener{
            showEditTextDialog()
        }


    }

    override fun onResume() {
        super.onResume()
        var data=db.readUserData()
        for (i in 0..(data.size - 1)) {
            binding.textViewId.append(data[i].id.toString())
            binding.textViewFirstName.append(data[i].firstName)
            binding.textViewLastName.append(data[i].lastName)
            binding.textViewDateOfBirth.append(data[i].dateOfBirth)
            binding.textViewWohnort.append(data[i].wohnort)
            binding.textViewPostalCode.append(data[i].postalCode)
            binding.textViewStreet.append(data[i].street)
        }

    }
    fun showEditTextDialog() {

       val builder = AlertDialog.Builder(this)
       val inflater: LayoutInflater = layoutInflater
       val dialogLayout: View = inflater.inflate(R.layout.layout_dialog_popup, null)
       val editTextFirstName: EditText = dialogLayout.findViewById(R.id.edit_firstName)
       val editTextLastName: EditText = dialogLayout.findViewById(R.id.edit_lastName)
       val editTextDateOfBirth: EditText = dialogLayout.findViewById(R.id.edit_dateOfBirth)
       val editTextWohnort: EditText = dialogLayout.findViewById(R.id.edit_wohnort)
       val editTextPostalCode: EditText = dialogLayout.findViewById(R.id.edit_postalCode)
       val editTextStreet: EditText = dialogLayout.findViewById(R.id.edit_street)

       val textViewFirstName: TextView = findViewById(R.id.textView_firstName)
       val textViewLastName: TextView = findViewById(R.id.textView_lastName)
       val textViewDateOfBirth: TextView = findViewById(R.id.textView_dateOfBirth)
       val textViewWohnort: TextView = findViewById(R.id.textView_wohnort)
       val textViewPostalCode: TextView = findViewById(R.id.textView_postalCode)
       val textViewStreet: TextView = findViewById(R.id.textView_street)
       val textViewId: TextView = findViewById(R.id.textViewId)


        var userProfileData = UserProfileData(
            editTextFirstName.toString(),
            editTextLastName.toString(),
            editTextDateOfBirth.toString(),
            editTextWohnort.toString(),
            editTextPostalCode.toString(),
            editTextStreet.toString()
        )

       with(builder) {
           setTitle("Daten bearbeiten")
           setPositiveButton("Ok") { dialog, which ->

               if (editTextFirstName.text.toString().isNotEmpty() &&
                   editTextLastName.text.toString().isNotEmpty() &&
                   editTextDateOfBirth.text.toString().isNotEmpty() &&
                   editTextWohnort.text.toString().isNotEmpty() &&
                   editTextPostalCode.text.toString().isNotEmpty() &&
                   editTextStreet.text.toString().isNotEmpty()
               ) {



                   /* userProfileData.firstName=editTextFirstName.text.toString()
                userProfileData.lastName=editTextLastName.text.toString()
                userProfileData.dateOfBirth=editTextDateOfBirth.text.toString()
                userProfileData.wohnort=editTextWohnort.text.toString()
                userProfileData.postalCode=editTextPostalCode.text.toString()
                userProfileData.street=editTextStreet.text.toString()*/


                   textViewFirstName.text = userProfileData.firstName
                   textViewLastName.text = userProfileData.lastName
                   textViewDateOfBirth.text = userProfileData.dateOfBirth
                   textViewWohnort.text = userProfileData.wohnort
                   textViewPostalCode.text = userProfileData.postalCode
                   textViewStreet.text = userProfileData.street
                   textViewId.text = userProfileData.id.toString()


                   db.insertUserData(userProfileData)
               } else {
                   Toast.makeText(context, "Please Fill all Data´s", Toast.LENGTH_SHORT).show()
               }
               setNegativeButton("cancel") { dialog, which ->
                   Log.d("Main", "Negative button clicked")
               }
               setView(dialogLayout)
               show()
           }
       }
   }
}

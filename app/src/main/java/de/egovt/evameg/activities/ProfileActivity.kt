package de.egovt.evameg.activities

import android.app.AlertDialog


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
import de.egovt.evameg.utility.DBDataStructures
import de.egovt.evameg.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    var db = DbHelper(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)



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
       when {
           data.isNotEmpty() -> {
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

        var userProfileData = DBDataStructures(
            editTextFirstName.text.toString(),
            editTextLastName.text.toString(),
            editTextDateOfBirth.text.toString(),
            editTextWohnort.text.toString(),
            editTextPostalCode.text.toString(), //oder hinter toString().toInt?
            editTextStreet.text.toString()

        )


       with(builder) {
           setTitle("Daten bearbeiten")
           setPositiveButton("Ok") { dialog, which ->
               textViewFirstName.text=editTextFirstName.text.toString()
               textViewLastName.text=editTextLastName.text.toString()
               textViewDateOfBirth.text=editTextDateOfBirth.text.toString()
               textViewWohnort.text=editTextWohnort.text.toString()
               textViewPostalCode.text=editTextPostalCode.text.toString()
               textViewStreet.text=editTextStreet.text.toString()
//userProfilData und TextView tauschen?
               userProfileData.firstName=textViewFirstName.text.toString()
               userProfileData.lastName=textViewLastName.text.toString()
               userProfileData.dateOfBirth=textViewDateOfBirth.text.toString()
               userProfileData.wohnort=textViewWohnort.text.toString()
               userProfileData.postalCode=textViewPostalCode.text.toString()
               userProfileData.street=textViewStreet.text.toString()

               Toast.makeText(context, "Data inserted", Toast.LENGTH_SHORT).show()
               db.insertUserData(userProfileData)

           }


           setNegativeButton("cancel") { dialog, which ->
                   Log.d("Main", "Negative button clicked")
               }
               setView(dialogLayout)
               show()
           }
       }
   }


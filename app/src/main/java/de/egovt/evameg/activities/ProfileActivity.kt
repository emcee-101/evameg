package de.egovt.evameg.activities

import android.app.AlertDialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import de.egovt.evameg.utility.DbHelper
import de.egovt.evameg.R
import de.egovt.evameg.utility.UserProfileData
import de.egovt.evameg.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    val context = this
    var db = DbHelper(context)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
//if-Abfrage? Ziel:immer die Daten in Textviev anzeigen lassen, wenn sie in der DBsind
        var data=db.readUserData()
        for (i in 0..(data.size-1)){

            binding.textViewFirstName.append(data.get(1).firstName)
            binding.textViewLastName.append(data.get(2).lastName)
            binding.textViewDateOfBirth.append(data.get(3).dateOfBirth)
            binding.textViewWohnort.append(data.get(4).wohnort)
            binding.textViewPostalCode.append(data.get(5).postalCode)
            binding.textViewStreet.append(data.get(6).street)
        }


    }

    override fun onStart() {
        super.onStart()
        val dataEditButton: Button = findViewById(R.id.button_edit_Data)
        dataEditButton.setOnClickListener{
            showEditTextDialog()
        }
    }

    fun showEditTextDialog() {

        val builder=AlertDialog.Builder(this)
        val inflater:LayoutInflater=layoutInflater
        val dialogLayout:View=inflater.inflate(R.layout.layout_dialog_popup,null)
        val editTextFirstName:EditText=dialogLayout.findViewById(R.id.edit_firstName)
        val editTextLastName:EditText=dialogLayout.findViewById(R.id.edit_lastName)
        val editTextDateOfBirth:EditText=dialogLayout.findViewById(R.id.edit_dateOfBirth)
        val editTextWohnort:EditText=dialogLayout.findViewById(R.id.edit_wohnort)
        val editTextPostalCode:EditText=dialogLayout.findViewById(R.id.edit_postalCode)
        val editTextStreet:EditText=dialogLayout.findViewById(R.id.edit_street)

        val textViewFirstName: TextView = findViewById(R.id.textView_firstName)
        val textViewLastName: TextView = findViewById(R.id.textView_lastName)
        val textViewDateOfBirth: TextView = findViewById(R.id.textView_dateOfBirth)
        val textViewWohnort: TextView = findViewById(R.id.textView_wohnort)
        val textViewPostalCode: TextView = findViewById(R.id.textView_postalCode)
        val textViewStreet: TextView = findViewById(R.id.textView_street)


        with(builder){
            setTitle("Daten bearbeiten")
            setPositiveButton("Ok") { dialog, which ->
                textViewFirstName.text=editTextFirstName.text.toString()
                textViewLastName.text=editTextLastName.text.toString()
                textViewDateOfBirth.text=editTextDateOfBirth.text.toString()
                textViewWohnort.text=editTextWohnort.text.toString()
                textViewPostalCode.text=editTextPostalCode.text.toString()
                textViewStreet.text=editTextStreet.text.toString()

                var userProfileData= UserProfileData(textViewFirstName.toString(),
                                textViewLastName.toString(),
                                textViewDateOfBirth.toString(),
                                textViewWohnort.toString(),
                                textViewPostalCode.toString(),
                                textViewStreet.toString())

                db.insertUserData(userProfileData)
            }
            setNegativeButton("cancel"){dialog,which->
                Log.d("Main","Negative button clicked")
            }
            setView(dialogLayout)
            show()
        }
    }
}

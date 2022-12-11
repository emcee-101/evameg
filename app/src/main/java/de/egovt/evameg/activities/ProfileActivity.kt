package de.egovt.evameg.activities

import android.annotation.SuppressLint
import android.app.AlertDialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import de.egovt.evameg.R
import de.egovt.evameg.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

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
    @SuppressLint("InflateParams")
    fun showEditTextDialog() {

        val builder=AlertDialog.Builder(this)
        val inflater:LayoutInflater=layoutInflater
        val dialogLayout:View=inflater.inflate(R.layout.layout_dialog_popup,null)
        val editText1:EditText=dialogLayout.findViewById(R.id.edit_firstName)
        val editText2:EditText=dialogLayout.findViewById(R.id.edit_lastName)
        val editText3:EditText=dialogLayout.findViewById(R.id.edit_dateOfBirth)
        val editText4:EditText=dialogLayout.findViewById(R.id.edit_wohnort)
        val editText5:EditText=dialogLayout.findViewById(R.id.edit_postalCode)
        val editText6:EditText=dialogLayout.findViewById<EditText>(R.id.edit_street)

        val textViewFirstName: TextView = findViewById(R.id.textView_firstName)
        val textViewLastName: TextView = findViewById(R.id.textView_lastName)
        val textViewDateOfBirth: TextView = findViewById(R.id.textView_dateOfBirth)
        val textViewWohnort: TextView = findViewById(R.id.textView_wohnort)
        val textViewPostalCode: TextView = findViewById(R.id.textView_postalCode)
        val textViewStreet: TextView = findViewById(R.id.textView_street)

        with(builder){
            setTitle("Daten bearbeiten")
            setPositiveButton("Ok") { dialog, which ->
                textViewFirstName.text=editText1.text.toString()
                textViewLastName.text=editText2.text.toString()
                textViewDateOfBirth.text=editText3.text.toString()
                textViewWohnort.text=editText4.text.toString()
                textViewPostalCode.text=editText5.text.toString()
                textViewStreet.text=editText6.text.toString()
            }
            setNegativeButton("cancel"){dialog,which->
                Log.d("Main","Negative button clicked")
            }
            setView(dialogLayout)
            show()
        }
    }
}


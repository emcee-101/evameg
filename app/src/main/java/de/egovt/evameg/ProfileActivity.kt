package de.egovt.evameg

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import de.egovt.evameg.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textViewFirstName:TextView= findViewById(R.id.textView_firstName)
        val textViewLastName:TextView= findViewById(R.id.textView_lastName)
        val textViewDateOfBirth:TextView= findViewById(R.id.textView_dateOfBirth)
        val textViewWohnort:TextView= findViewById(R.id.textView_wohnort)
        val textViewPostalCode:TextView= findViewById(R.id.textView_postalCode)
        val textViewStreet:TextView= findViewById(R.id.textView_street)
        val dataEditButton:Button=findViewById(R.id.button_edit_Data)
        dataEditButton.setOnClickListener(View.OnClickListener(){
          openDialog()
        })

    }
    fun openDialog(){

    }
}
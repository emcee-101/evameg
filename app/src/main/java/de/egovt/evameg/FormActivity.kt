package de.egovt.evameg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout


class FormActivity : AppCompatActivity() {
    private lateinit var matter_of_concern : String;
    private val requiredFields = mapOf(
        "new_id_card"                   to arrayOf("office_location", "body_height", "eye_color"),
        "register_marriage"             to arrayOf("office_location","partner_firstname", "partner_lastname"),
        "driving_licence_replacement"   to arrayOf(""),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        for (key in requiredFields.keys){
            if (key != matter_of_concern) continue
            for (item in requiredFields[key]!!){
                addItem(item)
            }
            break
        }
    }
    private fun addItem(hint : String){
        val editLinearLayout = findViewById<LinearLayout>(R.id.container)
        val editText = EditText(this)
        editText.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.setPadding(20, 20, 20, 20)
        editText.hint = hint
        editLinearLayout?.addView(editText)
    }
}
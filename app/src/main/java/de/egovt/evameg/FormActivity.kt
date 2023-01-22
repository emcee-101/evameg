package de.egovt.evameg

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class FormActivity : AppCompatActivity() {
    private val requiredFields = mapOf(
        "new_id_card"                   to arrayOf("office_location", "body_height", "eye_color"),
        "register_marriage"             to arrayOf("office_location","partner_firstname", "partner_lastname"),
        "driving_licence_replacement"   to arrayOf(""),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        val matterOfConcernId = intent.getStringExtra("mot_id") as String
        val matterOfConcernName = intent.getStringExtra("mot_string") as String

        genericFormLayout(matterOfConcernName)
        for (key in requiredFields.keys){
            if (key != matterOfConcernId) continue
            for (item in requiredFields[key]!!){
                addEditText(item)
            }
            break
        }
    }
    private fun genericFormLayout(title : String){
        val editLinearLayout = findViewById<LinearLayout>(R.id.container)
        val FormTitle = TextView(this)
        FormTitle.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        FormTitle.setPadding(10, 20, 0, 50)
        FormTitle.text = title
        FormTitle.textSize = 20f
        editLinearLayout.addView(FormTitle)
    }
    private fun addEditText(hint : String){
        val editLinearLayout = findViewById<LinearLayout>(R.id.container)
        val editTextLayout = TextInputLayout( ContextThemeWrapper(this, R.style.TextInputLayout),null,0)
        editTextLayout.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        val newEditTextLayoutID = View.generateViewId()
        editTextLayout.id = newEditTextLayoutID
        editTextLayout.helperText = getString(R.string.required)
        editTextLayout.counterMaxLength = 29
        editTextLayout.isCounterEnabled = true

        editLinearLayout?.addView(editTextLayout)

        val editText = TextInputEditText(this)
        editText.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.setPadding(20, 20, 20, 20)
        editText.hint = hint

        val editTextLayoutView = findViewById<LinearLayout>(newEditTextLayoutID)
        editTextLayoutView?.addView(editText)
    }
}
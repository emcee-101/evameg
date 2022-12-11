package de.egovt.evameg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout


class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

    }
    private fun addItem(hint : String){
        val editLinearLayout = findViewById<LinearLayout>(R.id.container)
        val view = findViewById<LinearLayout>(R.id.dynamic_edit_text)
        val editText = EditText(this)
        editText.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.setPadding(20, 20, 20, 20)
        editText.hint = hint
        editLinearLayout?.addView(editText)
        view.addView(editText)

    }
}
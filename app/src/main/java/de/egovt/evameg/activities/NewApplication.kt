package de.egovt.evameg.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import de.egovt.evameg.R
import de.egovt.evameg.databinding.ActivityNewApplicationBinding

/**
 * @author Mohammad Zidane
 */
class NewApplication : AppCompatActivity() {

    lateinit var binding: ActivityNewApplicationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val applicationsList = arrayOf(
            R.string.register_new_residence,
            R.string.new_id_card,
            R.string.new_passport,
            R.string.new_aufenthaltstitel,
            R.string.apply_ElektronischeLohnsteuerkarte,
            R.string.extend_passport,
            R.string.register_marriage,
            R.string.driving_licence_replacement)

        val applicationDict = mutableMapOf<String, Int>()
        applicationsList.forEach { item -> applicationDict[getString(item)] = item }
        println(applicationDict)
        val appDictAdapter : ArrayAdapter<String> = ArrayAdapter(
            this,android.R.layout.simple_list_item_1,
            applicationDict.keys.toList()
        )
        binding.applicationList.adapter = appDictAdapter
        val appList: ListView = findViewById(R.id.applicationList)
        appList.adapter = appDictAdapter
        appList.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as String
            val intent = Intent(this, FormActivity::class.java)
            intent.putExtra("mot_id", applicationDict[selectedItem])
            intent.putExtra("mot_string", selectedItem)
            startActivity(intent)
        }


        binding.searchApplication.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                appDictAdapter.filter.filter(newText)
                return false
            }
        })

    }
}
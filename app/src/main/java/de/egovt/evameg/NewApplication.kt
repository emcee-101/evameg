package de.egovt.evameg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import de.egovt.evameg.databinding.ActivityNewApplicationBinding

class NewApplication : AppCompatActivity() {

    lateinit var binding: ActivityNewApplicationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val applicationsList = arrayOf("Wohnsitz melden","Einbürgerung beantragen","Personalausweis beantragen","Reisepass beantragen","Reisepass verlängern","Einwohnerangelegenheiten","Heirat beantragen","Befreiung von der Ausweispflicht beantragen", "Kindergeld beantragen","Abschiebung beantragen", "Aufenthaltstitel verlängern","Ersatzführerschein beantragen", "Fahrerlaubnis - Begleitetes Fahren ab 17", "Fahrerlaubnis - Bus - Klassen D1, D1E, D, DE", "Fahrerqualifikationsnachweis", "Kfz-Zulassung", "eiD-Karte", )

        val appListAdapter : ArrayAdapter<String> = ArrayAdapter(
            this,android.R.layout.simple_list_item_1,
            applicationsList
        )
        binding.applicationList.adapter = appListAdapter;
        val appList: ListView = findViewById(R.id.applicationList)
        appList.adapter = appListAdapter
        appList.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as String
            //textView.text = "Printing out $selectedItem"
            startActivity(Intent(this, FormActivity::class.java))
        }


        binding.searchApplication.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                appListAdapter.filter.filter(newText)
                return false
            }
        })

    }
}
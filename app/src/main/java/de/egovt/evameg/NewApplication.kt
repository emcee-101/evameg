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

        val applicationsList = mapOf(
            getString(R.string.register_new_residence)  to "register_new_residence",
            getString(R.string.new_id_card)             to "new_id_card",
            getString(R.string.new_passport)            to "new_passport",
            getString(R.string.extend_passport)         to "extend_passport",
            getString(R.string.register_marriage)       to "register_marriage")
        val appListAdapter : ArrayAdapter<String> = ArrayAdapter(
            this,android.R.layout.simple_list_item_1,
            applicationsList.keys.toList()
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
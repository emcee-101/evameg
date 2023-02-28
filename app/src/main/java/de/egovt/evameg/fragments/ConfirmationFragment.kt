package de.egovt.evameg.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import de.egovt.evameg.Fragments.Home
import de.egovt.evameg.MainActivity
import de.egovt.evameg.R
import de.egovt.evameg.utility.DB.DbHelper
import de.egovt.evameg.utility.Office
import de.egovt.evameg.utility.ProposalData
import java.util.*


/**
 * A Fragment that asks for Confirmation to book a date at a Office.
 * @author Niklas Herzog
 */
class ConfirmationFragment(): Fragment() {

    var officeID: Int = -1
    lateinit var office: Office
    val options = arrayOf("09:00", "10:00", "11:00", "12:00", "13:00")
    lateinit var  curContext : Context
    var chosenOption : Int = 0
    var chosenDateFinal : String = ""
    lateinit var date : Button

    constructor(id:Int) : this(){

        officeID = id

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_confirmation, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        curContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list:List<Office>

        if(officeID != -1){
            val db : DbHelper = DbHelper(curContext)
            list = db.readOfficeData(arrayOf<String>(officeID.toString()))
            office = list[0]

        } else Log.e("aaa", "The ID passed isnot correct or nonexistent.")

        val data_description : TextView = view.findViewById(R.id.confirm_text2)
        var tmp : String = "${office.name} ${office.address}"
        data_description.text = tmp

        val spinner : Spinner = view.findViewById(R.id.confirm_spinner)

        val adapter : ArrayAdapter<String> = ArrayAdapter(curContext, android.R.layout.simple_spinner_item, options)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = itemSelector()

        val button = view.findViewById<Button>(R.id.confirm_button1)
        button.setOnClickListener { goAhead() }

        date = view.findViewById<Button>(R.id.confirm_date)


        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]

        setDate(mYear, mMonth, mDay)
        date.setOnClickListener { datePickerStart( mYear,mMonth, mDay) }
    }

    private fun datePickerStart(mYear:Int,mMonth:Int, mDay:Int):Boolean {
        val dialog : DatePickerDialog = DatePickerDialog(curContext, dateListener(), mYear,mMonth, mDay)
        val c = Calendar.getInstance()
        dialog.datePicker.minDate = c.timeInMillis
        dialog.show()
        return true
    }

    private fun setDate(year:Int, month:Int, day:Int){
        chosenDateFinal = "${day}.${month}.${year}"
        date.text = chosenDateFinal

    }

    private fun goAhead(){

        val db : DbHelper = DbHelper(curContext)
        db.insertProposalData(ProposalData(getString(R.string.date), "date", "$chosenDateFinal ${options[chosenOption]}", "confirmed", officeID.toString()))

        if(activity is MainActivity){
            (activity as MainActivity).replaceFragment(Home())
        }

        Toast.makeText(context, R.string.profile_builder_toast_success, Toast.LENGTH_SHORT).show()

    }

    inner class itemSelector : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            chosenOption = p2

        }

        override fun onNothingSelected(p0: AdapterView<*>?) {

            chosenOption = 0

        }

    }

    inner class dateListener : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            setDate(year, month, dayOfMonth)
        }
    }
}
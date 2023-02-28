package de.egovt.evameg.Fragments.setup

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import de.egovt.evameg.R
import de.egovt.evameg.activities.SetupActivity
import de.egovt.evameg.utility.UI.ProfileEditor

import de.egovt.evameg.utility.UI.back

/**
 * Fragment integrated in Setup Activity.
 *
 * Includes a dialog for asking if the user wants to create a profile or not.
 *
 * @author Niklas Herzog
 *
 */
class ScreenSlidePageFragment2 : Fragment() {

    lateinit var momentaryContext : Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.frag_setup2, container, false)

    override fun onAttach(context: Context) {

        momentaryContext = context

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        Log.i("a", "reached fragment 2")

        val builder = AlertDialog.Builder(context)


        val editor : ProfileEditor = ProfileEditor(momentaryContext, layoutInflater)

        // continue to next fragment object
        val backButton: Button? = getView()?.findViewById(R.id.welcome_button_2_back)
        backButton?.setOnClickListener { back(activity?.findViewById(R.id.viewPagerSetup)) }


        // Selection Dialog
        val noButton: Button? = getView()?.findViewById(R.id.welcome_button_3_no)
        noButton?.setOnClickListener {

            builder
                .setMessage(R.string.welcome_message)
                .setCancelable(true)
                .setTitle(R.string.welcome_alert_no_caption)
                .setMessage(R.string.welcome_alert_no_text)
                .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface, i: Int ->
                    run {
                        goToMain()
                    }
                }
                .setNegativeButton(R.string.no) { dialogInterface: DialogInterface, i: Int ->
                    run {
                        editor.showDataDialog { goToMain() }

                    }
                }
                .create()
                .show()
        }

        val yesButton: Button? = getView()?.findViewById(R.id.welcome_button_4_yes)
        yesButton?.setOnClickListener {

            builder
                .setMessage(R.string.welcome_message)
                .setCancelable(true)
                .setTitle(R.string.welcome_alert_yes_caption)
                .setMessage(R.string.welcome_alert_yes_text)
                .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface, i: Int ->
                    run {
                        editor.showDataDialog { goToMain() }

                    }
                }
                .setNegativeButton(R.string.no) { dialogInterface: DialogInterface, i: Int ->
                    run {
                        goToMain()
                    }
                }
                .create()
                .show()
        }

    }

    private fun goToMain(){

        if(activity is SetupActivity)
            (activity as SetupActivity).referToMain()
    }

}
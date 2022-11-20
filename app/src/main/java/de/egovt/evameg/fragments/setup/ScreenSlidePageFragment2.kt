package de.egovt.evameg.fragments.setup

import android.os.Bundle
import android.util.Log
import android.util.Log.INFO
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import de.egovt.evameg.R
import de.egovt.evameg.utility.back
import java.util.logging.Level.INFO

class ScreenSlidePageFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.frag_setup2, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("a", "reached fragment 2");

        // continue to next fragment object
        val backButton: Button? = getView()?.findViewById(R.id.welcome_button_2_back);
        backButton?.setOnClickListener { back(activity?.findViewById(R.id.viewPagerSetup)) }

    }
}
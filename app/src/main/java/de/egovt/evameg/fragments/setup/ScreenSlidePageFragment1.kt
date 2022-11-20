package de.egovt.evameg.fragments.setup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import de.egovt.evameg.R
import de.egovt.evameg.utility.cont

class ScreenSlidePageFragment1 : Fragment()  {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.frag_setup1, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // continue to next fragment object
        val nextButton:Button? = getView()?.findViewById(R.id.welcome_button_1_next);
        nextButton?.setOnClickListener { cont(activity?.findViewById(R.id.viewPagerSetup)) }

    }


}
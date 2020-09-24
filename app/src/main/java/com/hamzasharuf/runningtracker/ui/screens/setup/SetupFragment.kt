package com.hamzasharuf.runningtracker.ui.screens.setup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hamzasharuf.runningtracker.R
import kotlinx.android.synthetic.main.fragment_setup.*

class SetupFragment : Fragment(R.layout.fragment_setup) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabContinue.setOnClickListener {
            findNavController().navigate(R.id.action_setupFragment_to_runFragment)
        }
    }
}
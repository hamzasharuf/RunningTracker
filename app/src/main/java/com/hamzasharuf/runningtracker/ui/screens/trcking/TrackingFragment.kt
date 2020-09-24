package com.hamzasharuf.runningtracker.ui.screens.trcking

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hamzasharuf.runningtracker.R
import com.hamzasharuf.runningtracker.ui.RunSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking) {

    private val viewModel: RunSharedViewModel by viewModels()
}
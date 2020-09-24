package com.hamzasharuf.runningtracker.ui.screens.statistics

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hamzasharuf.runningtracker.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private val viewModel: StatisticsViewModel by viewModels()
}
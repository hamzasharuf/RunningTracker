package com.hamzasharuf.runningtracker.ui.screens

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.hamzasharuf.runningtracker.R
import com.hamzasharuf.runningtracker.utils.extensions.timber
import kotlinx.android.synthetic.main.fragment_run_details.*
import java.io.File
import java.io.FileOutputStream

class RunDetailsFragment : Fragment() {

    val args: RunDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_run_details, container, false)

        val run = args.run
        timber(run.toString())
            Glide.with(requireContext()).asBitmap().load(run.img).into(view.findViewById(R.id.run_map_image))
        view.findViewById<TextView>(R.id.tvDateDetails).text = args.date
        view.findViewById<TextView>(R.id.tvTimeDetails).text = args.time
        view.findViewById<TextView>(R.id.tvCaloriesDetails).text = args.calories
        view.findViewById<TextView>(R.id.tvAvgSpeedDetails).text = args.speed
        view.findViewById<TextView>(R.id.tvDistanceDetails).text = args.distance

        return view
    }


}
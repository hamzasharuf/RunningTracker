package com.hamzasharuf.runningtracker.ui.screens.trcking

import android.app.ActionBar
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.hamzasharuf.runningtracker.R
import com.hamzasharuf.runningtracker.data.database.entities.Run
import com.hamzasharuf.runningtracker.ui.RunSharedViewModel
import com.hamzasharuf.runningtracker.utils.Polyline
import com.hamzasharuf.runningtracker.utils.common.Constants.ACTION_PAUSE_SERVICE
import com.hamzasharuf.runningtracker.utils.common.Constants.ACTION_START_OR_RESUME_SERVICE
import com.hamzasharuf.runningtracker.utils.common.Constants.ACTION_STOP_SERVICE
import com.hamzasharuf.runningtracker.utils.common.Constants.MAP_ZOOM
import com.hamzasharuf.runningtracker.utils.common.Constants.POLYLINE_COLOR
import com.hamzasharuf.runningtracker.utils.common.Constants.POLYLINE_WIDTH
import com.hamzasharuf.runningtracker.utils.common.calculatePolylineLength
import com.hamzasharuf.runningtracker.utils.common.getFormattedStopWatchTimee
import com.hamzasharuf.runningtracker.utils.extensions.gone
import com.hamzasharuf.runningtracker.utils.extensions.visibile
import com.hamzasharuf.runningtracker.utils.services.TrackingService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tracking.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.io.File
import java.io.FileOutputStream
import java.lang.Math.round
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt


@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking) {

    private val viewModel: RunSharedViewModel by viewModels()

    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

    private var map: GoogleMap? = null

    private var curTimeInMillis = 0L

    @set:Inject
    var weight = 80f

    companion object{
        private const val CANCEL_TRACKING_DIALOG_TAG = "CancelDialog"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)

        setupClickListeners()

        // Handle cancel dialog on screen rotation

        if (savedInstanceState != null){
            val cancelTrackingDialog =
                parentFragmentManager.findFragmentByTag(CANCEL_TRACKING_DIALOG_TAG) as CancelTrackingDialog?
            cancelTrackingDialog?.setYesListener {
                clearMapPolylines()
                stopRun()
            }
        }

        mapView.getMapAsync {
            map = it
            addAllPolylines()
        }
        subscribeToObservers()

    }

    private fun setupClickListeners() {
        btnToggleRun.setOnClickListener {
            toggleRun()
        }

        btnCancelRun.setOnClickListener {
            showCancelTrackingDialog()
        }

        btnFinishRun.setOnClickListener {
            if (pathPoints.isEmpty() || pathPoints.last().isEmpty()){
                stopRun()
                Snackbar.make(
                    requireActivity().findViewById(R.id.rootView),
                    "Run is not saved",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            zoomToSeeWholeTrack()
            endRunAndSaveToDb()
        }
    }

    private fun subscribeToObservers() {
        TrackingService.isTracking.observe(viewLifecycleOwner, {
            updateTracking(it)
        })

        TrackingService.pathPoints.observe(viewLifecycleOwner, {
            pathPoints = it
            addLatestPolyline()
            moveCameraToUser()
        })

        TrackingService.timeRunInMillis.observe(viewLifecycleOwner, {
            curTimeInMillis = it
            val formattedTime = getFormattedStopWatchTimee(curTimeInMillis, true)
            tvTimer.text = formattedTime
        })
    }

    private fun toggleRun() {
        if (isTracking) {
            btnCancelRun.visibile()
            sendCommandToService(ACTION_PAUSE_SERVICE)
        } else {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if (!isTracking && curTimeInMillis > 0L) {
            btnToggleRun.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_play_arrow,
                    null
                )
            )
            btnFinishRun.visibility = VISIBLE
        } else if (isTracking) {
            btnToggleRun.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_pause,
                    null
                )
            )
            btnCancelRun.visibility = VISIBLE
            btnFinishRun.visibility = GONE
        }
    }

    private fun moveCameraToUser() {
        if (pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    MAP_ZOOM
                )
            )
        }
    }

    private fun addAllPolylines() {
        for (polyline in pathPoints) {
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun addLatestPolyline() {
        if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            val preLastLatLng = pathPoints.last()[pathPoints.last().size - 2]
            val lastLatLng = pathPoints.last().last()
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLastLatLng)
                .add(lastLatLng)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun sendCommandToService(action: String) =
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }

    private fun showCancelTrackingDialog() {
        CancelTrackingDialog().apply {
            setYesListener {
                clearMapPolylines()
                stopRun()
            }
        }.show(parentFragmentManager, CANCEL_TRACKING_DIALOG_TAG)
    }

    private fun clearMapPolylines() {
        map?.clear()
        pathPoints.clear()
    }

    private fun stopRun() {
        curTimeInMillis = 0
        tvTimer.text = getString(R.string._00_00_00_00)
        clearMapPolylines()
        btnToggleRun.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_play_arrow,
                null
            )
        )
        btnFinishRun.visibility = GONE
        btnCancelRun.visibility = GONE
        sendCommandToService(ACTION_STOP_SERVICE)
        //findNavController().navigate(R.id.action_trackingFragment_to_runFragment)
    }

    private fun zoomToSeeWholeTrack() {
        val bounds = LatLngBounds.Builder()
        for (polyline in pathPoints) {
            for (pos in polyline) {
                bounds.include(pos)
            }
        }

        map?.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds.build(),
                mapView.width,
                mapView.height,
                (mapView.height * 0.05f).toInt()
            )
        )
    }

    private fun endRunAndSaveToDb() {
        btnFinishRun.gone()
        btnCancelRun.gone()
        btnToggleRun.isEnabled = false
        CoroutineScope(Main).launch {
            delay(2000)
            btnToggleRun.isEnabled = true
                map?.snapshot { bmp ->
                var distanceInMeters = 0
                for (polyline in pathPoints) {
                    distanceInMeters += calculatePolylineLength(polyline).toInt()
                }
                val avgSpeed =
                    ((distanceInMeters / 1000f) / (curTimeInMillis / 1000f / 60 / 60) * 10).roundToInt() / 10f
                val dateTimestamp = Calendar.getInstance().timeInMillis
                val caloriesBurned = ((distanceInMeters / 1000f) * weight).toInt()
                val run = Run(
                    bmp,
                    dateTimestamp,
                    avgSpeed,
                    distanceInMeters,
                    curTimeInMillis,
                    caloriesBurned
                )
                viewModel.insertRun(run)
                Snackbar.make(
                    requireActivity().findViewById(R.id.rootView),
                    "Run saved successfully",
                    Snackbar.LENGTH_LONG
                ).show()

                stopRun()
            }
        }

    }

    fun cacheLocally(localPath: String, bitmap: Bitmap, quality: Int = 100) {
        val file = File(localPath)
        file.createNewFile()
        val ostream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, ostream)
        ostream.flush()
        ostream.close()
    }


    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}
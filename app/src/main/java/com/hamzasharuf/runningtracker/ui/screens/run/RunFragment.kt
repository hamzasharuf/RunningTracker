package com.hamzasharuf.runningtracker.ui.screens.run

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hamzasharuf.runningtracker.R
import com.hamzasharuf.runningtracker.ui.RunSharedViewModel
import com.hamzasharuf.runningtracker.utils.MarginItemDecoration
import com.hamzasharuf.runningtracker.utils.adapters.RunAdapter
import com.hamzasharuf.runningtracker.utils.common.PermissionUtils
import com.hamzasharuf.runningtracker.utils.enums.SortType
import com.hamzasharuf.runningtracker.utils.extensions.timber
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_run.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run), EasyPermissions.PermissionCallbacks {

    private val viewModel: RunSharedViewModel by viewModels()

    private lateinit var runAdapter: RunAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        ivFilter.setOnClickListener{
            showFilterDialog()
        }

        viewModel.runs.observe(viewLifecycleOwner, {
            runAdapter.submitList(it)
        })

        PermissionUtils.requestPermissions(this)

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }

    }

    private fun showFilterDialog() {
        val choices = SortType.values().map { it.typeName }.toTypedArray()
        var checkedItem = viewModel.sortType.ordinal

        MaterialAlertDialogBuilder(requireActivity(), R.style.SingleChoiceMaterialAlertDialog)
            .setTitle("Set Filter")
            .setSingleChoiceItems(choices, checkedItem){_,position->
                timber("onSelectChange --> $position")
                checkedItem = position
            }
            .setPositiveButton("ok") { dialog, position ->
                timber("position --> $position")
                viewModel.sortRuns(SortType.getItemAt(checkedItem))
                // TODO : Check Why $position always return -1
            }
            .setNegativeButton("Cancel"){dialog, position ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setupRecyclerView() = rvRuns.apply {
        runAdapter = RunAdapter{run, date, speed, distance, time, calories ->
            val action = RunFragmentDirections.actionRunFragmentToRunDetailsFragment(run, date, time, distance, speed, calories)
            findNavController().navigate(action)
        }
        adapter = runAdapter
        layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(MarginItemDecoration(48))
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            PermissionUtils.requestPermissions(this)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}

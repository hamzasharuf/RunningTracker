package com.hamzasharuf.runningtracker.ui.screens.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hamzasharuf.runningtracker.R
import com.hamzasharuf.runningtracker.data.preferences.PreferencesConfig.NAME_KEY
import com.hamzasharuf.runningtracker.data.preferences.PreferencesConfig.WEIGHT_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.menu_item_back_arrow.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    lateinit var mView: View
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        setupClickListeners()
        loadFieldsFromSharedPref()

    }

    private fun setupClickListeners() {

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPref()
            if(success) {
                Snackbar.make(mView, "Changes saved successfully", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(mView, "Please fill out all the fields", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun loadFieldsFromSharedPref() {
        val name = sharedPreferences.getString(NAME_KEY, "")
        val weight = sharedPreferences.getFloat(WEIGHT_KEY, 80f)
        etName.setText(name)
        etWeight.setText(weight.toInt().toString())
    }

    private fun applyChangesToSharedPref(): Boolean {
        val nameText = etName.text.toString()
        val weightText = etWeight.text.toString()
        if(nameText.isEmpty() || weightText.isEmpty()) {
            return false
        }
        sharedPreferences.edit()
            .putString(NAME_KEY, nameText)
            .putFloat(WEIGHT_KEY, weightText.toFloat())
            .apply()
        return true
    }
}
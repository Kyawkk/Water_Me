package com.kyawzinlinn.waterme.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import com.kyawzinlinn.waterme.R
import com.kyawzinlinn.waterme.viewmodel.PlantViewModel
import com.kyawzinlinn.waterme.viewmodel.PlantViewModelFactory
import java.util.concurrent.TimeUnit

class ReminderDialogFragment(private val plantName: String): DialogFragment() {
    private val viewModel: PlantViewModel by viewModels {
        PlantViewModelFactory(requireActivity().application)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = android.app.AlertDialog.Builder(it)
                .setTitle("Remind me to water $plantName in...")
                .setItems(R.array.water_schedule_array) { _, position ->
                    when (position) {
                        0 ->
                            viewModel
                                .scheduleReminder(20, TimeUnit.SECONDS, plantName)
                        1 ->
                            viewModel
                                .scheduleReminder(1, TimeUnit.DAYS, plantName)
                        2 ->
                            viewModel
                                .scheduleReminder(7, TimeUnit.DAYS, plantName)
                        3 ->
                            viewModel
                                .scheduleReminder(30, TimeUnit.DAYS, plantName)
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
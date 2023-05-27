package com.kyawzinlinn.waterme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kyawzinlinn.waterme.adapter.PlantAdapter
import com.kyawzinlinn.waterme.adapter.PlantListener
import com.kyawzinlinn.waterme.databinding.ActivityMainBinding
import com.kyawzinlinn.waterme.ui.ReminderDialogFragment
import com.kyawzinlinn.waterme.viewmodel.PlantViewModel
import com.kyawzinlinn.waterme.viewmodel.PlantViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PlantViewModel by viewModels {
        PlantViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PlantAdapter(PlantListener { plant ->
            val dialog = ReminderDialogFragment(plant.name)
            dialog.show(supportFragmentManager,"Reminder")
            true
        })

        with(binding.rvReminders){
            setHasFixedSize(true)
            setAdapter(adapter)
            layoutManager = LinearLayoutManager(this@MainActivity)
            val data = viewModel.plants
            adapter.submitList(data)
        }
    }
}
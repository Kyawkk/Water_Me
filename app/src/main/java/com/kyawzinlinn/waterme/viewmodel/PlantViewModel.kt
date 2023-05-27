package com.kyawzinlinn.waterme.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.kyawzinlinn.waterme.data.DataSource
import com.kyawzinlinn.waterme.worker.WaterReminderWorker
import kotlinx.coroutines.delay
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

class PlantViewModel(application: Application): ViewModel() {
    val plants = DataSource.plants
    private val workManager = WorkManager.getInstance(application)

    internal fun scheduleReminder(
        duration: Long,
        unit: TimeUnit,
        plantName: String
    ) {
        val data = Data.Builder()
        data.putString(WaterReminderWorker.nameKey, plantName)

        val reminderWorker = OneTimeWorkRequestBuilder<WaterReminderWorker>()
            .setInitialDelay(duration,unit)
            .setInputData(data.build())
            .build()

        workManager.enqueueUniqueWork(
            plantName,
            ExistingWorkPolicy.REPLACE,
            reminderWorker
        )

    }
}

class PlantViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PlantViewModel::class.java)){
            PlantViewModel(application) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
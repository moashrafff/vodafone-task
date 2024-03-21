package com.example.vodafone_task.application

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.core.LocationHandler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory : HiltWorkerFactory

    private val locationHandler by lazy {
        LocationHandler(this@MyApplication,
            onLocationUpdated = {

            }, onError = { })
    }

    override fun onCreate() {
        super.onCreate()
        if (isLocationPermissionGranted()) {
            locationHandler.fetchLocation()
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
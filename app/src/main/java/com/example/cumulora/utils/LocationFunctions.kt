package com.example.cumulora.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient

fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

fun isLocationPermissionGranted(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

@SuppressLint("MissingPermission")
fun Context.getUserLocation(onResult: (latitude: Double, longitude: Double) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            onResult(location.latitude, location.longitude)
        } else {
            //TODO: الاحسن اجيب اخر لات لونج متكيش
            onResult(0.0, 0.0)
        }
    }.addOnFailureListener {
        onResult(0.0, 0.0)
    }
}

fun requestLocationSettings(activity: Activity, onResult: (Boolean) -> Unit) {
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
    val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
    val client: SettingsClient = LocationServices.getSettingsClient(activity)
    val task = client.checkLocationSettings(builder.build())

    task.addOnSuccessListener {
        onResult(true)
    }.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                exception.startResolutionForResult(activity, 1001)
            } catch (sendEx: Exception) {
                sendEx.printStackTrace()
            }
        }
        onResult(false)
    }
}

@SuppressLint("MissingPermission")
fun waitForLocationUpdates(context: Context, onLocationReceived: (Double, Double) -> Unit) {
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location: Location? = locationResult.lastLocation
            if (location != null) {
                onLocationReceived(location.latitude, location.longitude)
                fusedLocationProviderClient.removeLocationUpdates(this)
            }
        }
    }
    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
}
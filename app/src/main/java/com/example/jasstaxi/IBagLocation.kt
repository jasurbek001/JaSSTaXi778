@file:Suppress("DEPRECATION")

package com.example.jasstaxi

import android.location.GpsStatus
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle

interface IBagLocation : LocationListener, GpsStatus.Listener {
    override fun onLocationChanged(location: Location)

    override fun onProviderDisabled(provider: String)

    override fun onProviderEnabled(provider: String)

    override fun onGpsStatusChanged(event: Int)

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?)



}
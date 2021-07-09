package com.example.jasstaxi.helper

import android.location.Location
import android.location.LocationListener

interface IBagLokatsiya: LocationListener {
    override fun onLocationChanged(location: Location)
}
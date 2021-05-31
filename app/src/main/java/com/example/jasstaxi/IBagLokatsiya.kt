package com.example.jasstaxi

import android.location.Location
import android.location.LocationListener

interface IBagLokatsiya: LocationListener {
    override fun onLocationChanged(location: Location)
}
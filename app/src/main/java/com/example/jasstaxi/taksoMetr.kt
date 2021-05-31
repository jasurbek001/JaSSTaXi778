  package com.example.jasstaxi

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isInvisible
import com.example.jasstaxi.databinding.ActivityTaksoMetrBinding
import kotlinx.android.synthetic.main.activity_register_oyna2.*
import kotlinx.android.synthetic.main.activity_takso_metr.*

class taksoMetr : IBagLocation, AppCompatActivity() {
    private  var running: Boolean = false
    private var PERMISSION_LOCATION: Int = 0
    private var seconds: Int = 0
    private  var distance: Double = 0.0
    private var lat1: Double = 0.0
    private var sk: Double = 0.0
    private var run: Boolean = false
    private var stringExtra: String? = null
    val RQUEST_PHONE = 1
    private lateinit var binding: ActivityTaksoMetrBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaksoMetrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        stringExtra = intent.getStringExtra("aaa")
        var mediaPlayer = MediaPlayer.create(this,R.raw.bool)
        var mediaPlayer1 = MediaPlayer.create(this,R.raw.stop)
        runTimer()

        pauzebtn.visibility = View.GONE
        startbtn.setOnClickListener{
            mediaPlayer.start()
            pauzebtn.visibility = View.VISIBLE

            startbtn.visibility = View.GONE

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_LOCATION
                )
            } else {
                showLocation()
            }
            running = true
            run = true

        }

        if (stringExtra!!.isEmpty())
        callphone.visibility = View.GONE

        callphone.setOnClickListener {

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CALL_PHONE), RQUEST_PHONE)
            }

            else
            {
                setPhone()
            }
        }

        pauzebtn.setOnClickListener {

            pauzebtn.visibility = View.GONE
            startbtn.text = "Davom et"
            this.startbtn.visibility = View.VISIBLE

            run = false
        }
        stopbtn.setOnClickListener {
            pauzebtn.visibility = View.GONE
            if (seconds != 0) {
                mediaPlayer1.start()
            }
            this.startbtn.visibility = View.VISIBLE
            running = false
            disttxt.text = "0 km"
            seconds = 0

        }
    }

    private fun setPhone() {
        var intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel: " + stringExtra)
        startActivity(intent)
    }


    @SuppressLint("MissingPermission")
    private fun showLocation() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)

        } else {
            Toast.makeText(this, "Enable GPS...", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    private fun runTimer() {

        val handler = Handler()
        handler.post(object : Runnable{
            @SuppressLint("SetTextI18n")
            override fun run() {
                var hours: Int = seconds / 3600
                var minut: Int = seconds / 60
                val second: Int = seconds % 60

                val st: String = String.format("%d:%02d:%02d",hours,minut,second)
                textView13.setText(st)

                if(running && run)
                {
                    distance += (lat1 / 1000.0 / 3.6)
                    seconds++

                    if(distance > 0)
                    {
                        sk = distance * 1200
                        sk += 5000

                        summatxt.setText(String.format("%.2f So'm", sk))
                    }
                }
                disttxt.setText(String.format("%.2f km", distance))
                handler.postDelayed(this,1000)

            }

        })

    }
    override fun onLocationChanged(location: Location) {

        lat1 = location.speed.toDouble()
        lat1 *= 3.6
        speedtxt.text = String.format("%.2f km/h", lat1)

    }

    override fun onProviderDisabled(provider: String) {

    }

    override fun onProviderEnabled(provider: String) {

    }

    override fun onGpsStatusChanged(event: Int) {

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == RQUEST_PHONE)setPhone()
    }

}
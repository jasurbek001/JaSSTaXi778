  package com.example.jasstaxi.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.*
import android.view.View.GONE
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import com.example.jasstaxi.helper.IBagLocation
import com.example.jasstaxi.R
import com.example.jasstaxi.databinding.ActivityTaksoMetrBinding
import com.example.jasstaxi.databinding.BottomsheetLayoutBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_city_oyna.*
import kotlinx.android.synthetic.main.activity_takso_metr.*
import kotlinx.android.synthetic.main.menu_bosh.*
import kotlinx.android.synthetic.main.toolbar.*

  @Suppress("DEPRECATION")
class TaksoMetr : IBagLocation, AppCompatActivity() {
    private  var running: Boolean = false
    private var PERMISSION_LOCATION: Int = 0
    private var seconds: Int = 0
    private  var distance: Double = 0.0
    private var lat1: Double = 0.0
    private var sk: Double = 5000.0
    private var run: Boolean = false
    private var stringExtra: String? = null
    private var stringKutish: String? = null
    private var calling: Boolean = false
    private val RQUEST_PHONE = 1
    private lateinit var bindingBottomedLayoutBinding:BottomsheetLayoutBinding
    lateinit var bindingBottomsheetLayoutBinding:BottomsheetLayoutBinding
    private lateinit var binding: ActivityTaksoMetrBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var preferences:SharedPreferences
    private lateinit var sharedPreferences1: SharedPreferences
    private var kutish: Int = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaksoMetrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences1 = getSharedPreferences("Zakaz",0)

        stringExtra = sharedPreferences1.getString("number", "")
        Toast.makeText(this,stringExtra,Toast.LENGTH_LONG).show()

        //menu_btn.setOnClickListener {
//            menyu.visibility = View.VISIBLE
//            takso_metr.alpha = 0.1F
//            ism_familiya_txt.text = preferences.getString("ism","")
//
//
//        }
//        chiqish_btn.setOnClickListener {
//            menyu.visibility = GONE
//            takso_metr.alpha = 1F
//        }
//
//        taksometr.setOnClickListener {
//            menyu.visibility = GONE
//
//        }
//
//        istoriya.setOnClickListener {
//            startActivity(Intent(this,TarxiBuyurtma::class.java))
//
//        }
//
//        chiqish.setOnClickListener {
//            showDialog1()
//            finish()
//        }
//
//
//        shaxsiy_kab.setOnClickListener{
//            Toast.makeText(this,"Shaxsiydaa",Toast.LENGTH_LONG).show()
//        }

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




        startbtn.setOnClickListener{ showDialog2() }

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
        stopbtn.setOnClickListener {
            showDialog4()
        }

        buyurtmalarga.setOnClickListener {
            startActivity(Intent(this,CityOyna::class.java))
        }
    }
    private fun toxtat(){
        val mediaPlayer1 = MediaPlayer.create(this, R.raw.stop_mp3 )

        if (seconds != 0) {
            mediaPlayer1.start()
        }
        running = false
        seconds = 0
    }

    @SuppressLint("SetTextI18n")
    private fun showDialog4() {
        val dialog = Dialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        bindingBottomsheetLayoutBinding = BottomsheetLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingBottomsheetLayoutBinding.root)

        bindingBottomsheetLayoutBinding.dialogText.text = "Siz hisobni tugatishni hohlaysizmi?"

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.attributes?.windowAnimations = R.style.DialoAnimation
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)

        bindingBottomsheetLayoutBinding.botNoBtn.setOnClickListener {
            dialog.dismiss()
        }

        bindingBottomsheetLayoutBinding.botOkBtn.setOnClickListener {

            startbtn.visibility = GONE
            disttxt.visibility = GONE
            vaqttxt.visibility = GONE
            narx_min.visibility = GONE
            narx_km.visibility = GONE
            disttxt.visibility = GONE
            val summa:String = summatxt.text.toString() + " " + "so'm"
            oxiri.visibility = View.VISIBLE
            oxirgi_km.text = disttxt.text
            oxirgi_km.visibility = View.VISIBLE
            km.visibility = View.VISIBLE
            stopbtn.visibility = GONE
            summatxt.text = summa
            buyurtmalarga.visibility = View.VISIBLE
            toxtat()
            dialog.dismiss()
        }

    }

    private fun showDialog2() {
        val dialog = Dialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        bindingBottomsheetLayoutBinding = BottomsheetLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingBottomsheetLayoutBinding.root)



        bindingBottomsheetLayoutBinding.dialogText.text = "Haqiqatdan ham boshlashni hohlaysizmi?"

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.attributes?.windowAnimations = R.style.DialoAnimation
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)



        bindingBottomsheetLayoutBinding.botNoBtn.setOnClickListener {
            dialog.dismiss()
        }

        bindingBottomsheetLayoutBinding.botOkBtn.setOnClickListener {
            kutish = 1
            stopbtn.visibility = View.VISIBLE
            asosiy.visibility = View.VISIBLE
            vaqttxt.visibility = View.VISIBLE
            speedtxt.visibility = View.VISIBLE
            disttxt.visibility = View.VISIBLE
            summatxt.visibility = View.VISIBLE
            onBoshlash()
            dialog.dismiss()
        }


    }

    private fun setPhone() {
         calling = true
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel: $stringExtra")
        startActivity(intent)
    }


    private fun runTimer() {

        val handler = Handler()
        handler.post(
            object : Runnable {
                @SuppressLint("SetTextI18n")
                override fun run() {
                    val hours: Int = seconds / 3600
                    val minut: Int = seconds / 60
                    val second: Int = seconds % 60
                    val st: String = String.format("%d:%02d:%02d", hours, minut, second)
                    vaqttxt.text = st


                    if (running && run) {

                        distance += (lat1 / 1000.0 / 3.6)
                        seconds++

                        if (distance < 2) {
                            if(kutish == 1 && (seconds >= 180))
                                sk += 6
                        }
                        else{
                            if (speedtxt.text == "0,00 km/h" || speedtxt.text == "0 km/s")
                                sk += 6
                            else
                                sk += (lat1 / 1000.0 / 3.6) * 1200
                        }



                        summatxt.text = String.format("%.1f", sk)

                    }
                    disttxt.text = String.format("%.2f km", distance)
                    handler.postDelayed(this, 1000)

                }

            }
        )

    }


    fun onBoshlash(){
        val mediaPlayer1 = MediaPlayer.create(this, R.raw.start_mp3)
        runTimer()
        mediaPlayer1.start()
        callphone.visibility = GONE
        startbtn.visibility = GONE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_LOCATION
            )
        }
        running = true
        run = true
    }
    override fun onLocationChanged(location: Location) {

        lat1 = location.speed.toDouble()
        lat1 *= 3.6
        speedtxt.text = String.format("%.2f km/h", lat1)

    }
    @SuppressLint("MissingPermission")
    private fun showLocation() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,this)

        } else {
            Toast.makeText(this, "Enable GPS...", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
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

    override fun onBackPressed() {
        showDialog1()
    }

    @SuppressLint("SetTextI18n")
    private fun showDialog1() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        bindingBottomsheetLayoutBinding = BottomsheetLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingBottomsheetLayoutBinding.root)



        bindingBottomsheetLayoutBinding.dialogText.text = "Haqiqatdan ham chiqmoqchimisiz"

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.attributes?.windowAnimations = R.style.DialoAnimation
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)

        bindingBottomsheetLayoutBinding.botNoBtn.setOnClickListener {
        dialog.dismiss()
        }

        bindingBottomsheetLayoutBinding.botOkBtn.setOnClickListener {
            if (seconds == 0) {
                val intent = Intent(this, AsosiyQism::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Avval boshlangan buyurtmani yakunlang.",Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        val i = Intent(this,TaksoMetr::class.java)
        startActivity(i)
    }


}
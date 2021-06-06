  package com.example.jasstaxi

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.View.GONE
import androidx.core.app.ActivityCompat
import com.example.jasstaxi.databinding.ActivityTaksoMetrBinding
import com.example.jasstaxi.databinding.BottomsheetLayoutBinding
import kotlinx.android.synthetic.main.activity_takso_metr.*

@Suppress("DEPRECATION")
class TaksoMetr : IBagLocation, AppCompatActivity() {
    private  var running: Boolean = false
    private var PERMISSION_LOCATION: Int = 0
    private var seconds: Int = 0
    private  var distance: Double = 0.0
    private var lat1: Double = 0.0
    private var sk: Double = 0.0
    private var run: Boolean = false
    private var stringExtra: String? = null
    private var stringKutish: String? = null

    private val RQUEST_PHONE = 1
    lateinit var bindingBottomsheetLayoutBinding:BottomsheetLayoutBinding
    private lateinit var binding: ActivityTaksoMetrBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaksoMetrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vaqttxt.visibility = GONE
        spedtxt.visibility = GONE
        speedtxt.visibility = GONE
        disttxt.visibility = GONE
        summatxt.visibility = GONE
        summaT.visibility = GONE
        vaqtT.visibility = GONE
        distT.visibility = GONE



        stringExtra = intent.getStringExtra("aaa")
        stringKutish = intent.getStringExtra("a")




        if(stringKutish == "10") {
            binding.kutishvaqt.text = "10:00"
        }
        else{
            binding.kutishvaqt.text = "20:00"
        }

        runTimer()

        if(stringExtra == "0") {
            callphone.visibility = GONE
            vaqttxt.visibility = View.VISIBLE
            spedtxt.visibility = View.VISIBLE
            speedtxt.visibility = View.VISIBLE
            disttxt.visibility = View.VISIBLE
            summatxt.visibility = View.VISIBLE
            summaT.visibility = View.VISIBLE
            vaqtT.visibility = View.VISIBLE
            distT.visibility = View.VISIBLE

            kutishvaqt.visibility = GONE

        }

        pauzebtn.visibility = GONE
        startbtn.setOnClickListener{
            showDialog2()
        }

        if (stringExtra!!.isEmpty())
        callphone.visibility = GONE

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

            pauzebtn.visibility = GONE
            startbtn.text = "Davom et"
            this.startbtn.visibility = View.VISIBLE

            run = false
        }
        stopbtn.setOnClickListener {
            showDialog4()
        }
    }
    private fun toxtat(){
        val mediaPlayer1 = MediaPlayer.create(this,R.raw.stop)

        pauzebtn.visibility = GONE
        if (seconds != 0) {
            mediaPlayer1.start()
        }
        this.startbtn.visibility = View.VISIBLE
        running = false
        disttxt.text = "0 km"
        seconds = 0
    }

    private fun showDialog4() {
        val dialog = Dialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        bindingBottomsheetLayoutBinding = BottomsheetLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingBottomsheetLayoutBinding.root)



        bindingBottomsheetLayoutBinding.textView.text = "Siz hisobni tugatishni hohlaysizmi?"

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.attributes?.windowAnimations = R.style.DialoAnimation
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)

        bindingBottomsheetLayoutBinding.botNoBtn.setOnClickListener {
            dialog.dismiss()
        }

        bindingBottomsheetLayoutBinding.botOkBtn.setOnClickListener {
            toxtat()
            dialog.dismiss()
        }

    }

    private fun showDialog2() {
        val dialog = Dialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        bindingBottomsheetLayoutBinding = BottomsheetLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingBottomsheetLayoutBinding.root)



        bindingBottomsheetLayoutBinding.textView.text = "Haqiqatdan ham boshlashni hohlaysizmi?"

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.attributes?.windowAnimations = R.style.DialoAnimation
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)



        bindingBottomsheetLayoutBinding.botNoBtn.setOnClickListener {
            dialog.dismiss()
        }

        bindingBottomsheetLayoutBinding.botOkBtn.setOnClickListener {
            vaqttxt.visibility = View.VISIBLE
            spedtxt.visibility = View.VISIBLE
            speedtxt.visibility = View.VISIBLE
            disttxt.visibility = View.VISIBLE
            summatxt.visibility = View.VISIBLE
            summaT.visibility = View.VISIBLE
            vaqtT.visibility = View.VISIBLE
            distT.visibility = View.VISIBLE

            kutishvaqt.visibility = GONE
            onBoshlash()
            dialog.dismiss()
        }


    }

    private fun setPhone() {
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

                        if (distance > 0) {
                            sk = distance * 1200
                            sk += 5000

                            summatxt.setText(String.format("%.2f So'm", sk))
                        }
                    }
                    disttxt.text = String.format("%.2f km", distance)
                    handler.postDelayed(this, 1000)

                }

            }
        )

    }

    fun onBoshlash(){
        val mediaPlayer = MediaPlayer.create(this,R.raw.bool)

        mediaPlayer.start()
        pauzebtn.visibility = View.VISIBLE

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

    private fun showDialog1() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        bindingBottomsheetLayoutBinding = BottomsheetLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingBottomsheetLayoutBinding.root)



        bindingBottomsheetLayoutBinding.textView.text = "Haqiqatdan ham chiqmoqchimisiz"

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.attributes?.windowAnimations = R.style.DialoAnimation
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)



        bindingBottomsheetLayoutBinding.botNoBtn.setOnClickListener {
        dialog.dismiss()
        }

        bindingBottomsheetLayoutBinding.botOkBtn.setOnClickListener {
            val intent = Intent(this,AsosiyQism::class.java)
            startActivity(intent)
        }



    }

}
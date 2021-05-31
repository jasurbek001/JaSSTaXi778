package com.example.jasstaxi

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import java.util.*

class BoshOyna : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var sharedPreferences: SharedPreferences
    var isNightModeOn = false

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_bosh_oyna)

        sharedPreferences = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        isNightModeOn = sharedPreferences.getBoolean("NightMode", false)

        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }



        button = Button(this)
        button = findViewById(R.id.button3)
        button.setOnClickListener { startActivity(Intent(this, CityOyna::class.java)) }

        button = Button(this)
        button = findViewById(R.id.button4)
        button.setOnClickListener { startActivity(Intent(this, taksoMetr::class.java)) }

        button = Button(this)
        button = findViewById(R.id.button6)
        button.setOnClickListener { startActivity(Intent(this, payNet::class.java)) }

        button = findViewById(R.id.button7)
        button.setOnClickListener { startActivity(Intent(this, Settings::class.java)) }


        button = findViewById(R.id.button5)
        button.setOnClickListener { startActivity(Intent(this, TarxiBuyurtma::class.java)) }


    }

    override fun onRestart() {

        super.onRestart()
        loadLocate()
        recreate()
    }

    private fun setLocate(Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale

        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings",Context.MODE_PRIVATE).edit()
        editor.putString("MY_Lang", Lang)
        editor.apply()
    }

    private fun loadLocate(){
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("MY_Lang","")
        setLocate(language.toString())
    }

}

package com.example.jasstaxi.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.example.jasstaxi.R
import java.util.*

class BoshOyna : AppCompatActivity() {
    private lateinit var button: Button
    @SuppressLint("RestrictedApi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_oyna)


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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

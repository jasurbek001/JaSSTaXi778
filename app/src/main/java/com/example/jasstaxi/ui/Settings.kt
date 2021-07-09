package com.example.jasstaxi.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.example.jasstaxi.R
import com.mahfa.dnswitch.DayNightSwitch
import java.util.*

@Suppress("DEPRECATION")
class Settings : AppCompatActivity() {

    private lateinit var btn: Button
    private lateinit var btnnight: Button

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var outbtn: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        loadLocate()

        btnnight = findViewById(R.id.nightmode)
        outbtn = findViewById(R.id.logoutbtn)
        btn = findViewById(R.id.tilbtn)


        btn.setOnClickListener() {

            showChangeLang()
        }

        outbtn.setOnClickListener {

            val intent = Intent(this, AsosiyQism::class.java)

            intent.putExtra("abd", "000")

            startActivity(intent)
        }
    }

    private fun showChangeLang() {
        val listItems = arrayOf("Pусский", "Uzbek", "English")

        val nBuilder = AlertDialog.Builder(this@Settings)
        nBuilder.setTitle("Tilni tanlang")

        nBuilder.setSingleChoiceItems(listItems, -1) { dialog,
                                                       which ->
            if (which == 0) {
                setLocate("ru")
                recreate()
            }
            if (which == 1) {
                setLocate("uz")
                recreate()
            }
            if (which == 2) {
                setLocate("en")
                recreate()
            }

            dialog.dismiss()
        }
        val nDialog = nBuilder.create()

        nDialog.show()
    }

    private fun setLocate(Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale

        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("MY_Lang", Lang)
        editor.apply()


    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("MY_Lang", "")
        setLocate(language.toString())
    }

}
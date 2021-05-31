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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import java.util.*

@Suppress("DEPRECATION")
class Settings : AppCompatActivity() {

    private lateinit var btn: Button
    private lateinit var switch: Button
    private lateinit var outbtn: Button
    private lateinit var sharedPreferences: SharedPreferences


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadLocate()

        setContentView(R.layout.activity_settings)
        switch = findViewById(R.id.textbtn)
        outbtn = findViewById(R.id.logoutbtn)

        val appSettingsPref: SharedPreferences =
            getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val isNightModeOn: Boolean = appSettingsPref.getBoolean("NightMode", false)
        val sharedPreferesEdit: SharedPreferences.Editor = appSettingsPref.edit()

        switch.setOnClickListener {
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPreferesEdit.putBoolean("NightMode", false)
                sharedPreferesEdit.apply()
            } else {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPreferesEdit.putBoolean("NightMode", true)
                sharedPreferesEdit.apply()
            }
        }


        val actionBar = supportActionBar
        actionBar!!.title = "Sozlamalar"
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
package com.example.jasstaxi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jasstaxi.R
import kotlinx.android.synthetic.main.toolbar.*

class TarxiBuyurtma : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarxi_buyurtma)

        ortga.setOnClickListener {
            startActivity(Intent(this,CityOyna::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,CityOyna::class.java))
    }
}
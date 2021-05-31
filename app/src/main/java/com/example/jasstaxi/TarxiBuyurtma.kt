package com.example.jasstaxi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TarxiBuyurtma : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarxi_buyurtma)

        val actionBar = supportActionBar
        actionBar!!.title = "Buyurtmalar tarixi"
    }
}
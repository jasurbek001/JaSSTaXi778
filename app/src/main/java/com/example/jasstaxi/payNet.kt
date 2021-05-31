package com.example.jasstaxi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class payNet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_net)
        val actionBar = supportActionBar
        actionBar!!.title = "Paynet"
    }
}
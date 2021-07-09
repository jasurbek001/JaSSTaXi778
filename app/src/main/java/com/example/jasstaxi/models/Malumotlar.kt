package com.example.jasstaxi.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jasstaxi.databinding.ActivityMalumotlarBinding
import com.example.jasstaxi.ui.RegisterOyna
import kotlinx.android.synthetic.main.activity_malumotlar.*
import kotlinx.android.synthetic.main.activity_malumotlar.name1

class Malumotlar : AppCompatActivity() {
    private lateinit var binding1: ActivityMalumotlarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding1 = ActivityMalumotlarBinding.inflate(layoutInflater)
        setContentView(binding1.root)

        val ism = name1.text
        val familiya = familiya.text
        val passport = passport1.text
        val avtotur = car1.text
        val avtorang = carrang1.text
        val avtoraqam = carnomer1.text


        qoshish.setOnClickListener {
            val intent = Intent(this, RegisterOyna::class.java)
            intent.putExtra("familiya", "$familiya")
            intent.putExtra("ism", "$ism")

            intent.putExtra("passport", "$passport")
            intent.putExtra("tur", "$avtotur")
            intent.putExtra("raqam", "$avtoraqam")
            intent.putExtra("rang", "$avtorang")
            startActivity(intent)
        }

    }
}
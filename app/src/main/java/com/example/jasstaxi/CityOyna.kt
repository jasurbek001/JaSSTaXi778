package com.example.jasstaxi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.jasstaxi.databinding.ActivityCityOynaBinding

class CityOyna : AppCompatActivity() {
    lateinit var binding: ActivityCityOynaBinding
    private lateinit var btnch: Button
    private lateinit var btny: Button
    private lateinit var btnu: Button
    private lateinit var btnt: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityOynaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        btnch = findViewById(R.id.chortoq_btn)
        btnch.setOnClickListener {  val intent = Intent(this, Buyurtma::class.java)
            intent.putExtra("abd", "Chortoq")
            startActivity(intent) }
        btny = findViewById(R.id.yangiqorgon_btn)
        btny.setOnClickListener {  val intent = Intent(this, Buyurtma::class.java)
            intent.putExtra("abd", "Yangiqo'rg'on")
            startActivity(intent) }
        btnu = findViewById(R.id.uychi_btn)
        btnu.setOnClickListener {  val intent = Intent(this, Buyurtma::class.java)
            intent.putExtra("abd", "Uychi")
            startActivity(intent) }
        btnt = findViewById(R.id.toraqorgon_btn)
        btnt.setOnClickListener {  val intent = Intent(this, Buyurtma::class.java)
            intent.putExtra("abd", "To'raqo'rg'on")
            startActivity(intent) }


    }
}
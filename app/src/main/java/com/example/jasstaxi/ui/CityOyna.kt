package com.example.jasstaxi.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.jasstaxi.R
import com.example.jasstaxi.databinding.ActivityCityOynaBinding
import kotlinx.android.synthetic.main.activity_city_oyna.*
import kotlinx.android.synthetic.main.menu_bosh.*
import kotlinx.android.synthetic.main.toolbar.*

class CityOyna : AppCompatActivity() {
    private lateinit var btnch: Button
    private lateinit var btny: Button
    private lateinit var btnu: Button
    private lateinit var btnt: Button
    private lateinit var imageView: ImageView
    private lateinit var view: ImageView
    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_oyna)
        ortga.setOnClickListener {
            Toast.makeText(this,"Chiqish uchub yaba bir bora bosing.",Toast.LENGTH_LONG).show()
            ortga.setOnClickListener { finish() }

        }
        preferences = getSharedPreferences("User",0)
        val edit = preferences.edit()
        btnch = findViewById(R.id.chortoq_btn)
        btnch.setOnClickListener {  val intent = Intent(this, Buyurtma::class.java)
edit.putString("joy","Chortoq")
            edit.apply()
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
        namanganTbtn.setOnClickListener { val intent = Intent(this, Buyurtma::class.java)
            intent.putExtra("abd", "Namangan")
            startActivity(intent)
        }


       menu_btn.setOnClickListener {
           menyu.visibility = View.VISIBLE
           constr_city.alpha = 0.1F
           ism_familiya_txt.text = preferences.getString("ism","")


       }
        chiqish_btn.setOnClickListener {
            menyu.visibility = View.GONE
            constr_city.alpha = 1F
        }

        taksometr.setOnClickListener{
            startActivity(Intent(this,TaksoMetr::class.java))
        }

        istoriya.setOnClickListener{
            startActivity(Intent(this,TarxiBuyurtma::class.java))

        }

        chiqish.setOnClickListener{
            finish()
        }

        takso.setOnClickListener {
            startActivity(Intent(this,TaksoMetr::class.java))
        }
        shaxsiy_kab.setOnClickListener{
            Toast.makeText(this,"Shaxsiydaa",Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this,"Chiqish uchub yaba bir bora bosing.",Toast.LENGTH_LONG).show()
        super.onBackPressed()
        finish()
    }
}
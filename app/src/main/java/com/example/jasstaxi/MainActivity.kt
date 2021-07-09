package com.example.jasstaxi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.example.jasstaxi.ui.AsosiyQism
import kotlinx.android.synthetic.main.activity_asosiy_qism.*

class MainActivity : AppCompatActivity(), Runnable {
    private lateinit var topAnim: Animation
    private lateinit var bottomAnim: Animation
    private lateinit var image: View
    private lateinit var text: TextView
    private lateinit var text1: TextView
    private var splash: Int = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_asosiy_qism)

        topAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim)

        image = findViewById(R.id.carimage)


        image.animation = topAnim
        moshina.animation = bottomAnim

        val handeler = Handler()
        handeler.post {
            handeler.postDelayed(this@MainActivity, splash.toLong())

        }

    }

    override fun run() {
        val intent = Intent(this, AsosiyQism::class.java)
        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }

}
package com.example.jasstaxi.ui

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.example.jasstaxi.models.Malumotlar
import com.example.jasstaxi.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.activity_city_oyna.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_tarxi_buyurtma.*
import kotlinx.android.synthetic.main.menu_bosh.*
import kotlinx.android.synthetic.main.register_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*

class AsosiyQism : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var textView: TextView
    private lateinit var phonnum: EditText
    private lateinit var pasnum: EditText
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var preferences: SharedPreferences
    private lateinit var progressBar: ProgressDialog
    lateinit var firebaseAuth: FirebaseAuth
    var isLogin = false

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = ProgressDialog(this)
        preferences = getSharedPreferences("User",0)
//        menu_btn.setOnClickListener {
//            menyu.visibility = View.VISIBLE
//            constr_city.alpha = 0.1F
//
//        }
//
//        chiqish_btn.setOnClickListener {
//            menyu.visibility = View.GONE
//            constr_city.alpha = 1F
//
//        }

        reg_kir.setOnClickListener {
            startActivity(Intent(this,RegisterOyna::class.java))
        }

        progressBar.setCancelable(false)
        progressBar.setCanceledOnTouchOutside(false)
        progressBar.setTitle("Please wait...")

        sharedPreferences = getSharedPreferences("LogIn", Context.MODE_PRIVATE)

        val stringExtra = intent.getStringExtra("abd")

        if (stringExtra != "000") {
            isLogin = sharedPreferences.getBoolean("", false)
            if (isLogin) {
                startActivity(Intent(this@AsosiyQism, CityOyna::class.java))
            }
        }

        firebaseAuth = FirebaseAuth.getInstance()
        phonnum = findViewById(R.id.editTextPhone)
        pasnum = findViewById(R.id.password_edit)


        button = Button(this)
        button = findViewById(R.id.kirish)
        button.setOnClickListener {
            progressBar.show()
            login()
        }


    }

    private fun login(){
        val phone: String = editTextPhone.text.toString()
        val pasword: String = password_edit.text.toString()

        if(phone.isBlank() || pasword.isBlank())
        {
            Toast.makeText(this,"Telefon raqam yoki parolni to'liq kiriting...", Toast.LENGTH_LONG).show()
            return
        }

        val checkUser: Query = FirebaseDatabase.getInstance().getReference("User").orderByChild("phoneNumber").equalTo(phone)

        checkUser.addListenerForSingleValueEvent(object: com.google.firebase.database.ValueEventListener{
            @SuppressLint("CommitPrefEdits")
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    val code: String = snapshot.child(phone).child("password").value.toString()

                    if(code == pasword)
                    {
                        val user = FirebaseDatabase.getInstance().getReference("User").orderByChild("userName").toString()
                        Toast.makeText(this@AsosiyQism,"$user xush kelibsiz!!!...",Toast.LENGTH_LONG).show()
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        progressBar.dismiss()
                        editor.putBoolean("",true)
                        editor.apply()
                        val hisob: String = snapshot.child(phone).child("hisob").value.toString()
                        val edit = preferences.edit()
                        edit.putString("hisob",hisob)
                        val intent = Intent(this@AsosiyQism, CityOyna::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        progressBar.dismiss()
                        Toast.makeText(this@AsosiyQism,"Parol hato...", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AsosiyQism,"$error Topilmadi...",Toast.LENGTH_LONG).show()
            }

        })

    }

    override fun onRestart() {
        super.onRestart()
        finish()

    }
    }




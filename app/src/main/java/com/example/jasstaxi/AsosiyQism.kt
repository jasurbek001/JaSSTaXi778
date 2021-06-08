package com.example.jasstaxi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class AsosiyQism : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var textView: TextView
    private lateinit var phonnum: EditText
    private lateinit var pasnum: EditText
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var firebaseAuth: FirebaseAuth
    var isLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            sharedPreferences = getSharedPreferences("LogIn", Context.MODE_PRIVATE)
        val stringExtra = intent.getStringExtra("abd")

        if(stringExtra != "000") {
            isLogin = sharedPreferences.getBoolean("", false)
            if (isLogin) {
                val intent = Intent(this@AsosiyQism, BoshOyna::class.java)
                startActivity(intent)
            }
        }


        firebaseAuth = FirebaseAuth.getInstance()
        phonnum = findViewById(R.id.phonnumber)
        pasnum = findViewById(R.id.passwordnum)


        button = Button(this)
        button = findViewById(R.id.boshOynaBtn)
        button.setOnClickListener { login()}

        textView = TextView(this)
        textView = findViewById(R.id.textView5)

        textView.setOnClickListener{startActivity(Intent(
            this, Malumotlar::class.java))}

        textView = findViewById(R.id.textView3)
        textView.setOnClickListener{startActivity(Intent(
            this, forgotPassword::class.java))}
    }

    private fun login(){
        val phone: String = phonnum.text.toString()
        val pasword: String = pasnum.text.toString()

        if(phone.isBlank() || pasword.isBlank())
        {
            Toast.makeText(this,"Telefon raqam yoki parolni to'liq kiriting...", Toast.LENGTH_LONG).show()
            return
        }

        var checkUser: Query = FirebaseDatabase.getInstance().getReference("User").orderByChild("phoneNumber").equalTo(phone)

        checkUser.addListenerForSingleValueEvent(object: com.google.firebase.database.ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    val code: String = snapshot.child(phone).child("password").value.toString()

                    if(code == pasword)
                    {
                        var user = FirebaseDatabase.getInstance().getReference("User").orderByChild("userName").toString()
                        Toast.makeText(this@AsosiyQism,"$user xush kelibsiz!!!...",Toast.LENGTH_LONG).show()
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()

                        editor.putBoolean("",true)
                        editor.apply()
                        var intent = Intent(this@AsosiyQism,BoshOyna::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this@AsosiyQism,"Parol hato...", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AsosiyQism,"$error Topilmadi...",Toast.LENGTH_LONG).show()
            }

        })

    }

    override fun onResume() {
        super.onResume()
        finish()
    }
}


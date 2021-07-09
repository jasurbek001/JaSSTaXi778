package com.example.jasstaxi.ui

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.jasstaxi.R
import com.example.jasstaxi.databinding.ActivityRegisterOyna2Binding
import com.example.jasstaxi.models.User
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_malumotlar.*
import kotlinx.android.synthetic.main.activity_register_oyna2.*
import kotlinx.android.synthetic.main.register_layout.*
import java.util.concurrent.TimeUnit

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RegisterOyna : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var progressDialog: ProgressDialog
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var kam: AutoCompleteTextView
    private lateinit var preferences: SharedPreferences
    private lateinit var tel: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout)

        zor.visibility = View.VISIBLE
        kam = findViewById(R.id.kam)
        val avtur = resources.getStringArray(R.array.avtoTur)
        val arrayAdapter = ArrayAdapter(this,R.layout.drop_down,avtur)
        kam.setAdapter(arrayAdapter)



        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setCancelable(false)

        tel = findViewById(R.id.tel_edit)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("User")
        auth.languageCode = "uz"


        val phone: String = tel.text.toString()
        register_btn.setOnClickListener {
            sendData()
           login()
        }



        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            startActivity(Intent(this@RegisterOyna,BoshOyna::class.java))
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {

                    Toast.makeText(this@RegisterOyna, "${e.message} Invalidation", Toast.LENGTH_LONG).show()
                    progressDialog.dismiss()

                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(this@RegisterOyna, "Juda ko'p urinish bo'ldi. Birozdan so'ng yana urinib ko'ring!", Toast.LENGTH_LONG).show()
                    progressDialog.dismiss()
                }

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("TAG","onCodeSent:$verificationId")

                progressDialog.dismiss()

                resendToken = token
                val intent = Intent(this@RegisterOyna, Verify::class.java)
                intent.putExtra("storedVerificationId", verificationId)
                startActivity(intent)            }
        }

        val stringExtra = intent.getStringExtra("ism")

        Toast.makeText(this,"$stringExtra hammadan salom",Toast.LENGTH_LONG).show()

    }


    private fun sendVerificationCode(phoneNumber: String) {
        progressDialog.setMessage("Verify phone number...")
        progressDialog.show()

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun login() {

        if (tel_edit.text.toString().isNotEmpty()) {
            sendVerificationCode(tel_edit.text.toString().trim())
        } else {
            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }
    @SuppressLint("CommitPrefEdits")
    private fun sendData() {

        val userName1 = ism_edit.text.toString().trim()
        val phoneNumber = tel_edit.text.toString().trim()
        val paswordd = parol_edit.text.toString().trim()
        val ism: String = ism_edit.text.toString().trim()
        val familiya: String = familiya_edit.text.toString().trim()
        val passport: String = passport_edit.text.toString().trim()
        val avtotur: String = kam.text.toString().trim()
        val avtoraqam: String = avto_raqam.text.toString().trim()
        val schot = "0"

        if (ism_edit.toString().isNotEmpty() && tel_edit.toString().isNotEmpty() && parol_edit.toString().isNotEmpty()) {
            val model = User(
                userName1, phoneNumber, paswordd,
                ism, familiya, passport, avtotur, avtoraqam,schot)
            preferences = getSharedPreferences("User",0)

            val edit = preferences.edit()

            edit.putString("userName",userName1)
            edit.putString("tel",phoneNumber)
            edit.putString("paswordd",paswordd)
            edit.putString("ism",ism)
            edit.putString("avtotur",avtotur)
            edit.putString("familiya",familiya)
            edit.putString("passport",passport)
            edit.putString("avtoraqam",avtoraqam)
            edit.putString("hisob",schot)
            edit.apply()
            reference.child(phoneNumber).setValue(model)


        }
    }

}

package com.example.jasstaxi.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jasstaxi.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.android.synthetic.main.activity_register_oyna2.*
import kotlinx.android.synthetic.main.activity_verify.*
import kotlinx.android.synthetic.main.register_layout.*
import java.security.Security.getProvider

class Verify : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setCancelable(false)

        auth=FirebaseAuth.getInstance()

        val storedVerificationId=intent.getStringExtra("storedVerificationId")

        verifyBtn.setOnClickListener {
            val otp = id_otp.text?.trim().toString()
            progressDialog.show()
            if (otp.isNotEmpty()) {
                val credential:PhoneAuthCredential = PhoneAuthCredential.zzb(storedVerificationId.toString(),otp.toString())
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this, "Parolni kiriting...", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    val fone = auth.currentUser.phoneNumber
                    Toast.makeText(this, "Kirdingiz $fone", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, CityOyna::class.java)
                    startActivity(intent)
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(
                        applicationContext,
                        "Kod notugri kiritildi...",
                        Toast.LENGTH_LONG
                    ).show()

               }
            }

    }
}
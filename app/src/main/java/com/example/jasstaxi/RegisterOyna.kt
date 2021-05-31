package com.example.jasstaxi

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.jasstaxi.databinding.ActivityRegisterOyna2Binding
import com.example.myapplication.User
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_malumotlar.*
import kotlinx.android.synthetic.main.activity_register_oyna2.*
import java.util.concurrent.TimeUnit

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RegisterOyna : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var progressDialog: ProgressDialog
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var binding: ActivityRegisterOyna2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterOyna2Binding.inflate(layoutInflater)
        setContentView(binding.root)





        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("User")
        auth.languageCode = "uz"


        pasword.visibility = View.GONE
        regbtn.visibility = View.GONE

        verbtn.setOnClickListener {
            var phones: String = phone12.text.trim().toString()
            if (phones.isNotEmpty()) {
                sendVerificationCode(phones)
                regbtn.visibility = View.VISIBLE
                pasword.visibility = View.VISIBLE
                userName.isVisible = false
                phone12.isVisible = false
                verbtn.isVisible = false
                passwords1.isVisible = false
                sendData()

            } else {
                Toast.makeText(this, "Nomerni kiriting...", Toast.LENGTH_LONG).show()
            }
        }
        regbtn.setOnClickListener {
            var otp = pasword.text.trim().toString()
            if (otp.isNotEmpty()) {

                signInWithPhoneAuthCredential(
                    credential = PhoneAuthCredential.zzb(otp,phone12.toString())


                )

            } else {
                Toast.makeText(this, "Parolni kiriting...", Toast.LENGTH_LONG).show()
            }

        }



        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                        progressDialog.dismiss()
                    Toast.makeText(this@RegisterOyna, "${e.message}", Toast.LENGTH_LONG).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    progressDialog.dismiss()
                }

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                resendToken = token
            }
        }

        val stringExtra = intent.getStringExtra("ism")

        Toast.makeText(this,"$stringExtra hammadan salom",Toast.LENGTH_LONG).show()

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    val fone = auth.currentUser.phoneNumber
                    Toast.makeText(this, "Kirdingiz $fone", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, BoshOyna::class.java)
                    startActivity(intent)


                } else {
                    Toast.makeText(
                        applicationContext,
                        "Code notugri kiritildi...",
                        Toast.LENGTH_LONG
                    ).show()

                    pasword.setText("")

                }
            }

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

    private fun sendData() {

        var userName1 = userName.text.toString().trim()
        var phoneNumber = phone12.text.toString().trim()
        var paswordd = passwords1.text.toString().trim()
        lateinit var ism: String
        lateinit var familiya: String
        lateinit var passport: String
        lateinit var avtorang: String
        lateinit var avtoraqam: String
        lateinit var avtotur: String
        ism = intent.getStringExtra("ism")!!.trim()
        familiya = intent.getStringExtra("familiya")!!.trim()
        passport = intent.getStringExtra("passport")!!.trim()
        avtotur = intent.getStringExtra("tur")!!.trim()
        avtoraqam = intent.getStringExtra("raqam")!!.trim()
        avtorang = intent.getStringExtra("rang")!!.trim()

        if (userName1.isNotEmpty() && phoneNumber.isNotEmpty() && paswordd.isNotEmpty()) {
            var model = User(
                userName1, phoneNumber, paswordd,
                ism, familiya, passport, avtotur, avtoraqam, avtorang)
            reference.child(phoneNumber).setValue(model)

            userName.setText("")
            phone12.setText("")
            passwords1.setText("")
        }
    }

}

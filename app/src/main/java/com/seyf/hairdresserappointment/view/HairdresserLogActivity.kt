package com.seyf.hairdresserappointment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.databinding.ActivityHairdresserLogBinding

class HairdresserLogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHairdresserLogBinding
    private lateinit var hdAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHairdresserLogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hdAuth = Firebase.auth

        val currentHd = hdAuth.currentUser

        if (currentHd != null) {
            val intent = Intent(this, HairdresserHomePageActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun hairdresserLog(view: View) {
        val hdEmail = binding.hdEmailText.text.toString()
        val hdPassword = binding.hdPassText.text.toString()
        if (hdEmail.equals("") || hdPassword.equals("")) {
            Toast.makeText(this, "Bilgileri eksiksiz doldurun!", Toast.LENGTH_LONG).show()
        } else {
            hdAuth.signInWithEmailAndPassword(hdEmail, hdPassword).addOnSuccessListener {
                val intent = Intent(this, HairdresserHomePageActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Giriş Yapılamadı!", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun hairdresserRegPage(view: View) {
        val intent = Intent(this, HairdresserRegActivity::class.java)
        startActivity(intent)
    }
}
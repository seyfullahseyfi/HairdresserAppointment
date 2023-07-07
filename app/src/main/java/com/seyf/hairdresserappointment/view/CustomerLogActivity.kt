package com.seyf.hairdresserappointment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.databinding.ActivityCustomerLogBinding

class CustomerLogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerLogBinding
    private lateinit var customerAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerLogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        customerAuth = Firebase.auth

        val currentCustomer = customerAuth.currentUser

        if (currentCustomer != null) {
            val intent = Intent(this, CustomerHomePageActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun customerLog(view: View) {

        val email = binding.custEmailText.text.toString()
        val custPassword = binding.custPassText.text.toString()
        if (email.equals("") || custPassword.equals("")) {
            Toast.makeText(this, "Bilgileri eksiksiz doldurun!", Toast.LENGTH_LONG).show()
        } else {
            customerAuth.signInWithEmailAndPassword(email, custPassword).addOnSuccessListener {
                val intent = Intent(this, CustomerHomePageActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Giriş Yapılamadı!", Toast.LENGTH_LONG).show()
            }
        }


    }

    fun customerRegPage(view: View) {
        val intent = Intent(this, CustomerRegActivity::class.java)
        startActivity(intent)
    }
}
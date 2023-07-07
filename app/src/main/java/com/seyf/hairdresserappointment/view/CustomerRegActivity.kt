package com.seyf.hairdresserappointment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.databinding.ActivityCustomerRegBinding

class CustomerRegActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerRegBinding
    private lateinit var customerAuth: FirebaseAuth
    private lateinit var customerFirestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerRegBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        customerAuth = Firebase.auth
        customerFirestore = Firebase.firestore
    }

    fun customerReg(view: View) {
        val custEmail = binding.custEmailText.text.toString()
        val custPassword = binding.custPassText.text.toString()
        val custName = binding.custNameText.text.toString()
        val custCity = binding.custCityText.text.toString()
        val custDistrict = binding.custDistrictText.text.toString()
        val custPhone = binding.custNumberText.text.toString()

        if (custEmail.equals("") || custPassword.equals("") || custName.equals("")
            || custCity.equals("") || custDistrict.equals("") || custPhone.equals("")
        ) {
            Toast.makeText(this, "Bilgileri eksiksiz doldurun!", Toast.LENGTH_LONG).show()
        } else {
            customerAuth.createUserWithEmailAndPassword(custEmail, custPassword)
                .addOnSuccessListener {
                    //success
                    val currentCust= customerAuth.currentUser
                    val currentId = currentCust!!.uid
                    val custDataMap = hashMapOf(
                        "custName" to custName,
                        "custMail" to custEmail,
                        "custCity" to custCity,
                        "custDistrict" to custDistrict,
                        "custPhone" to custPhone,
                        "custId" to currentId
                    )

                    customerFirestore.collection("Customer").document(currentCust!!.uid).set(custDataMap).addOnSuccessListener {
                        //başarılı olursa
                        val intent = Intent(this, CustomerHomePageActivity::class.java)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener {
                        //başarısız olursa
                    }

                }.addOnFailureListener {
                Toast.makeText(this, "Kullanıcı oluşturulamadı", Toast.LENGTH_LONG).show()
            }

        }

    }
}
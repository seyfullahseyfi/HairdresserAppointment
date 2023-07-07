package com.seyf.hairdresserappointment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.databinding.ActivityCustomerProfileBinding

class CustomerProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCustomerProfileBinding
    private lateinit var customerAuth: FirebaseAuth
    private lateinit var customerFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        customerAuth = Firebase.auth
        customerFirestore = Firebase.firestore

        getCustProfileData()
    }

    private fun getCustProfileData() {
        val currentCust = customerAuth.currentUser

        val docRef = customerFirestore.collection("Customer").document(currentCust!!.uid)
        docRef.get().addOnSuccessListener { document ->
            if (document!= null) {
                val custName = document.get("custName") as String
                val custEmail = document.get("custMail") as String
                val custPhone = document.get("custPhone") as String
                val custCity = document.get("custCity") as String
                val custDistrict = document.get("custDistrict") as String

                binding.customerNameTv.setText(custName)
                binding.customerEmailTv.setText(custEmail)
                binding.customerPhoneTv.setText(custPhone)
                binding.customerCityTv.setText(custCity)
                binding.customerDistrictTv.setText(custDistrict)

                binding.btnCustProfile.setOnClickListener {

                    val newCustProfileData = hashMapOf<String, Any>(
                        "custName" to binding.customerNameTv.text.toString(),
                        "custMail" to binding.customerEmailTv.text.toString(),
                        "custPhone" to binding.customerPhoneTv.text.toString(),
                        "custCity" to binding.customerCityTv.text.toString(),
                        "custDistrict" to binding.customerDistrictTv.text.toString(),
                    )
                    docRef.update(newCustProfileData)
                        .addOnSuccessListener {
                            Toast.makeText(this,"Bilgiler Güncellendi", Toast.LENGTH_LONG).show()
                        }.addOnFailureListener {

                        }

                }

            } else {

            }
        }
    }
}
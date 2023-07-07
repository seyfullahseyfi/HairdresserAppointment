package com.seyf.hairdresserappointment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.databinding.ActivityEmployeeRegBinding

class EmployeeRegActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeRegBinding
    private lateinit var hdAuth: FirebaseAuth
    private lateinit var hdFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeRegBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hdAuth = Firebase.auth
        hdFirestore = Firebase.firestore

        val currentHd = hdAuth.currentUser

        binding.employeeSave.setOnClickListener {
            val employeeName = binding.employeeNameText.text.toString()
            val employeeContact = binding.employeeContactText.text.toString()
            val employeeWork = binding.employeeWorkText.text.toString()
            val employeeServiceName = binding.employeeServiceRegNameText.text.toString()
            val employeeServiceTime = binding.employeeServiceTimeRegText.text.toString()
            val employeeServicePrice = binding.employeeServicePriceRegText.text.toString()
            val workingStatus = "Çalışıyor"
            val empRating = 0.0.toString()

            if (employeeName.equals("") || employeeContact.equals("") || employeeWork.equals("")) {
                Toast.makeText(this, "Bilgileri eksiksiz doldurun!", Toast.LENGTH_LONG).show()
            } else {
                if (currentHd != null) {
                    val hdId = currentHd.uid
                    val employeeData = hashMapOf(
                        "employeeName" to employeeName,
                        "employeeContact" to employeeContact,
                        "employeeWork" to employeeWork,
                        "workingStatus" to workingStatus,
                        "averageRating" to empRating

                    )
                 hdFirestore.collection("HairDresser").document(hdId).collection("Employees")
                        .add(employeeData)
                        .addOnSuccessListener {
                            val employeeDocId = it.id
                            val employeeServiceData = hashMapOf(
                                "employeeServiceName" to employeeServiceName,
                                "employeeServiceTime" to employeeServiceTime,
                                "employeeServicePrice" to employeeServicePrice
                            )
                                hdFirestore.collection("HairDresser").document(hdId).collection("Employees")
                                    .document(employeeDocId).collection("Services").add(employeeServiceData).addOnSuccessListener {
                                        Toast.makeText(this, "Hizmet Eklendi", Toast.LENGTH_LONG).show()
                                    }.addOnFailureListener {
                                        Toast.makeText(this, "Hizmet Eklenemedi", Toast.LENGTH_LONG).show()
                                    }


                            //personel kaydı başarılı oldu gerçekleşecekler

                            Toast.makeText(this, "Personel kaydedildi", Toast.LENGTH_LONG).show()

                        }.addOnFailureListener {
                            //personel kaydı başarısız oldu gerçekleşecekler
                            Toast.makeText(this, "Personel kaydedilemedi", Toast.LENGTH_LONG).show()

                        }
                }
            }

        }
        binding.goList.setOnClickListener {
            val intent = Intent(this, EmployeesListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
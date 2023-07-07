package com.seyf.hairdresserappointment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.databinding.ActivityAddServicesBinding

class AddServicesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddServicesBinding
    private lateinit var hdFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddServicesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hdFirestore = Firebase.firestore

        val empAddServiceIntent = intent
        val employeeName = empAddServiceIntent.getStringExtra("employeeName")
        val hdEmpId = empAddServiceIntent.getStringExtra("hairDrEmpId")
        val hdDocId = empAddServiceIntent.getStringExtra("hairDrDocId")

        val employeeId = hdEmpId.toString()
        val hairDresserId = hdDocId.toString()
        binding.textView28.text = "Hizmet eklenecek Personelin adı: " + employeeName

        binding.employeeServiceAddBtn.setOnClickListener {
            val employeeServiceName = binding.employeeServiceNameText.text.toString()
            val employeeServiceTime = binding.employeeServiceTimeText.text.toString()
            val employeeServicePrice = binding.employeeServicePriceText.text.toString()
            val employeeServiceData = hashMapOf(
                "employeeServiceName" to employeeServiceName,
                "employeeServiceTime" to employeeServiceTime,
                "employeeServiceTime" to employeeServicePrice
            )
            hdFirestore.collection("HairDresser").document(hairDresserId).collection("Employees")
                .document(employeeId).collection("Services").add(employeeServiceData).addOnSuccessListener {
                    Toast.makeText(this, "Hizmet Eklendi", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Hizmet Eklenemedi", Toast.LENGTH_LONG).show()
                }

        }
    }
}
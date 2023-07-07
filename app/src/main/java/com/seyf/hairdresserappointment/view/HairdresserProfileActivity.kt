package com.seyf.hairdresserappointment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.databinding.ActivityHairdresserProfileBinding

class HairdresserProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHairdresserProfileBinding
    private lateinit var hdAuth: FirebaseAuth
    private lateinit var hdFirestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHairdresserProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hdAuth = Firebase.auth
        hdFirestore = Firebase.firestore

        getHdProfileDate()
    }


    private fun getHdProfileDate() {

        val currentHd = hdAuth.currentUser

        val docRef = hdFirestore.collection("HairDresser").document(currentHd!!.uid)
        docRef.get().addOnSuccessListener { document ->
            if (document!= null) {
                val hdName = document.get("hdName") as String
                val hdType = document.get("hdType") as String
                val hdWk = document.get("hdWk") as String
                val hdEmail = document.get("hdMail") as String
                val hdPhone = document.get("hdPhone") as String
                val hdCity = document.get("hdCity") as String
                val hdDistrict = document.get("hdDistrict") as String
                val hdAddress = document.get("hdAddress") as String

                binding.hairdresserNameTv.setText(hdName)
                binding.hairdresserTypeTv.setText(hdType)
                binding.hairdresserWkTv.setText(hdWk)
                binding.hairdresserEmailTv.setText(hdEmail)
                binding.hairdresserPhoneTv.setText(hdPhone)
                binding.hairdresserCityTv.setText(hdCity)
                binding.hairdresserDistrictTv.setText(hdDistrict)
                binding.hairdresserAddressTv.setText(hdAddress)


                binding.btnHdProfile.setOnClickListener {
                    val newHdProfileData = hashMapOf<String, Any>(
                        "hdName" to binding.hairdresserNameTv.text.toString(),
                        "hdType" to binding.hairdresserTypeTv.text.toString(),
                        "hdWk" to binding.hairdresserWkTv.text.toString(),
                        "hdMail" to binding.hairdresserEmailTv.text.toString(),
                        "hdPhone" to binding.hairdresserPhoneTv.text.toString(),
                        "hdCity" to binding.hairdresserCityTv.text.toString(),
                        "hdDistrict" to binding.hairdresserDistrictTv.text.toString(),
                        "hdAddress" to binding.hairdresserAddressTv.text.toString(),
                    )
                    docRef.update(newHdProfileData)
                        .addOnSuccessListener {
                                    Toast.makeText(this,"Bilgiler Güncellendi",Toast.LENGTH_LONG).show()
                        }.addOnFailureListener {

                        }
                }

            } else {

            }
        }
    }
}
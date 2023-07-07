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
import com.seyf.hairdresserappointment.databinding.ActivityPriceRegBinding

class PriceRegActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPriceRegBinding
    private lateinit var hdAuth: FirebaseAuth
    private lateinit var hdFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPriceRegBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hdAuth = Firebase.auth
        hdFirestore = Firebase.firestore


    }

    //hizmetin fiyat bilgilerinin kaydedilmesi
    fun serviceSave(view: View) {
        val currentHd = hdAuth.currentUser

        val serviceName = binding.serviceNameText.text.toString()
        val serviceDuration = binding.serviceDurationText.text.toString()
        val servicePrice = binding.servicePriceText.text.toString()
        if (serviceName.equals("") || serviceDuration.equals("") || servicePrice.equals("")) {
            Toast.makeText(this, "Bilgileri eksiksiz doldurun!", Toast.LENGTH_LONG).show()
        } else {
            if (currentHd != null) {
                val hdId = currentHd.uid
                val priceData = hashMapOf(
                    "serviceName" to serviceName,
                    "serviceDuration" to serviceDuration,
                    "servicePrice" to servicePrice
                )
                hdFirestore.collection("HairDresser").document(hdId).collection("Prices")
                    .add(priceData)
                    .addOnSuccessListener {
                        //personel kaydı başarılı oldu gerçekleşecekler
                        Toast.makeText(this, "Fiyat kaydedildi", Toast.LENGTH_LONG).show()

                    }.addOnFailureListener {
                        //personel kaydı başarısız oldu gerçekleşecekler
                        Toast.makeText(this, "Fiyat kaydedilemedi", Toast.LENGTH_LONG).show()

                    }
            }
        }

    }

    fun goPriceList(view: View) {
        val intent = Intent(this, PriceListActivity::class.java)
        startActivity(intent)
        finish()
    }

}
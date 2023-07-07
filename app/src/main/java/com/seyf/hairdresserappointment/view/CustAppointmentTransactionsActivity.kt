package com.seyf.hairdresserappointment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.databinding.ActivityCustAppointmentTransactionsBinding
import java.text.DecimalFormat

class CustAppointmentTransactionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustAppointmentTransactionsBinding
    private lateinit var custFirestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustAppointmentTransactionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        custFirestore = Firebase.firestore

        binding.ratingBar.visibility = View.GONE
        binding.ratingBtn.visibility = View.GONE

        val custAppointmentTransactionsIntent = intent
        val appointmentId = custAppointmentTransactionsIntent.getStringExtra("appointmentId") as String
        val appointmentStatus = custAppointmentTransactionsIntent.getStringExtra("appointmentStatus") as String
        val hairDresserId = custAppointmentTransactionsIntent.getStringExtra("hairDresserId") as String
        val employeeId = custAppointmentTransactionsIntent.getStringExtra("employeeId") as String

        val appointmentDocRef = custFirestore.collection("Appointments").document(appointmentId)

        if (appointmentStatus =="Kuaför Tarafından İptal Edildi") {
            binding.statusTv.visibility = View.VISIBLE
            binding.btnCancel.isEnabled = false
            binding.statusTv.text = "Randevu kuaför tarafından iptal edilmiş. Randevu Durumunu Değiştiremezsiniz."

        } else if (appointmentStatus =="Randevu Gerçekleşti") {
            binding.statusTv.visibility = View.VISIBLE
            binding.btnCancel.isEnabled = false
            binding.statusTv.text = "Randevu Gerçekleşmiş. Randevu Durumunu Değiştiremezsiniz."

            binding.ratingBar.visibility = View.VISIBLE
            binding.ratingBtn.visibility = View.VISIBLE

            binding.ratingBtn.setOnClickListener {
                val rating = binding.ratingBar.rating.toString()

                val ratingUpdates = hashMapOf<String, Any>(
                    "rating" to rating
                )
                appointmentDocRef.update(ratingUpdates).addOnSuccessListener {
                    //değerlendirma kaydedildi
                    Toast.makeText(this,"Değerlendirme kaydedildi.",Toast.LENGTH_LONG).show()

                    val appointmentsRef = custFirestore.collection("Appointments").whereEqualTo("employeeDocId",employeeId)
                        .whereEqualTo("appointmentStatus","Randevu Gerçekleşti")

                    appointmentsRef.get().addOnSuccessListener {documents->
                        if (!documents.isEmpty) {
                            var totalRating = 0.0
                            var numberOfAppointment = 0

                            for (document in documents) {
                                val rating = document.get("rating") as String

                                if (rating!= null) {
                                    totalRating += rating.toDouble()
                                    numberOfAppointment++

                                }

                            }

                            val ratingAverageDouble = if (numberOfAppointment>0) totalRating / numberOfAppointment else 0.0

                            val decimalFormat = DecimalFormat("#.#")
                            decimalFormat.maximumFractionDigits = 1
                            val ratingAverage = decimalFormat.format(ratingAverageDouble).toString()

                            val employeeRef = custFirestore.collection("HairDresser").document(hairDresserId)
                                .collection("Employees").document(employeeId)
                            employeeRef.update("averageRating",ratingAverage)

                        }

                    }

                }.addOnFailureListener {
                    //hata durumu
                    Toast.makeText(this,"Değerlendirme sırasında hata oluştu.",Toast.LENGTH_LONG).show()
                }
                binding.ratingBtn.isEnabled = false

                val intent = Intent(this,CustomerHomePageActivity::class.java)
                startActivity(intent)

            }


        } else if (appointmentStatus =="Müşteri Gelmedi") {
            binding.statusTv.visibility = View.VISIBLE
            binding.btnCancel.isEnabled = false
            binding.statusTv.text = "Randevu Tarihi Geçmiş. Randevu Durumunu Değiştiremezsiniz."


        } else if(appointmentStatus =="Müşteri Tarafından İptal Edildi") {
            binding.statusTv.visibility = View.VISIBLE
            binding.btnCancel.isEnabled = false
            binding.statusTv.text = "Randevuyu Daha Önce İptal Etmişsiniz."
        } else {
            binding.statusTv.visibility = View.GONE
            binding.btnCancel.setOnClickListener {
                binding.btnCancel.isEnabled = true

                val newStatus = "Müşteri Tarafından İptal Edildi"

                appointmentDocRef.update("appointmentStatus", newStatus).addOnSuccessListener {
                    Toast.makeText(this, "Randevu Durumu GÜncellendi", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Randevu Durumu GÜncellenemedi", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
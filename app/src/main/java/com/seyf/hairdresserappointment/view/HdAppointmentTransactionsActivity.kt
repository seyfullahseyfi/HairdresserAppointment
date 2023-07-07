package com.seyf.hairdresserappointment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.databinding.ActivityHdAppointmentTransactionsBinding

class HdAppointmentTransactionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHdAppointmentTransactionsBinding
    private lateinit var hdFirestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHdAppointmentTransactionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hdFirestore = Firebase.firestore

        val hdAppointmentTransactionsIntent = intent
        val appointmentId = hdAppointmentTransactionsIntent.getStringExtra("appointmentId") as String
        val appointmentStatus = hdAppointmentTransactionsIntent.getStringExtra("appointmentStatus") as String

        val appointmentDocRef = hdFirestore.collection("Appointments").document(appointmentId)


        if (appointmentStatus =="Müşteri Tarafından İptal Edildi") {
            binding.btnAppCompleted.isEnabled = false
            binding.btnAppCancel.isEnabled = false
            binding.btnCustNotCome.isEnabled = false
            binding.statusTextView.visibility = View.VISIBLE

            binding.statusTextView.text = "Randevu Müşteri Tarafından İptal Edilmiş.Randevu Durumunu Değiştiremezsiniz"

        } else {
            binding.statusTextView.visibility = View.GONE
            binding.btnAppCancel.setOnClickListener {
                val newStatus = "Kuaför Tarafından İptal Edildi"

                appointmentDocRef.update("appointmentStatus", newStatus).addOnSuccessListener {
                    Toast.makeText(this, "Randevu Durumu GÜncellendi", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Randevu Durumu GÜncellenemedi", Toast.LENGTH_LONG).show()
                }
            }

            binding.btnAppCompleted.setOnClickListener {
                binding.statusTextView.visibility = View.GONE
                val newStatus = "Randevu Gerçekleşti"

                appointmentDocRef.update("appointmentStatus", newStatus).addOnSuccessListener {
                    Toast.makeText(this, "Randevu Durumu GÜncellendi", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Randevu Durumu GÜncellenemedi", Toast.LENGTH_LONG).show()
                }
            }

            binding.btnCustNotCome.setOnClickListener {
                binding.statusTextView.visibility = View.GONE
                val newStatus = "Müşteri Gelmedi"

                appointmentDocRef.update("appointmentStatus", newStatus).addOnSuccessListener {
                    Toast.makeText(this, "Randevu Durumu GÜncellendi", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Randevu Durumu GÜncellenemedi", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}
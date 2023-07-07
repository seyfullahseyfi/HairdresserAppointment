package com.seyf.hairdresserappointment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.adapter.HdAppointmentRecyclerAdapter
import com.seyf.hairdresserappointment.databinding.ActivityHairDresserAppointmentBinding
import com.seyf.hairdresserappointment.model.HdAppointment

class HairDresserAppointmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHairDresserAppointmentBinding
    private lateinit var hdAuth: FirebaseAuth
    private lateinit var hdFirestore: FirebaseFirestore

    private lateinit var hdAppointmentArrayList: ArrayList<HdAppointment>
    private lateinit var hdAppointmentAdapter: HdAppointmentRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHairDresserAppointmentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        hdAuth = Firebase.auth
        hdFirestore = Firebase.firestore
        binding.totalPriceTv.visibility = View.GONE

        hdAppointmentArrayList = ArrayList<HdAppointment>()

        binding.hdAppointments.layoutManager = LinearLayoutManager(this)
        hdAppointmentAdapter = HdAppointmentRecyclerAdapter(
            hdAppointmentArrayList,
            this@HairDresserAppointmentActivity
        )
        binding.hdAppointments.adapter = hdAppointmentAdapter

        binding.hdAllBtn.setOnClickListener {
            getListHdAllAppointment()
        }

        binding.hdActiveBtn.setOnClickListener {
            getListHdActiveAppointment()
        }

        binding.hdCancelledBtn.setOnClickListener {
            getListHdCancelledAppointment()
        }

        binding.hdCompletedBtn.setOnClickListener {
            getListHdCompletedAppointment()
        }

    }
        private fun getListHdAllAppointment() {
            val currentHd = hdAuth.currentUser!!.uid
            //tümü seçiliyse
            binding.totalPriceTv.visibility = View.GONE
            val allAppointment = hdFirestore.collection("Appointments").whereEqualTo("hairDresserDocId", currentHd)

            allAppointment.get().addOnSuccessListener { documents ->
                //sorgu başarılı olursa
                if (!documents.isEmpty) {
                    hdAppointmentArrayList.clear()

                    var totalPrice = 0.0

                    for (document in documents) {
                        val customerName = document.get("customerName") as String
                        val employeeName = document.get("employeeName") as String
                        val appointmentTime = document.get("selectedTime") as String
                        val appointmentDate = document.get("selectedDate") as String
                        val customerContact = document.get("customerContact") as String
                        val appointmentStatus = document.get("appointmentStatus") as String
                        val serviceName = document.get("serviceName") as String
                        val serviceTime = document.get("serviceTime") as String
                        val servicePrice = document.get("servicePrice") as String
                        val appointmentDocId = document.id

                        val appointmentTimeAndDate = "$appointmentDate  $appointmentTime"


                        val appointmentPrice = servicePrice.toDoubleOrNull()
                        totalPrice += appointmentPrice!!

                        val hdAppointment = HdAppointment(
                            customerName,
                            employeeName,
                            appointmentTimeAndDate,
                            customerContact,
                            appointmentStatus,
                            serviceName,
                            serviceTime,
                            servicePrice,
                            appointmentDocId
                        )
                        hdAppointmentArrayList.add(hdAppointment)
                    }
                    binding.totalPriceTv.text = "Toplam Kazanç: $totalPrice TL"

                    hdAppointmentAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Randevu bulunamadı", Toast.LENGTH_LONG)
                        .show()
                }

            }
        }

        private fun getListHdActiveAppointment() {
        val currentHd = hdAuth.currentUser!!.uid
        //aktif seçiliyse
        binding.totalPriceTv.visibility = View.GONE
            val activeAppointment = hdFirestore.collection("Appointments").whereEqualTo("hairDresserDocId", currentHd)
            .whereEqualTo("appointmentStatus", "Aktif")

            activeAppointment.get().addOnSuccessListener { documents ->
            //sorgu başarılı olursa
            if (!documents.isEmpty) {
                hdAppointmentArrayList.clear()

                var totalPrice = 0.0

                for (document in documents) {
                    val customerName = document.get("customerName") as String
                    val employeeName = document.get("employeeName") as String
                    val appointmentTime = document.get("selectedTime") as String
                    val appointmentDate = document.get("selectedDate") as String
                    val customerContact = document.get("customerContact") as String
                    val appointmentStatus = document.get("appointmentStatus") as String
                    val serviceName = document.get("serviceName") as String
                    val serviceTime = document.get("serviceTime") as String
                    val servicePrice = document.get("servicePrice") as String
                    val appointmentDocId = document.id

                    val appointmentTimeAndDate = "$appointmentDate  $appointmentTime"


                    val appointmentPrice = servicePrice.toDoubleOrNull()
                    totalPrice += appointmentPrice!!

                    val hdAppointment = HdAppointment(
                        customerName,
                        employeeName,
                        appointmentTimeAndDate,
                        customerContact,
                        appointmentStatus,
                        serviceName,
                        serviceTime,
                        servicePrice,
                        appointmentDocId
                    )
                    hdAppointmentArrayList.add(hdAppointment)
                }
                binding.totalPriceTv.text = "Toplam Kazanç: $totalPrice TL"

                hdAppointmentAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Randevu bulunamadı", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

        private fun getListHdCancelledAppointment() {
        val currentHd = hdAuth.currentUser!!.uid

        binding.totalPriceTv.visibility = View.GONE
            val cancelledAppointment = hdFirestore.collection("Appointments").whereEqualTo("hairDresserDocId", currentHd)
            .whereEqualTo("appointmentStatus", "Kuaför Tarafından İptal Edildi")

            cancelledAppointment.get().addOnSuccessListener { documents ->
            //sorgu başarılı olursa
            if (!documents.isEmpty) {
                hdAppointmentArrayList.clear()

                var totalPrice = 0.0

                for (document in documents) {
                    val customerName = document.get("customerName") as String
                    val employeeName = document.get("employeeName") as String
                    val appointmentTime = document.get("selectedTime") as String
                    val appointmentDate = document.get("selectedDate") as String
                    val customerContact = document.get("customerContact") as String
                    val appointmentStatus = document.get("appointmentStatus") as String
                    val serviceName = document.get("serviceName") as String
                    val serviceTime = document.get("serviceTime") as String
                    val servicePrice = document.get("servicePrice") as String
                    val appointmentDocId = document.id

                    val appointmentTimeAndDate = "$appointmentDate  $appointmentTime"


                    val appointmentPrice = servicePrice.toDoubleOrNull()
                    totalPrice += appointmentPrice!!

                    val hdAppointment = HdAppointment(
                        customerName,
                        employeeName,
                        appointmentTimeAndDate,
                        customerContact,
                        appointmentStatus,
                        serviceName,
                        serviceTime,
                        servicePrice,
                        appointmentDocId
                    )
                    hdAppointmentArrayList.add(hdAppointment)
                }
                binding.totalPriceTv.text = "Toplam Kazanç: $totalPrice TL"

                hdAppointmentAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Randevu bulunamadı", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

        private fun getListHdCompletedAppointment() {
        val currentHd = hdAuth.currentUser!!.uid
        //tamamlanmış/geçmiş seçiliyse
        binding.totalPriceTv.visibility = View.VISIBLE
        val completedAppointment = hdFirestore.collection("Appointments").whereEqualTo("hairDresserDocId", currentHd)
        .whereEqualTo("appointmentStatus", "Randevu Gerçekleşti")

        completedAppointment.get().addOnSuccessListener { documents ->
            //sorgu başarılı olursa
            if (!documents.isEmpty) {
                hdAppointmentArrayList.clear()

                var totalPrice = 0.0

                for (document in documents) {
                    val customerName = document.get("customerName") as String
                    val employeeName = document.get("employeeName") as String
                    val appointmentTime = document.get("selectedTime") as String
                    val appointmentDate = document.get("selectedDate") as String
                    val customerContact = document.get("customerContact") as String
                    val appointmentStatus = document.get("appointmentStatus") as String
                    val serviceName = document.get("serviceName") as String
                    val serviceTime = document.get("serviceTime") as String
                    val servicePrice = document.get("servicePrice") as String
                    val appointmentDocId = document.id

                    val appointmentTimeAndDate = "$appointmentDate  $appointmentTime"


                    val appointmentPrice = servicePrice.toDoubleOrNull()
                    totalPrice += appointmentPrice!!

                    val hdAppointment = HdAppointment(
                        customerName,
                        employeeName,
                        appointmentTimeAndDate,
                        customerContact,
                        appointmentStatus,
                        serviceName,
                        serviceTime,
                        servicePrice,
                        appointmentDocId
                    )
                    hdAppointmentArrayList.add(hdAppointment)
                }
                binding.totalPriceTv.text = "Toplam Kazanç: $totalPrice TL"

                hdAppointmentAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Randevu bulunamadı", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

    }

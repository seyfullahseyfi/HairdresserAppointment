package com.seyf.hairdresserappointment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.adapter.CustAppointmentRecyclerAdapter
import com.seyf.hairdresserappointment.databinding.ActivityCustomerAppointmentBinding
import com.seyf.hairdresserappointment.databinding.ActivityCustomerHomePageBinding
import com.seyf.hairdresserappointment.model.CustAppointment
import com.seyf.hairdresserappointment.model.Hairdresser

class CustomerAppointmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerAppointmentBinding
    private lateinit var custAuth: FirebaseAuth
    private lateinit var custFirestore: FirebaseFirestore

    private lateinit var custAppointmentArrayList: ArrayList<CustAppointment>
    private lateinit var custAppointmentAdapter: CustAppointmentRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerAppointmentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        custAuth = Firebase.auth
        custFirestore = Firebase.firestore

        custAppointmentArrayList = ArrayList<CustAppointment>()
        binding.custCancelledBtn.setOnClickListener {
            getListCustCancelledAppointment()
        }

        binding.custAllBtn.setOnClickListener {
            getListCustAllAppointment()
        }

        binding.custActiveBtn.setOnClickListener {
            getListCustActiveAppointment()
        }

        binding.custCompletedBtn.setOnClickListener {
            getListCustCompletedAppointment()
        }


        binding.custAppointments.layoutManager = LinearLayoutManager(this)
        custAppointmentAdapter = CustAppointmentRecyclerAdapter(custAppointmentArrayList, this@CustomerAppointmentActivity)
        binding.custAppointments.adapter = custAppointmentAdapter
    }

    private fun getListCustCancelledAppointment() {
        val currentCust = custAuth.currentUser!!.uid

        val query = custFirestore.collection("Appointments").whereEqualTo("customerId", currentCust)
            .whereEqualTo("appointmentStatus","Müşteri Tarafından İptal Edildi")

        query.get().addOnSuccessListener { documents ->
            //sorgu başarılı olursa
            if (!documents.isEmpty) {
                custAppointmentArrayList.clear()

                for (document in documents) {
                    val hairdresserName = document.get("hairDresserName") as String
                    val employeeName = document.get("employeeName") as String
                    val serviceName = document.get("serviceName") as String
                    val appointmentTime = document.get("selectedTime") as String
                    val appointmentDate = document.get("selectedDate") as String
                    val appointmentStatus = document.get("appointmentStatus") as String
                    val hairDresserAddress = document.get("hairDresserAddress") as String
                    val employeeContact = document.get("employeeContact") as String
                    val servicePrice = document.get("servicePrice") as String
                    val serviceTime = document.get("serviceTime") as String
                    val hairDresserId = document.get("hairDresserDocId") as String
                    val employeeId = document.get("employeeDocId") as String
                    val appointmentDocId = document.id

                    val appointmentTimeAndDate = "$appointmentDate  $appointmentTime"


                    val custAppointment = CustAppointment(
                        hairdresserName,
                        employeeName,
                        serviceName,
                        appointmentTimeAndDate,
                        appointmentStatus,
                        hairDresserAddress,
                        employeeContact,
                        servicePrice,
                        serviceTime,
                        appointmentDocId,hairDresserId,employeeId
                    )
                    custAppointmentArrayList.add(custAppointment)

                }
                custAppointmentAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Randevu bulunamadı", Toast.LENGTH_LONG)
                    .show()
            }

        }

    }

    private fun getListCustAllAppointment() {
        val currentCust = custAuth.currentUser!!.uid

        val query = custFirestore.collection("Appointments").whereEqualTo("customerId", currentCust)

        query.get().addOnSuccessListener { documents ->
            //sorgu başarılı olursa
            if (!documents.isEmpty) {
                custAppointmentArrayList.clear()

                for (document in documents) {
                    val hairdresserName = document.get("hairDresserName") as String
                    val employeeName = document.get("employeeName") as String
                    val serviceName = document.get("serviceName") as String
                    val appointmentTime = document.get("selectedTime") as String
                    val appointmentDate = document.get("selectedDate") as String
                    val appointmentStatus = document.get("appointmentStatus") as String
                    val hairDresserAddress = document.get("hairDresserAddress") as String
                    val employeeContact = document.get("employeeContact") as String
                    val servicePrice = document.get("servicePrice") as String
                    val serviceTime = document.get("serviceTime") as String
                    val hairDresserId = document.get("hairDresserDocId") as String
                    val employeeId = document.get("employeeDocId") as String
                    val appointmentDocId = document.id

                    val appointmentTimeAndDate = "$appointmentDate  $appointmentTime"

                    val custAppointment = CustAppointment(
                        hairdresserName,
                        employeeName,
                        serviceName,
                        appointmentTimeAndDate,
                        appointmentStatus,
                        hairDresserAddress,
                        employeeContact,
                        servicePrice,
                        serviceTime,
                        appointmentDocId,hairDresserId,employeeId
                    )
                    custAppointmentArrayList.add(custAppointment)

                }
                custAppointmentAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Randevu bulunamadı", Toast.LENGTH_LONG)
                    .show()
            }

        }

    }

    private fun getListCustActiveAppointment() {
        val currentCust = custAuth.currentUser!!.uid

        val query = custFirestore.collection("Appointments").whereEqualTo("customerId", currentCust)
            .whereEqualTo("appointmentStatus","Aktif")


        query.get().addOnSuccessListener { documents ->
            //sorgu başarılı olursa
            if (!documents.isEmpty) {
                custAppointmentArrayList.clear()

                for (document in documents) {
                    val hairdresserName = document.get("hairDresserName") as String
                    val employeeName = document.get("employeeName") as String
                    val serviceName = document.get("serviceName") as String
                    val appointmentTime = document.get("selectedTime") as String
                    val appointmentDate = document.get("selectedDate") as String
                    val appointmentStatus = document.get("appointmentStatus") as String
                    val hairDresserAddress = document.get("hairDresserAddress") as String
                    val employeeContact = document.get("employeeContact") as String
                    val servicePrice = document.get("servicePrice") as String
                    val serviceTime = document.get("serviceTime") as String
                    val hairDresserId = document.get("hairDresserDocId") as String
                    val employeeId = document.get("employeeDocId") as String
                    val appointmentDocId = document.id

                    val appointmentTimeAndDate = "$appointmentDate  $appointmentTime"


                    val custAppointment = CustAppointment(
                        hairdresserName,
                        employeeName,
                        serviceName,
                        appointmentTimeAndDate,
                        appointmentStatus,
                        hairDresserAddress,
                        employeeContact,
                        servicePrice,
                        serviceTime,
                        appointmentDocId,hairDresserId,employeeId
                    )
                    custAppointmentArrayList.add(custAppointment)

                }
                custAppointmentAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Randevu bulunamadı", Toast.LENGTH_LONG)
                    .show()
            }

        }

    }

    private fun getListCustCompletedAppointment() {
        val currentCust = custAuth.currentUser!!.uid

        val query = custFirestore.collection("Appointments").whereEqualTo("customerId", currentCust)
            .whereEqualTo("appointmentStatus","Randevu Gerçekleşti")


        query.get().addOnSuccessListener { documents ->
            //sorgu başarılı olursa
            if (!documents.isEmpty) {
                custAppointmentArrayList.clear()

                for (document in documents) {
                    val hairdresserName = document.get("hairDresserName") as String
                    val employeeName = document.get("employeeName") as String
                    val serviceName = document.get("serviceName") as String
                    val appointmentTime = document.get("selectedTime") as String
                    val appointmentDate = document.get("selectedDate") as String
                    val appointmentStatus = document.get("appointmentStatus") as String
                    val hairDresserAddress = document.get("hairDresserAddress") as String
                    val employeeContact = document.get("employeeContact") as String
                    val servicePrice = document.get("servicePrice") as String
                    val serviceTime = document.get("serviceTime") as String
                    val hairDresserId = document.get("hairDresserDocId") as String
                    val employeeId = document.get("employeeDocId") as String
                    val appointmentDocId = document.id

                    val appointmentTimeAndDate = "$appointmentDate  $appointmentTime"


                    val custAppointment = CustAppointment(
                        hairdresserName,
                        employeeName,
                        serviceName,
                        appointmentTimeAndDate,
                        appointmentStatus,
                        hairDresserAddress,
                        employeeContact,
                        servicePrice,
                        serviceTime,
                        appointmentDocId,hairDresserId,employeeId
                    )
                    custAppointmentArrayList.add(custAppointment)

                }
                custAppointmentAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Randevu bulunamadı", Toast.LENGTH_LONG)
                    .show()
            }

        }

    }
}
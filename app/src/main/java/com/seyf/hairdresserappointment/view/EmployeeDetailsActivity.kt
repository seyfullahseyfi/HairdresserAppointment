package com.seyf.hairdresserappointment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.adapter.ServiceHdRecyclerAdapter
import com.seyf.hairdresserappointment.databinding.ActivityEmployeeDetailsBinding
import com.seyf.hairdresserappointment.model.ServiceHd

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeDetailsBinding
    private lateinit var hdFirestore: FirebaseFirestore

    private lateinit var serviceArrayList: ArrayList<ServiceHd>
    private lateinit var serviceAdapter: ServiceHdRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hdFirestore = Firebase.firestore

        //personel detay sayfasına kuaför giriş yaparsa

        val hdEmpDetailsIntent = intent
        val hdEmpNameDet = hdEmpDetailsIntent.getStringExtra("empCardName") as String
        val hdEmpContDet = hdEmpDetailsIntent.getStringExtra("empCardCont") as String
        val hdEmpWorkDet = hdEmpDetailsIntent.getStringExtra("empCardWork") as String
        val hdEmpId = hdEmpDetailsIntent.getStringExtra("empId") as String
        val hdDocId =hdEmpDetailsIntent.getStringExtra("hairId") as String
        val hdHairDresserName = hdEmpDetailsIntent.getStringExtra("hairDresserName") as String
    //    val hdCustomerName = hdEmpDetailsIntent.getStringExtra("customerName") as String
    //    val hdCustomerId = hdEmpDetailsIntent.getStringExtra("customerId") as String


        binding.empDetName.text = hdEmpNameDet
        binding.empDetCont.text = hdEmpContDet
        binding.empDetWork.text = hdEmpWorkDet

        var hairDrEmpId = hdEmpId
        var hairDrDocId = hdDocId
        var employeeName = hdEmpNameDet

        serviceArrayList = ArrayList<ServiceHd>()

        binding.employeeServicesRecycler.layoutManager = LinearLayoutManager(this)
        serviceAdapter = ServiceHdRecyclerAdapter(serviceArrayList,this@EmployeeDetailsActivity)
        binding.employeeServicesRecycler.adapter = serviceAdapter

        //hizmetlerin listelenmesi
        val employeeServiceRef = hdFirestore.collection("HairDresser")
            .document(hdDocId).collection("Employees").document(hdEmpId).collection("Services")

        employeeServiceRef.get().addOnSuccessListener { querySnapshot->

            serviceArrayList.clear()

            for (document in querySnapshot) {

                val serviceName = document.get("employeeServiceName") as String
                val serviceTime = document.get("employeeServiceTime") as String
                val servicePrice = document.get("employeeServicePrice") as String
                val serviceId = document.id
           //     val rating = document.get("averageRating") as String

                val service = ServiceHd(serviceName,serviceTime,servicePrice)
                serviceArrayList.add(service)

            //    binding.ratingTv.text = "Puanı: $rating"
            }
            serviceAdapter.notifyDataSetChanged()
        }

        val employeeRef = hdFirestore.collection("HairDresser").document(hdDocId).collection("Employees").document(hdEmpId)

        binding.toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->

            val newStatus = if (isChecked) "İzinli" else "Çalışıyor"

            //çalışma durmunu güncelle
            employeeRef.update("workingStatus",newStatus).addOnSuccessListener {
                //güncelleme başarılı
                Toast.makeText(this,"Çalışma Durumu GÜncellendi",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                //güncelleme başarısız
                Toast.makeText(this,"Çalışma Durumu Güncellenemedi",Toast.LENGTH_LONG).show()
            }

        }


        hdAddService(hairDrEmpId,hairDrDocId,employeeName)


        val query = hdFirestore.collection("Appointments").whereEqualTo("employeeDocId",hdEmpId).
        whereEqualTo("appointmentStatus","Randevu Gerçekleşti")

        query.get().addOnSuccessListener { documents->

            if (!documents.isEmpty) {
                var totalPrice = 0.0

                for (document in documents) {
                    val servicePrice = document.get("servicePrice") as String

                    val appointmentPrice = servicePrice.toDoubleOrNull()
                    totalPrice += appointmentPrice!!

                }
                binding.empTotalPrice.text = "Toplam Kazanç: $totalPrice TL"

            }

        }


    }


    fun hdAddService(hairDrEmpId:String,hairDrDocId:String,employeeName:String) {
        binding.employeeServiceAddBtn.setOnClickListener {
            val empAddServiceIntent = Intent(this,AddServicesActivity::class.java)
            empAddServiceIntent.putExtra("hairDrEmpId",hairDrEmpId)
            empAddServiceIntent.putExtra("hairDrDocId",hairDrDocId)
            empAddServiceIntent.putExtra("employeeName",employeeName)
            startActivity(empAddServiceIntent)
        }
    }
}
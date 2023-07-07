package com.seyf.hairdresserappointment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.adapter.ServiceRecyclerAdapter
import com.seyf.hairdresserappointment.databinding.ActivityCustEmployeeDetailsBinding
import com.seyf.hairdresserappointment.model.Service

class CustEmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustEmployeeDetailsBinding
    private lateinit var custFirestore: FirebaseFirestore

    private lateinit var serviceArrayList: ArrayList<Service>
    private lateinit var serviceAdapter: ServiceRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustEmployeeDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        custFirestore = Firebase.firestore

        val custEmpDetailsIntent = intent
        val empNameDet = custEmpDetailsIntent.getStringExtra("empCardName") as String
        val empContDet = custEmpDetailsIntent.getStringExtra("empCardCont") as String
        val empWorkDet = custEmpDetailsIntent.getStringExtra("empCardWork")
        val hairDocId = custEmpDetailsIntent.getStringExtra("hairId")
        val empId = custEmpDetailsIntent.getStringExtra("empId")
        val btnAddVisibility = custEmpDetailsIntent.getBooleanExtra("btnAddVisible",true)
        val hairDresserName = custEmpDetailsIntent.getStringExtra("hairDresserName") as String
        val customerName = custEmpDetailsIntent.getStringExtra("customerName") as String
        val customerId = custEmpDetailsIntent.getStringExtra("customerId") as String
        val customerContact = custEmpDetailsIntent.getStringExtra("customerCont") as String
        val hairDresserWk = custEmpDetailsIntent.getStringExtra("hairDresserWk") as String
        val hairDresserAddress = custEmpDetailsIntent.getStringExtra("hairDresserAddress") as String

        val hairDresserDocRefId = hairDocId.toString()
        val employeeDocRefId = empId.toString()
        binding.empDetNameCust.text = empNameDet.toString()
        binding.empDetContCust.text = empContDet.toString()
        binding.empDetWorkCust.text = empWorkDet.toString()

        serviceArrayList = ArrayList<Service>()

        binding.employeeServicesRecycler.layoutManager = LinearLayoutManager(this)
        serviceAdapter = ServiceRecyclerAdapter(serviceArrayList,this@CustEmployeeDetailsActivity)
        binding.employeeServicesRecycler.adapter = serviceAdapter

        custClickedDetails(hairDresserDocRefId,employeeDocRefId,hairDresserName,
            customerName,customerId,empNameDet, empContDet, customerContact,hairDresserWk,hairDresserAddress)
    }

    fun custClickedDetails(hairDresserDocId: String, employeeDocId: String, hairDresserName:String, customerName:String,
                           customerId:String, employeeName:String, employeeContact:String, customerContact: String,hairDresserWk: String,hairDresserAddress: String) {

        val employeeServiceRef = custFirestore.collection("HairDresser")
            .document(hairDresserDocId).collection("Employees").document(employeeDocId).collection("Services")

        employeeServiceRef.get().addOnSuccessListener { querySnapshot->

            serviceArrayList.clear()

            for (document in querySnapshot) {

                val serviceName = document.get("employeeServiceName") as String
                val serviceTime = document.get("employeeServiceTime") as String
                val servicePrice = document.get("employeeServicePrice") as String
                val serviceId = document.id
                val hairDresserId = hairDresserDocId
                val employeeId = employeeDocId

                val service = Service(serviceName,serviceTime,serviceId,hairDresserId,employeeId,
                    hairDresserName,customerName,customerId,employeeName,employeeContact,customerContact,hairDresserWk,servicePrice,hairDresserAddress)
                serviceArrayList.add(service)

            }
            serviceAdapter.notifyDataSetChanged()
        }
    }
}
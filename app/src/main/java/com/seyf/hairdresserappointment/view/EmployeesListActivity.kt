package com.seyf.hairdresserappointment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.adapter.EmployeeRecyclerAdapter
import com.seyf.hairdresserappointment.databinding.ActivityEmployeesListBinding
import com.seyf.hairdresserappointment.model.Employee
import com.seyf.hairdresserappointment.model.EmployeeHd

class EmployeesListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeesListBinding
    private lateinit var hdAuth: FirebaseAuth
    private lateinit var hdFirestore: FirebaseFirestore

    private lateinit var employeeArrayList: ArrayList<EmployeeHd>
    private lateinit var employeeAdapter: EmployeeRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeesListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hdAuth = Firebase.auth
        hdFirestore = Firebase.firestore

        employeeArrayList = ArrayList<EmployeeHd>()

        binding.btnEmployeeAdd.setOnClickListener {
            val intent = Intent(this, EmployeeRegActivity::class.java)
            startActivity(intent)
            finish()
        }

        getEmployeeData()

        binding.employeesList.layoutManager = LinearLayoutManager(this)
        employeeAdapter = EmployeeRecyclerAdapter(employeeArrayList, this@EmployeesListActivity)
        binding.employeesList.adapter = employeeAdapter
    }
    // val currentHd = hdAuth.currentUser

    private fun getEmployeeData() {
        //   if (currentHd != null) {

        val hdId = hdAuth.currentUser

        //kuaförün ismini alma
        val docRef = hdFirestore.collection("HairDresser").document(hdId!!.uid)
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val hdName = document.get("hdName") as String


                //çalışan belgesine ulaşma
                hdFirestore.collection("HairDresser").document(hdId!!.uid).collection("Employees")
                    .addSnapshotListener { value, error ->
                        val hdDocId = hdId!!.uid
                        if (error != null) {
                            //  Toast.makeText(this, "Bir sorun var", Toast.LENGTH_LONG).show()
                        } else {

                            if (value != null) {
                                if (!value.isEmpty) {

                                    val documents = value.documents

                                    employeeArrayList.clear()

                                    for (document in documents) {

                                        val empName = document.get("employeeName") as String
                                        val empContact = document.get("employeeContact") as String
                                        val empWork = document.get("employeeWork") as String
                                        val empRating = document.get("averageRating") as String
                                        val empId = document.id


                                        val employeeHd =
                                            EmployeeHd(empName, empContact, empWork, hdDocId, empId, hdName,empRating)
                                        employeeArrayList.add(employeeHd)
                                    }
                                    employeeAdapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }
                //  }
            }
        }
    }
}
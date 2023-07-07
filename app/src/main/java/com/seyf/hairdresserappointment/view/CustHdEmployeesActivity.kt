package com.seyf.hairdresserappointment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.adapter.CustEmployeeRecyclerAdapter
import com.seyf.hairdresserappointment.adapter.EmployeeRecyclerAdapter
import com.seyf.hairdresserappointment.databinding.ActivityCustHdEmployeesBinding
import com.seyf.hairdresserappointment.model.Employee

class CustHdEmployeesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustHdEmployeesBinding
    private lateinit var custFirestore: FirebaseFirestore

    private lateinit var custEmployeeArrayList: ArrayList<Employee>
    private lateinit var custEmployeeAdapter: CustEmployeeRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustHdEmployeesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        custFirestore = Firebase.firestore

        custEmployeeArrayList = ArrayList<Employee>()

        val custHdEmpIntent = intent
        val hairDrId = custHdEmpIntent.getStringExtra("hairDrId") as String
        val hairDrName = custHdEmpIntent.getStringExtra("hairDrName") as String
        val custName = custHdEmpIntent.getStringExtra("custName") as String
        val custId = custHdEmpIntent.getStringExtra("custId") as String
        val custCont = custHdEmpIntent.getStringExtra("custCont") as String
        val hairDrWk = custHdEmpIntent.getStringExtra("hairDrWk") as String
        val hairDrAddress = custHdEmpIntent.getStringExtra("hairDrAddress") as String


        binding.custHdEmployeesList.layoutManager = LinearLayoutManager(this)
        custEmployeeAdapter =
            CustEmployeeRecyclerAdapter(custEmployeeArrayList, this@CustHdEmployeesActivity)
        binding.custHdEmployeesList.adapter = custEmployeeAdapter

        val employeesRef =
            custFirestore.collection("HairDresser").document(hairDrId).collection("Employees")

        employeesRef.whereEqualTo("workingStatus", "Çalışıyor").get().addOnSuccessListener { querySnapshot ->
            //başarılı olunca çalışacak kod
            println("Sorgu çalıştı")


            custEmployeeArrayList.clear()


                for (document in querySnapshot.documents) {
                    val documentCount = querySnapshot.documents.size
                    println("Döküman Sayısı: $documentCount")
                    println(querySnapshot.documents)
                    println("döngü ilk satırı")
                    val hdEmployeeName = document.get("employeeName") as String
                    val hdEmployeeContact = document.get("employeeContact") as String
                    val hdEmployeeWk = document.get("employeeWork") as String
                    val hdEmployeeId = document.id
                    val hairDresserId = hairDrId
                    val hdEmployeeRating = document.get("averageRating") as String

                    // hata alınan bölgede print denemesi
                    println("Employee Name: $hdEmployeeName")
                    println("Employee Contact: $hdEmployeeContact")
                    println("Employee Work: $hdEmployeeWk")

                    val hdEmployee = Employee(hdEmployeeName, hdEmployeeContact, hdEmployeeWk, hairDresserId,
                        hdEmployeeId, hairDrName, custName, custId,custCont,hairDrWk,hairDrAddress,hdEmployeeRating)
                    custEmployeeArrayList.add(hdEmployee)
                }


            println("veriyi kontrol etten önce")
            custEmployeeAdapter.notifyDataSetChanged()

        }.addOnFailureListener { exception ->
            //başarısızlık durumunda çalışacak kod
        }

    }
}
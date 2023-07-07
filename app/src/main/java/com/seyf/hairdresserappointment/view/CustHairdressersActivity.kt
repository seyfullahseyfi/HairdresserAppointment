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
import com.seyf.hairdresserappointment.adapter.HairdresserRecyclerAdapter
import com.seyf.hairdresserappointment.databinding.ActivityCustHairdressersBinding
import com.seyf.hairdresserappointment.model.Hairdresser

class CustHairdressersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustHairdressersBinding
    private lateinit var customerAuth: FirebaseAuth
    private lateinit var customerFirestore: FirebaseFirestore

    private lateinit var hairdresserArrayList: ArrayList<Hairdresser>
    private lateinit var hairdresserAdapter: HairdresserRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustHairdressersBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        customerAuth = Firebase.auth
        customerFirestore = Firebase.firestore

        hairdresserArrayList = ArrayList<Hairdresser>()


        getListHairdressers()

        binding.custHairdressers.layoutManager = LinearLayoutManager(this)
        hairdresserAdapter = HairdresserRecyclerAdapter(hairdresserArrayList,this@CustHairdressersActivity)
        binding.custHairdressers.adapter = hairdresserAdapter
    }

    private fun getListHairdressers() {
        val currentCust = customerAuth.currentUser

        val docRef = customerFirestore.collection("Customer").document(currentCust!!.uid)
        docRef.get().addOnSuccessListener { document ->
            if (document!= null) {
                val custCity = document.get("custCity") as String
                val custDistrict = document.get("custDistrict") as String
                val custName = document.get("custName") as String
                val custId = document.id
                val custCont = document.get("custPhone") as String

                val query = customerFirestore.collection("HairDresser")
                    .whereEqualTo("hdCity", custCity).whereEqualTo("hdDistrict", custDistrict)
                query.get()
                    .addOnSuccessListener { documents ->

                        // Sorgu başarılı olduğunda burası çalışacak
                        if (!documents.isEmpty){
                            hairdresserArrayList.clear()

                            for (document in documents) {
                                val hairdresserName = document.get("hdName") as String
                                val hairdresserType = document.get("hdType") as String
                                val hairdresserContact = document.get("hdPhone") as String
                                val hairdresserAddress = document.get("hdAddress") as String
                                val hairdresserWk = document.get("hdWk") as String
                                val hairdresserId = document.get("hdId") as String
                                val docId=document.id

                                val hairdresser = Hairdresser(hairdresserName, hairdresserType, hairdresserContact,
                                    hairdresserAddress, hairdresserWk,docId,custName,custId,custCont)
                                hairdresserArrayList.add(hairdresser)

                            }
                            hairdresserAdapter.notifyDataSetChanged()
                        } else {
                            Toast.makeText(this,"Adresinizde kayıtlı kuaför bulunamadı",Toast.LENGTH_LONG)
                                .show()
                        }

                    }
                    .addOnFailureListener { exception ->
                        // Sorgu başarısız olduğunda burası çalışacak
                    }
            }
        }
    }
}
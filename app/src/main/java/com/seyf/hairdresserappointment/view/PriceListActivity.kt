package com.seyf.hairdresserappointment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.adapter.EmployeeRecyclerAdapter
import com.seyf.hairdresserappointment.adapter.PriceRecyclerAdapter
import com.seyf.hairdresserappointment.databinding.ActivityHairdresserHomePageBinding
import com.seyf.hairdresserappointment.databinding.ActivityPriceListBinding
import com.seyf.hairdresserappointment.model.Employee
import com.seyf.hairdresserappointment.model.Price

class PriceListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPriceListBinding
    private lateinit var hdAuth: FirebaseAuth
    private lateinit var hdFirestore: FirebaseFirestore

    private lateinit var priceArrayList: ArrayList<Price>
    private lateinit var priceAdapter: PriceRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPriceListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hdAuth = Firebase.auth
        hdFirestore = Firebase.firestore

        priceArrayList = ArrayList<Price>()


        binding.btnPriceAdd.setOnClickListener {
            val intent = Intent(this, PriceRegActivity::class.java)
            startActivity(intent)
            finish()
        }

        getPriceData()

        binding.priceList.layoutManager = LinearLayoutManager(this)
        priceAdapter = PriceRecyclerAdapter(priceArrayList, this@PriceListActivity)
        binding.priceList.adapter = priceAdapter
    }
    //kuaför giriş yaptığında verileri alan fonksiyon
    private fun getPriceData() {

        val hdId = hdAuth.currentUser
        hdFirestore.collection("HairDresser").document(hdId!!.uid).collection("Prices")
            .addSnapshotListener { value, error ->

                if (error != null) {
                    //  Toast.makeText(this, "Bir sorun var", Toast.LENGTH_LONG).show()
                } else {

                    if (value != null) {
                        if (!value.isEmpty) {

                            val documents = value.documents

                            priceArrayList.clear()

                            for (document in documents) {

                                val srvName = document.get("serviceName") as String
                                val srvDuration = document.get("serviceDuration") as String
                                val srvPrice = document.get("servicePrice") as String

                                val price = Price(srvName, srvDuration, srvPrice)
                                priceArrayList.add(price)
                            }
                            priceAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

    }
}

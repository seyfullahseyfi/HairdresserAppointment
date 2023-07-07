package com.seyf.hairdresserappointment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.adapter.PriceRecyclerAdapter
import com.seyf.hairdresserappointment.databinding.ActivityCustHdPriceListBinding
import com.seyf.hairdresserappointment.model.Price

class CustHdPriceList : AppCompatActivity() {
    private lateinit var binding: ActivityCustHdPriceListBinding
    private lateinit var custFirestore: FirebaseFirestore

    private lateinit var priceArrayList: ArrayList<Price>
    private lateinit var priceAdapter: PriceRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustHdPriceListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        custFirestore = Firebase.firestore
        priceArrayList = ArrayList<Price>()

        val custHdPriceIntent = intent
        val hairDrId = custHdPriceIntent.getStringExtra("hairDrId") as String

        binding.custHdPriceList.layoutManager = LinearLayoutManager(this)
        priceAdapter = PriceRecyclerAdapter(priceArrayList, this@CustHdPriceList)
        binding.custHdPriceList.adapter = priceAdapter

        custFirestore.collection("HairDresser").document(hairDrId).collection("Prices")
            .addSnapshotListener { value, error ->

                if (error ==null) {
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
                } else {
                    //error null olmadığı durumda çalışacak kısım
                }

            }


    }
}
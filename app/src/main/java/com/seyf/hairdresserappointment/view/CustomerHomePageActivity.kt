package com.seyf.hairdresserappointment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.R
import com.seyf.hairdresserappointment.databinding.ActivityCustomerHomePageBinding

class CustomerHomePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerHomePageBinding
    private lateinit var custAuth: FirebaseAuth
    private lateinit var custFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerHomePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        custAuth = Firebase.auth
        custFirestore = Firebase.firestore


        val currentCust = custAuth.currentUser

        val docRef = custFirestore.collection("Customer").document(currentCust!!.uid)
        docRef.get().addOnSuccessListener { document ->
            if (document!= null) {
                val custName = document.get("custName") as String
                val custCity = document.get("custCity") as String
                val custDistrict = document.get("custDistrict") as String

                binding.textView3.text = "Hoş Geldin," + custName
                binding.etCustCity.setText(custCity)
                binding.etCustDistrict.setText(custDistrict)

            binding.btnChange.setOnClickListener {
                val newCityAndDistrict =hashMapOf<String, Any>(
                    "custCity" to binding.etCustCity.text.toString(),
                    "custDistrict" to binding.etCustDistrict.text.toString()
                )

                docRef.update(newCityAndDistrict)
                    .addOnSuccessListener {
                }.addOnFailureListener {

                }
              }
            }
        }

    }
    fun changeDataCst(view: View) {

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.cust_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.custLogOut) {
            custAuth.signOut()
            val intent = Intent(this, CustomerLogActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)

    }

     //kuaförlere tıklanınca gerçekleşecek fonksiyon
     fun hairdressersPage(view : View) {
         val intent = Intent(this,CustHairdressersActivity::class.java)
         startActivity(intent)
     }

     //profilime tıklanınca gerçekleşecek fonksiyon
     fun profileCustPage(view : View) {

         val intent = Intent(this,CustomerProfileActivity::class.java)
         startActivity(intent)
     }

     //randevlar tıklanınca gerçekleşecek fonksiyon
     fun custAppointmentPage(view : View) {

         val intent = Intent(this,CustomerAppointmentActivity::class.java)
         startActivity(intent)
     }

}

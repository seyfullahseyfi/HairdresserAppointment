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
import com.seyf.hairdresserappointment.databinding.ActivityHairdresserHomePageBinding

class HairdresserHomePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHairdresserHomePageBinding
    private lateinit var hdAuth: FirebaseAuth
    private lateinit var hdFirestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHairdresserHomePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        hdAuth = Firebase.auth
        hdFirestore = Firebase.firestore

        val currentHd = hdAuth.currentUser

        val docRef = hdFirestore.collection("HairDresser").document(currentHd!!.uid)
        docRef.get().addOnSuccessListener { document ->
            if (document!= null) {
                val hdName = document.get("hdName") as String

                binding.textView2.text = "Hoş Geldin," + hdName


            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.hd_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.hdLogOut) {
            hdAuth.signOut()
            val intent = Intent(this, HairdresserLogActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)

    }

    //çalışanlar kartına tıklandığında gerçekleşecek fonksiyon
    fun employeesPage(view: View) {
        val intent = Intent(this, EmployeesListActivity::class.java)
        startActivity(intent)
    }

    //fiyat listesi kartına tıklandığında gerçekleşecek fonksiyon
    fun pricePage(view: View) {
        val intent = Intent(this,PriceListActivity::class.java)
        startActivity(intent)
    }

    //Randevular kartına tıklandığında gerçekleşecek fonksiyon
    fun hdAppointmentPage(view: View) {

        val intent = Intent(this,HairDresserAppointmentActivity::class.java)
        startActivity(intent)

    }

    //Profil kartına tıklandığında gerçekleşecek fonksiyon
    fun profileHdPage(view: View) {
        val intent = Intent(this,HairdresserProfileActivity::class.java)
        startActivity(intent)
    }
}
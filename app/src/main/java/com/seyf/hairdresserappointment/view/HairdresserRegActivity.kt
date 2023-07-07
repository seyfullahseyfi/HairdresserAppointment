package com.seyf.hairdresserappointment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.databinding.ActivityHairdresserRegBinding

class HairdresserRegActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHairdresserRegBinding
    private lateinit var hdAuth: FirebaseAuth
    private lateinit var hdFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHairdresserRegBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hdAuth = Firebase.auth
        hdFirestore = Firebase.firestore

    }
   // var currentHd = hdAuth.currentUser
    fun hairdresserReg(view: View) {
        val hdEmail = binding.hdEmailText.text.toString()
        val hdPassword = binding.hdPassText.text.toString()
        val hdName = binding.hdNameText.text.toString()
        val hdType = binding.hdTypeText.text.toString()
        val hdAddress = binding.hdAddressText.text.toString()
        val hdWk = binding.hdWkText.text.toString()
        val hdCity = binding.hdCityText.text.toString()
        val hdDistrict = binding.hdDistrictText.text.toString()
        val hdPhone = binding.hdNumberText.text.toString()

        if (hdEmail.equals("") || hdPassword.equals("") || hdName.equals("") || hdType.equals("") ||
            hdAddress.equals("") || hdWk.equals("") || hdCity.equals("") || hdDistrict.equals("") || hdPhone.equals("")
        ) {
            Toast.makeText(this, "Bilgileri eksiksiz doldurun!", Toast.LENGTH_LONG).show()
        } else {
            hdAuth.createUserWithEmailAndPassword(hdEmail, hdPassword).addOnSuccessListener {
                //kullanıcı oluşturma başarılı
                //verileri firestore kaydetme

              //  if (currentHd != null) {
                    val currentHd= hdAuth.currentUser
                    val currentId = currentHd!!.uid
                    val hdDataMap = hashMapOf(
                        "hdName" to hdName,
                        "hdMail" to hdEmail,
                        "hdType" to hdType,
                        "hdAddress" to hdAddress,
                        "hdWk" to hdWk,
                        "hdCity" to hdCity,
                        "hdDistrict" to hdDistrict,
                        "hdPhone" to hdPhone,
                        "hdId" to currentId
                    )

                    hdFirestore.collection("HairDresser").document(currentHd!!.uid).set(hdDataMap)
                        .addOnSuccessListener {
                            //başarılı olursa
                            val intent = Intent(this@HairdresserRegActivity, HairdresserHomePageActivity::class.java)
                            startActivity(intent)
                            finish()
                        }.addOnFailureListener {
                        //başarısız olursa


                    }
               // }

            }.addOnFailureListener {
                Toast.makeText(this, "Kullanıcı oluşturulamadı", Toast.LENGTH_LONG).show()
            }
        }
    }

}
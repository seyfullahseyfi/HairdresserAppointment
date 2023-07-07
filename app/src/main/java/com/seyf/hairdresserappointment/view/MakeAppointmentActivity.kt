package com.seyf.hairdresserappointment.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seyf.hairdresserappointment.databinding.ActivityMakeAppointmentBinding
import com.seyf.hairdresserappointment.model.Service
import java.text.SimpleDateFormat
import java.util.*

class MakeAppointmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeAppointmentBinding
    private lateinit var custFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeAppointmentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        custFirestore = Firebase.firestore

        val serviceDataIntent = intent
        val serviceName = serviceDataIntent.getStringExtra("serviceName").toString()
        val serviceTime = serviceDataIntent.getStringExtra("serviceTime").toString()
        val serviceId = serviceDataIntent.getStringExtra("serviceId").toString()
        val hairDresserDocId = serviceDataIntent.getStringExtra("hairDresserDocId").toString()
        val employeeDocId = serviceDataIntent.getStringExtra("employeeDocId").toString()
        val hairDresserName = serviceDataIntent.getStringExtra("hairDresserName").toString()
        val customerName = serviceDataIntent.getStringExtra("customerName").toString()
        val customerId = serviceDataIntent.getStringExtra("customerId").toString()
        val employeeName = serviceDataIntent.getStringExtra("employeeName").toString()
        val employeeContact = serviceDataIntent.getStringExtra("employeeContact").toString()
        val customerContact = serviceDataIntent.getStringExtra("customerContact").toString()
        val hairDresserWk = serviceDataIntent.getStringExtra("hairDresserWk").toString()
        val servicePrice = serviceDataIntent.getStringExtra("servicePrice").toString()
        val hairDresserAddress = serviceDataIntent.getStringExtra("hairDresserAddress").toString()

        val appointmentStatus = "Aktif"


        binding.empNameTv.text = "Personel Adı: $employeeName"
        binding.hdNameTv.text = "Kuaförün Adı: $hairDresserName"
        binding.srvcTv.text = "Seçilen hizmet: $serviceName"
        binding.srvcTimeTv.text = "Tahmini Süresi: $serviceTime"
        binding.hdWkTv.text = "Kuaför Çalışma bilgisi: $hairDresserWk"
        binding.srvcPriceTv.text = "Ücreti: $servicePrice"
        //tarihi seçimi yaptırtıyoruz
        binding.btnDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                    // Seçilen tarih
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    binding.tvDate.text = selectedDate
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }
        //zaman seçimi
        binding.btnTime.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val hour = currentTime.get(Calendar.HOUR_OF_DAY)
            val minute = currentTime.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                    binding.tvTime.text = selectedTime
                },
                hour,
                minute,
                true
            )

            timePickerDialog.show()
        }

        binding.btnAppointment.setOnClickListener {
            val selectedDate = binding.tvDate.text.toString()
            val selectedTime = binding.tvTime.text.toString()
            val serviceTimeInMinutes = serviceTime.toInt()

            // Başlangıç zamanını saat ve dakika olarak ayrıştırma
            val startTimeParts = selectedTime.split(":")
            val startHour = startTimeParts[0].toInt()
            val startMinute = startTimeParts[1].toInt()

            // Başlangıç zamanını dakika cinsinden hesaplama
            val startMinutes = startHour * 60 + startMinute

            // Bitiş zamanını hesaplama
            val endMinutes = startMinutes + serviceTimeInMinutes

            // Bitiş zamanını saat ve dakika olarak ayrıştırma
            val endHour = endMinutes / 60
            val endMinute = endMinutes % 60

            // Bitiş zamanını formatlama
            val endTime = String.format("%02d:%02d", endHour, endMinute)


            // Randevu oluşturma işlemini gerçekleştir
            createAppointment(
                serviceName,
                serviceTime,
                serviceId,
                hairDresserDocId,
                employeeDocId,
                selectedDate,
                selectedTime,
                hairDresserName,
                customerName,
                customerId,
                employeeName,
                employeeContact,
                customerContact,
                endTime,
                servicePrice,
                hairDresserAddress,
                appointmentStatus
            )
        }

    }

    private fun createAppointment(
        serviceName: String, serviceTime: String, serviceId: String,
        hairDresserDocId: String, employeeDocId: String, selectedDate: String,
        selectedTime: String, hairDresserName: String, customerName: String, customerId: String,
        employeeName: String, employeeContact: String, customerContact: String, endTime: String,
        servicePrice: String, hairDresserAddress: String,appointmentStatus:String
    ) {


        val appointmentColRef = custFirestore.collection("Appointments")

        //seçilen tarih,saatte ve çalışanın başka randevusu var mı kontrol ediliyor
        val query = appointmentColRef
            .whereEqualTo("selectedDate", selectedDate)
            .whereEqualTo("selectedTime", selectedTime)
            .whereEqualTo("employeeDocId", employeeDocId)




           query.get().addOnSuccessListener { documents->
               if (documents.isEmpty) {
                   //çakışan randevu yok randevu oluştur.

                   val appointmentData = hashMapOf(
                       "serviceName" to serviceName,
                       "serviceTime" to serviceTime,
                       "serviceId" to serviceId,
                       "hairDresserDocId" to hairDresserDocId,
                       "employeeDocId" to employeeDocId,
                       "selectedDate" to selectedDate,
                       "selectedTime" to selectedTime,
                       "hairDresserName" to hairDresserName,
                       "customerName" to customerName,
                       "customerId" to customerId,
                       "employeeName" to employeeName,
                       "employeeContact" to employeeContact,
                       "customerContact" to customerContact,
                       "endTime" to endTime,
                       "servicePrice" to servicePrice,
                       "hairDresserAddress" to hairDresserAddress,
                       "appointmentStatus" to appointmentStatus
                   )

                   //randevu oluştur

                   appointmentColRef.add(appointmentData).addOnSuccessListener {documentReference->
                       //randevu oluşturuldu...
                       Toast.makeText(this, "Randevu oluşturuldu.", Toast.LENGTH_LONG).show()

                   }.addOnFailureListener {exception ->
                       // Randevu oluşturma sırasında bir hata oluştu
                       Toast.makeText(this, "Randevu oluşturulurken bir hata oluştu:", Toast.LENGTH_LONG).show()

                   }
               } else {
                   // Aynı tarih ve saat aralığında başka bir randevu var
                   Toast.makeText(this, "Seçilen tarih ve saat aralığında başka bir randevu bulunmaktadır. Lütfen başka bir saat seçiniz!", Toast.LENGTH_LONG).show()
               }

           }.addOnFailureListener {exception ->
               //sorguda hata var
               Toast.makeText(this, "Sorgu işlemi sırasında bir hata oluştu: ${exception.message}", Toast.LENGTH_LONG).show()
               val hata = "hata :${exception.message}"
               println(hata)

           }


    }

}


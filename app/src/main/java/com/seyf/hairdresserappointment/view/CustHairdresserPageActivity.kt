package com.seyf.hairdresserappointment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seyf.hairdresserappointment.databinding.ActivityCustHairdresserPageBinding

class CustHairdresserPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustHairdresserPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustHairdresserPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val custHdPageIntent = intent
        val hairDrName = custHdPageIntent.getStringExtra("custHdName").toString()
        val hairDrId = custHdPageIntent.getStringExtra("custHdId").toString()
        val custName = custHdPageIntent.getStringExtra("custName").toString()
        val custId = custHdPageIntent.getStringExtra("custId").toString()
        val custCont = custHdPageIntent.getStringExtra("custCont").toString()
        val hairDrWk = custHdPageIntent.getStringExtra("hdWk").toString()
        val hairDrAddress = custHdPageIntent.getStringExtra("hdAddress").toString()

        binding.textview27.text = "Kuaförün adı:$hairDrName"

        println(hairDrId)

        //personeller kartına tıklandığında gerçekleşecek işlemler
        binding.custGoEmployee.setOnClickListener {
            val custHdEmpIntent = Intent(this,CustHdEmployeesActivity::class.java)
            custHdEmpIntent.putExtra("hairDrId",hairDrId)
            custHdEmpIntent.putExtra("hairDrName",hairDrName)
            custHdEmpIntent.putExtra("custName",custName)
            custHdEmpIntent.putExtra("custId",custId)
            custHdEmpIntent.putExtra("custCont", custCont)
            custHdEmpIntent.putExtra("hairDrWk",hairDrWk)
            custHdEmpIntent.putExtra("hairDrAddress",hairDrAddress)

            startActivity(custHdEmpIntent)

        }

        //fiyat listesi kartına tıklandığında gerçekleşecek işlemler

        binding.custHdGoPrice.setOnClickListener {
            val custHdPriceIntent= Intent(this,CustHdPriceList::class.java)
            custHdPriceIntent.putExtra("hairDrId",hairDrId)

            startActivity(custHdPriceIntent)
        }


    }

}
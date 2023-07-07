package com.seyf.hairdresserappointment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.seyf.hairdresserappointment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun customerLogPage(view: View) {
        val intent = Intent(this, CustomerLogActivity::class.java)
        startActivity(intent)
    }

    fun hairdresserLogPage(view: View) {
        val intent = Intent(this, HairdresserLogActivity::class.java)
        startActivity(intent)
    }
}
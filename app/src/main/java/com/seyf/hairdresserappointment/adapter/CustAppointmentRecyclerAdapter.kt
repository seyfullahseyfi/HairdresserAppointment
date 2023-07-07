package com.seyf.hairdresserappointment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seyf.hairdresserappointment.databinding.CustAppointItemListBinding
import com.seyf.hairdresserappointment.model.CustAppointment
import com.seyf.hairdresserappointment.view.CustAppointmentTransactionsActivity

class CustAppointmentRecyclerAdapter(private val custAppointmentList: ArrayList<CustAppointment>, val context: Context) :
    RecyclerView.Adapter<CustAppointmentRecyclerAdapter.CustAppointmentHolder>() {

    class CustAppointmentHolder(val binding: CustAppointItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustAppointmentHolder {

        val binding =
            CustAppointItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustAppointmentHolder(binding)

    }

    override fun getItemCount(): Int {

        return custAppointmentList.size
    }

    override fun onBindViewHolder(holder: CustAppointmentHolder, position: Int) {

        holder.binding.textView43.text = custAppointmentList.get(position).hairDresserName
        holder.binding.textView44.text = custAppointmentList.get(position).employeeName
        holder.binding.textView45.text = custAppointmentList.get(position).serviceName
        holder.binding.textView47.text = custAppointmentList.get(position).appointmentTime
        holder.binding.textView49.text = custAppointmentList.get(position).appointmentStatus
        holder.binding.textView53.text = custAppointmentList.get(position).hairDresserAddress
        holder.binding.textView55.text = custAppointmentList.get(position).employeeContact
        holder.binding.textView51.text = custAppointmentList.get(position).servicePrice
        holder.binding.textView57.text = custAppointmentList.get(position).serviceTime

        //karta tıklandığında
        holder.itemView.setOnClickListener {
            val appointment = custAppointmentList[position]
            val appointmentId = appointment.appointmentDocId
            val appointmentStatus = appointment.appointmentStatus
            val hairDresserId = appointment.hairDresserId
            val employeeId = appointment.employeeId

            val custAppointmentTransactionsIntent =
                Intent(context, CustAppointmentTransactionsActivity::class.java)
            custAppointmentTransactionsIntent.putExtra("appointmentId", appointmentId)
            custAppointmentTransactionsIntent.putExtra("appointmentStatus", appointmentStatus)
            custAppointmentTransactionsIntent.putExtra("hairDresserId", hairDresserId)
            custAppointmentTransactionsIntent.putExtra("employeeId", employeeId)
            context.startActivity(custAppointmentTransactionsIntent)
        }

    }
}
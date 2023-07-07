package com.seyf.hairdresserappointment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seyf.hairdresserappointment.databinding.HdAppointItemListBinding
import com.seyf.hairdresserappointment.model.HdAppointment
import com.seyf.hairdresserappointment.view.HdAppointmentTransactionsActivity

class HdAppointmentRecyclerAdapter(
    private val hdAppointmentList: ArrayList<HdAppointment>,
    val context: Context
) :
    RecyclerView.Adapter<HdAppointmentRecyclerAdapter.HdAppointmentHolder>() {

    class HdAppointmentHolder(val binding: HdAppointItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HdAppointmentHolder {
        val binding =
            HdAppointItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HdAppointmentHolder(binding)
    }

    override fun getItemCount(): Int {
        return hdAppointmentList.size
    }

    override fun onBindViewHolder(holder: HdAppointmentHolder, position: Int) {

        holder.binding.hdAppCustName.text = hdAppointmentList.get(position).custName
        holder.binding.hdAppEmpName.text = hdAppointmentList.get(position).employeeName
        holder.binding.hdAppDate.text = hdAppointmentList.get(position).appointmentDate
        holder.binding.hdAppContact.text = hdAppointmentList.get(position).custContact
        holder.binding.hdAppStatus.text = hdAppointmentList.get(position).appointmentStatus
        holder.binding.hdAppServiceName.text = hdAppointmentList.get(position).serviceName
        holder.binding.hdAppTime.text = hdAppointmentList.get(position).serviceTime
        holder.binding.hdAppPrice.text = hdAppointmentList.get(position).servicePrice

        //karta tıklandığında
        holder.itemView.setOnClickListener {
            val appointment = hdAppointmentList[position]
            val appointmentId = appointment.appointmentDocId
            val appointmentStatus = appointment.appointmentStatus

            val hdAppointmentTransactionsIntent =
                Intent(context, HdAppointmentTransactionsActivity::class.java)
            hdAppointmentTransactionsIntent.putExtra("appointmentId", appointmentId)
            hdAppointmentTransactionsIntent.putExtra("appointmentStatus", appointmentStatus)
            context.startActivity(hdAppointmentTransactionsIntent)
        }

    }
}
package com.seyf.hairdresserappointment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seyf.hairdresserappointment.databinding.EmployeeServicesItemListBinding

import com.seyf.hairdresserappointment.model.ServiceHd

class ServiceHdRecyclerAdapter(private val serviceList: ArrayList<ServiceHd>, val context: Context) :
    RecyclerView.Adapter<ServiceHdRecyclerAdapter.ServiceHdHolder>() {
    class ServiceHdHolder(val binding: EmployeeServicesItemListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceHdHolder {
        val binding = EmployeeServicesItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ServiceHdHolder(binding)
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    override fun onBindViewHolder(holder: ServiceHdHolder, position: Int) {
        holder.binding.serviceNameTv.text = serviceList.get(position).serviceName
        holder.binding.serviceTimeTv.text = serviceList.get(position).serviceTime
        holder.binding.servicePriceTv.text = serviceList.get(position).servicePrice
    }
}
package com.seyf.hairdresserappointment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seyf.hairdresserappointment.databinding.EmployeeServicesItemListBinding
import com.seyf.hairdresserappointment.model.Service
import com.seyf.hairdresserappointment.view.MakeAppointmentActivity

class ServiceRecyclerAdapter(private val serviceList: ArrayList<Service>,val context: Context) :
RecyclerView.Adapter<ServiceRecyclerAdapter.ServiceHolder>(){
    class ServiceHolder(val binding: EmployeeServicesItemListBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceHolder {
        val binding = EmployeeServicesItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ServiceHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceHolder, position: Int) {
        holder.binding.serviceNameTv.text = serviceList.get(position).serviceName
        holder.binding.serviceTimeTv.text = serviceList.get(position).serviceTime
        holder.binding.servicePriceTv.text = serviceList.get(position).servicePrice

        holder.itemView.setOnClickListener {
            //seçilen pozisyonu alma
            val service = serviceList.get(position)
            val serviceName = service.serviceName
            val serviceTime = service.serviceTime
            val serviceId = service.serviceId
            val hairDresserDocId = service.hairDresserId
            val employeeDocId = service.employeeId
            val hairDresserName = service.hairDresserName
            val customerName = service.customerName
            val customerId = service.customerId
            val employeeName = service.employeeName
            val employeeContact = service.employeeContact
            val customerContact = service.customerContact
            val hairDresserWk = service.hairDresserWk
            val servicePrice = service.servicePrice
            val hairDresserAddress = service.hairDresserAddress

            val serviceDataIntent = Intent(context,MakeAppointmentActivity::class.java)
            serviceDataIntent.putExtra("serviceName",serviceName)
            serviceDataIntent.putExtra("serviceTime",serviceTime)
            serviceDataIntent.putExtra("serviceId",serviceId)
            serviceDataIntent.putExtra("hairDresserDocId",hairDresserDocId)
            serviceDataIntent.putExtra("employeeDocId",employeeDocId)
            serviceDataIntent.putExtra("hairDresserName",hairDresserName)
            serviceDataIntent.putExtra("customerName",customerName)
            serviceDataIntent.putExtra("customerId",customerId)
            serviceDataIntent.putExtra("employeeName",employeeName)
            serviceDataIntent.putExtra("employeeContact",employeeContact)
            serviceDataIntent.putExtra("customerContact",customerContact)
            serviceDataIntent.putExtra("hairDresserWk",hairDresserWk)
            serviceDataIntent.putExtra("servicePrice",servicePrice)
            serviceDataIntent.putExtra("hairDresserAddress",hairDresserAddress)

            context.startActivity(serviceDataIntent)
        }


    }

    override fun getItemCount(): Int {
       return serviceList.size
    }

}

package com.seyf.hairdresserappointment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seyf.hairdresserappointment.databinding.EmployeesItemListBinding
import com.seyf.hairdresserappointment.model.Employee
import com.seyf.hairdresserappointment.model.EmployeeHd
import com.seyf.hairdresserappointment.view.EmployeeDetailsActivity

class EmployeeRecyclerAdapter(private val employeeList: ArrayList<EmployeeHd>, val context: Context) :
    RecyclerView.Adapter<EmployeeRecyclerAdapter.EmployeeHolder>() {

    class EmployeeHolder(val binding: EmployeesItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeHolder {
        val binding =
            EmployeesItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeHolder, position: Int) {
        holder.binding.tvEmployeeName.text = employeeList.get(position).employeeName
        holder.binding.tvEmployeeContact.text = employeeList.get(position).employeeContact
        holder.binding.tvEmployeeWork.text = employeeList.get(position).employeeWork
        holder.binding.tvEmployeeRating.text = employeeList.get(position).rating


        holder.itemView.setOnClickListener {
            //seçili pozisyon alma
            var emp = employeeList[position]
            var empName = emp.employeeName
            var empCont = emp.employeeContact
            var empWork = emp.employeeWork
            val empId = emp.employeeId
            val hairId = emp.hairDresserId
            val hairDresserName = emp.hairDresserName
        //    val customerName = emp.customerName
        //    val customerId = emp.customerId

            val hdEmpDetailsIntent = Intent(context, EmployeeDetailsActivity::class.java)
            hdEmpDetailsIntent.putExtra("empCardName", empName)
            hdEmpDetailsIntent.putExtra("empCardCont", empCont)
            hdEmpDetailsIntent.putExtra("empCardWork", empWork)
            hdEmpDetailsIntent.putExtra("btnVisible",false)
            hdEmpDetailsIntent.putExtra("empId",empId)
            hdEmpDetailsIntent.putExtra("hairId",hairId)
            hdEmpDetailsIntent.putExtra("hairDresserName",hairDresserName)
        /*    hdEmpDetailsIntent.putExtra("customerName",customerName)
            hdEmpDetailsIntent.putExtra("customerId",customerId)*/

            context.startActivity(hdEmpDetailsIntent)
        }

    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

}
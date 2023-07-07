package com.seyf.hairdresserappointment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seyf.hairdresserappointment.databinding.EmployeesItemListBinding
import com.seyf.hairdresserappointment.model.Employee
import com.seyf.hairdresserappointment.view.CustEmployeeDetailsActivity

class CustEmployeeRecyclerAdapter(private val custEmployeeList: ArrayList<Employee>, val context: Context) :
    RecyclerView.Adapter<CustEmployeeRecyclerAdapter.CustEmployeeHolder>() {

    class CustEmployeeHolder(val binding: EmployeesItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustEmployeeHolder {

        val binding =
            EmployeesItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustEmployeeHolder(binding)

    }

    override fun onBindViewHolder(holder: CustEmployeeHolder, position: Int) {

        holder.binding.tvEmployeeName.text = custEmployeeList.get(position).employeeName
        holder.binding.tvEmployeeContact.text = custEmployeeList.get(position).employeeContact
        holder.binding.tvEmployeeWork.text = custEmployeeList.get(position).employeeWork
        holder.binding.tvEmployeeRating.text = custEmployeeList.get(position).rating

        holder.itemView.setOnClickListener {
            //seçili pozisyon alma
            var emp = custEmployeeList[position]
            var empName = emp.employeeName
            var empCont = emp.employeeContact
            var empWork = emp.employeeWork
            val empId = emp.employeeId
            val hairId = emp.hairDresserId
            val hairDresserName = emp.hairDresserName
            val customerName = emp.customerName
            val customerId = emp.customerId
            val customerCont = emp.customerContact
            val hairDresserWk = emp.hairDresserWk
            val hairDresserAddress = emp.hairDresserAddress

            val custEmpDetailsIntent = Intent(context, CustEmployeeDetailsActivity::class.java)
            custEmpDetailsIntent.putExtra("empCardName", empName)
            custEmpDetailsIntent.putExtra("empCardCont", empCont)
            custEmpDetailsIntent.putExtra("empCardWork", empWork)
            custEmpDetailsIntent.putExtra("empId",empId)
            custEmpDetailsIntent.putExtra("hairId",hairId)
            custEmpDetailsIntent.putExtra("hairDresserName",hairDresserName)
            custEmpDetailsIntent.putExtra("customerName",customerName)
            custEmpDetailsIntent.putExtra("customerId",customerId)
            custEmpDetailsIntent.putExtra("customerCont",customerCont)
            custEmpDetailsIntent.putExtra("btnAddVisible",false)
            custEmpDetailsIntent.putExtra("hairDresserWk",hairDresserWk)
            custEmpDetailsIntent.putExtra("hairDresserAddress",hairDresserAddress)

            context.startActivity(custEmpDetailsIntent)
        }
    }

    override fun getItemCount(): Int {
        return custEmployeeList.size
    }

}
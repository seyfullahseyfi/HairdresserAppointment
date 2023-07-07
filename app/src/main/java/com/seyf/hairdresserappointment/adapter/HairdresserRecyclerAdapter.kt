package com.seyf.hairdresserappointment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seyf.hairdresserappointment.databinding.HairdressersItemListBinding
import com.seyf.hairdresserappointment.model.Hairdresser
import com.seyf.hairdresserappointment.view.CustHairdresserPageActivity

class HairdresserRecyclerAdapter(private val hairdresserList: ArrayList<Hairdresser>, val context: Context) :
        RecyclerView.Adapter<HairdresserRecyclerAdapter.HairdresserHolder>() {

        class HairdresserHolder(val binding: HairdressersItemListBinding) :
            RecyclerView.ViewHolder(binding.root) {

            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HairdresserHolder {
        val binding = HairdressersItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HairdresserHolder(binding)
    }

    override fun onBindViewHolder(holder: HairdresserHolder, position: Int) {
        holder.binding.tvHairdresserName.text = hairdresserList.get(position).hairdresserName
        holder.binding.tvHairdresserType.text = hairdresserList.get(position).hairdresserType
        holder.binding.tvHairdresserContact.text = hairdresserList.get(position).hairdresserContact
        holder.binding.tvHairdresserAddress.text = hairdresserList.get(position).hairdresserAddress
        holder.binding.tvHairdresserWk.text = hairdresserList.get(position).hairdresserWk


        //seçili pozisyonu alma
        holder.itemView.setOnClickListener{
            val hairDr = hairdresserList[position]
            val hairDrId = hairDr.hairdresserId
            val hairDrName = hairDr.hairdresserName
            val custName = hairDr.customerName
            val custId = hairDr.customerId
            val custCont = hairDr.customerContact
            val hdWk = hairDr.hairdresserWk
            val hairDrAddress = hairDr.hairdresserAddress

            val custHdPageIntent = Intent(context,CustHairdresserPageActivity::class.java)
            custHdPageIntent.putExtra("custHdName", hairDrName)
            custHdPageIntent.putExtra("custHdId",hairDrId)
            custHdPageIntent.putExtra("custName",custName)
            custHdPageIntent.putExtra("custId",custId)
            custHdPageIntent.putExtra("custCont",custCont)
            custHdPageIntent.putExtra("hdWk",hdWk)
            custHdPageIntent.putExtra("hdAddress",hairDrAddress)

            context.startActivity(custHdPageIntent)
        }

    }

    override fun getItemCount(): Int {
        return hairdresserList.size
    }

}
package com.seyf.hairdresserappointment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seyf.hairdresserappointment.databinding.PriceItemListBinding
import com.seyf.hairdresserappointment.model.Price

class PriceRecyclerAdapter(private val priceList: ArrayList<Price>, val context: Context) :
    RecyclerView.Adapter<PriceRecyclerAdapter.PriceHolder>() {

    class PriceHolder(val binding: PriceItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceHolder {
        val binding =
            PriceItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PriceHolder(binding)
    }

    override fun onBindViewHolder(holder: PriceHolder, position: Int) {
        holder.binding.serviceTv.text = priceList.get(position).serviceName
        holder.binding.durationTv.text = priceList.get(position).serviceDuration
        holder.binding.priceTv.text = priceList.get(position).servicePrice

    }

    override fun getItemCount(): Int {
        return priceList.size
    }

}
package com.malhotra.urwork.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.malhotra.urwork.ModelClass.ServicesData
import com.malhotra.urwork.R
import com.malhotra.urwork.databinding.ServicesCardBinding

class ServicesAdapter(private val context: Context, private var servicesList : ArrayList<ServicesData>)
    : RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        return ServicesViewHolder(LayoutInflater
            .from(context)
            .inflate(R.layout.services_card, parent, false))
    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        val servicesList = servicesList[position]

        holder.binding.menuImage.setImageResource(servicesList.image)
        holder.binding.menuTitle.text = servicesList.title
    }

    override fun getItemCount(): Int {
        return servicesList.size
    }


    class ServicesViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
            val binding =  ServicesCardBinding.bind(view)
    }



}
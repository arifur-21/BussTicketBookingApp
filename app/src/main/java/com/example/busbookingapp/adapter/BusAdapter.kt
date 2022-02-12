package com.example.busbookingapp.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.busbookingapp.R
import com.example.busbookingapp.model.BookingDetail
import com.example.busbookingapp.model.BusModel

class BusAdapter (private val context: Context, private val busList: ArrayList<BusModel>): RecyclerView.Adapter<BusAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.buses_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val list = busList[position]

        holder.busName.text = list.travelsName
        holder.busNumber.text =("Bus Number :"+ list.busNumber)
        holder.condition.text = ("Bus Condition :"+ list.busCondition)
        holder.date.text = ("Journey Date :"+ list.date)
        holder.from.text = ("From :"+ list.from)
        holder.to.text =("To :"+ list.to)

        holder.itemView.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("name", list.travelsName)
            bundle.putString("date", list.date)
            bundle.putString("condition", list.busCondition)
            bundle.putString("busNumber", list.busNumber)
            bundle.putString("from_bus", list.from)
            bundle.putString("to_bus", list.to)


            Navigation.findNavController(it).navigate(R.id.action_busFragment_to_seatFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return busList.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val busName = itemView.findViewById<TextView>(R.id.text_view_busName)
        val busNumber = itemView.findViewById<TextView>(R.id.text_view_busNumber)
        val date = itemView.findViewById<TextView>(R.id.text_view_date)
        val from = itemView.findViewById<TextView>(R.id.text_view_from)
        val to = itemView.findViewById<TextView>(R.id.text_view_to)
        val condition = itemView.findViewById<TextView>(R.id.text_view_condition)

    }
}
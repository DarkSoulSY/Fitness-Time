package com.example.fitnesstime.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesstime.R
import com.example.fitnesstime.ui.model.Diary

class MyRecyclerViewAdapter(private val history: List<Diary>): RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.progress_list_item, parent,
            false)
        return MyViewHolder(itemView)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val currentItem = history[position]
        if (!currentItem.currentWeight.isNullOrBlank() && !currentItem.date.isNullOrBlank()){
            holder.date.text = currentItem.date
            holder.weight.text = currentItem.currentWeight + " Kg"
        }

    }

    override fun getItemCount(): Int {
        return history.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val weight: TextView = itemView.findViewById(R.id.weight)
        val date: TextView = itemView.findViewById(R.id.date)

    }
}
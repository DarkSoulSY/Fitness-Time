package com.example.fitnesstime.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesstime.R
import com.example.fitnesstime.data.DailyWeight

class MyRecyclerViewAdapter(private val checkIns: ArrayList<DailyWeight>): RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.progress_list_item, parent,
            false)
        return MyViewHolder(itemView)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = checkIns[position]
        holder.imageTitle.setImageResource(currentItem.imageTitle)
        holder.date.text = currentItem.date
        holder.weight.text = currentItem.weight

    }

    override fun getItemCount(): Int {
        return checkIns.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val imageTitle: ImageView = itemView.findViewById(R.id.image_title)
        val weight: TextView = itemView.findViewById(R.id.weight)
        val date: TextView = itemView.findViewById(R.id.date)



    }
}
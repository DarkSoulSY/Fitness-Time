package com.example.fitnesstime.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesstime.R
import com.example.fitnesstime.ui.model.AllMealsInformationAndQuantity

class BreakFastAdapter(private val item: List<AllMealsInformationAndQuantity>): RecyclerView.Adapter<BreakFastAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item_list, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = item[position]
        holder.productName.text = currentItem.product_name
        holder.calories.text = currentItem.calories

    }

    override fun getItemCount(): Int {
        return item.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val productName: TextView = itemView.findViewById(R.id.diary_product_name)
        val calories: TextView = itemView.findViewById(R.id.diary_product_calories1)
    }
}
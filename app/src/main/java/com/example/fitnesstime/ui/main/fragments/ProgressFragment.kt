package com.example.fitnesstime.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesstime.R
import com.example.fitnesstime.adapters.MyRecyclerViewAdapter
import com.example.fitnesstime.data.DailyWeight
import com.example.fitnesstime.databinding.FragmentProgressBinding
import com.example.fitnesstime.databinding.FragmentSettingsBinding
import kotlinx.android.synthetic.main.fragment_progress.*


class ProgressFragment : Fragment() {

    private lateinit var binding: FragmentProgressBinding

    private  val newArrayList = mutableListOf<DailyWeight>()
    private val imageId = mutableListOf<Int>()
    val weight = mutableListOf<String>()
    val date = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {binding = FragmentProgressBinding.inflate(inflater, container, false)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageId.addAll(listOf(
            R.drawable.nig,
            R.drawable.nig,
            R.drawable.nig
        ))
        weight.addAll(listOf(
            "90",
            "70",
            "80"

        ))
        date.addAll(listOf(
            "1992-01-20",
            "2005-01-20",
            "2010-01-20"

        ))

        binding.progressRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.progressRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf<DailyWeight>()
        getUserdata()
    }

    private fun getUserdata(){
        for (i in imageId.indices){
            val dailyWeight = DailyWeight(imageId[i],weight[i],date[i])
            newArrayList.add(dailyWeight)
        }
        binding.progressRecyclerView.adapter = MyRecyclerViewAdapter(newArrayList)

    }
}
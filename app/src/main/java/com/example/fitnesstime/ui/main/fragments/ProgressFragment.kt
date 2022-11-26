package com.example.fitnesstime.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesstime.R
import com.example.fitnesstime.adapters.MyRecyclerViewAdapter
import com.example.fitnesstime.data.DailyWeight
import com.example.fitnesstime.databinding.FragmentProgressBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class ProgressFragment : Fragment() {

    private lateinit var binding: FragmentProgressBinding
    private var newArrayList = mutableListOf<DailyWeight>()
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
        newArrayList = mutableListOf()
        getUserdata()
        setUpLineChart()

    }

    private fun getUserdata(){
        for (i in imageId.indices){
            val dailyWeight = DailyWeight(imageId[i],weight[i],date[i])
            newArrayList.add(dailyWeight)
        }
        binding.progressRecyclerView.adapter = MyRecyclerViewAdapter(newArrayList)

    }

    private fun setUpLineChart() {
        var entries: ArrayList<Entry> = ArrayList<Entry>()
        entries.add(Entry(1f,80f))
        entries.add(Entry(2f,90f))
        entries.add(Entry(3f,100f))

        var dataSet: LineDataSet = LineDataSet(entries, "Weight Entries")
        var dataSets: ArrayList<ILineDataSet> =  ArrayList<ILineDataSet>()
        dataSets.add(dataSet)

        var data: LineData = LineData(dataSets)
        binding.weightChart.data = data
        binding.weightChart.invalidate()

    }




}
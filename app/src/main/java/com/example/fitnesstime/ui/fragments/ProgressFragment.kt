package com.example.fitnesstime.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesstime.R
import com.example.fitnesstime.adapters.MyRecyclerViewAdapter
import com.example.fitnesstime.data.DailyWeight
import com.example.fitnesstime.databinding.FragmentProgressBinding
import com.example.fitnesstime.ui.model.Diary
import com.example.fitnesstime.ui.viewmodel.ProgressViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProgressFragment : Fragment() {

    private lateinit var binding: FragmentProgressBinding
    private var newArrayList = mutableListOf<DailyWeight>()
    private val imageId = mutableListOf<Int>()
    val weight = mutableListOf<String>()
    private val date = mutableListOf<String>()
    private lateinit var sharedPreferences: SharedPreferences
    private val personalViewModel: ProgressViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProgressBinding.inflate(inflater, container, false)
        binding.progressRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.progressRecyclerView.setHasFixedSize(true)
        personalViewModel.diaryRecords.observe(requireActivity(), Observer {
            if(!it.isNullOrEmpty()){
                binding.progressRecyclerView.adapter = MyRecyclerViewAdapter(it)
            }
        })

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onStart() {

        sharedPreferences = requireActivity().getSharedPreferences("User Session", MODE_PRIVATE)
        val email = sharedPreferences.getString("Email", null)
        if (!email.isNullOrBlank())
            personalViewModel.setDiaryRecords(requireContext(),email)



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

        newArrayList = mutableListOf()
        getUserdata()
        setUpLineChart()

        super.onStart()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isVisible = true

        super.onViewCreated(view, savedInstanceState)
    }

    private fun getUserdata(){
        for (i in imageId.indices){
            val dailyWeight = DailyWeight(imageId[i],weight[i],date[i])
            newArrayList.add(dailyWeight)
        }
    }

    private fun setUpLineChart() {
        var entries: ArrayList<Entry> = ArrayList()
        entries.add(Entry(1f,80f))
        entries.add(Entry(2f,90f))
        entries.add(Entry(3f,100f))


        var dataSet: LineDataSet = LineDataSet(entries, "Weight Entries")
        var dataSets: ArrayList<ILineDataSet> =  ArrayList()
        dataSets.add(dataSet)

        var data: LineData = LineData(dataSets)
        binding.weightChart.data = data
        binding.weightChart.invalidate()

    }




}
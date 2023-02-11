package com.example.fitnesstime.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesstime.R
import com.example.fitnesstime.adapters.MyRecyclerViewAdapter
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.databinding.FragmentProgressBinding
import com.example.fitnesstime.ui.model.AddDailyWeight
import com.example.fitnesstime.ui.model.Diary
import com.example.fitnesstime.ui.repositories.DiaryRepository
import com.example.fitnesstime.ui.viewmodel.ProgressViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ProgressFragment : Fragment() {


    private lateinit var binding: FragmentProgressBinding
    private var newArrayList = mutableListOf<Diary>()
    private val imageId = mutableListOf<Int>()
    val weight = mutableListOf<String>()
    private val date = mutableListOf<String>()
    private lateinit var sharedPreferences: SharedPreferences
    private val personalViewModel: ProgressViewModel by activityViewModels()
    private lateinit var weightEdit: EditText
    private lateinit var add: Button
    private lateinit var cancel: Button
    private val api = RetrofitInstance.retrofit
    private val diaryRepo: DiaryRepository = DiaryRepository(api = api)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProgressBinding.inflate(inflater, container, false)
        binding.progressRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.progressRecyclerView.setHasFixedSize(true)
        personalViewModel.diaryRecords.observe(requireActivity(), Observer {
            if (!it.isNullOrEmpty()) {
                binding.progressRecyclerView.adapter = MyRecyclerViewAdapter(it)
                getUserdata()
                setUpLineChart()
            }
        })

        return binding.root
    }


    override fun onStart() {

        sharedPreferences = requireActivity().getSharedPreferences("User Session", MODE_PRIVATE)
        val email = sharedPreferences.getString("Email", null)
        if (!email.isNullOrBlank())
            try {
                personalViewModel.setDiaryRecords(requireContext(), email)
            } catch (e: Exception) {

            }


        newArrayList = mutableListOf()
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isVisible = true

        super.onViewCreated(view, savedInstanceState)
    }

    private fun getUserdata() {

        for (i in personalViewModel.diaryRecords.value!!)
            if (!i.date.isNullOrEmpty() && !i.currentWeight.isNullOrEmpty()) {
                var diary = Diary(i.currentWeight, i.date)
                newArrayList.add(diary)
            }
    }

    private fun setUpLineChart() {
        var date: MutableList<String> = mutableListOf()
        var weight: MutableList<String> = mutableListOf()
        for (i in personalViewModel.diaryRecords.value!!)
            if (!i.currentWeight.isNullOrBlank()) {
                date.add(i.date)
                weight.add(i.currentWeight.toString())
            }

        var entries: ArrayList<Entry> = ArrayList()

        for (i in 0 until newArrayList.size){
            entries.add(Entry(i.toFloat(), newArrayList[i].currentWeight!!.toFloat()))

        }




        var dataSet: LineDataSet = LineDataSet(entries, "Weight Entries")
        var dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(dataSet)

        var data: LineData = LineData(dataSets)

        binding.weightChart.xAxis.valueFormatter = IndexAxisValueFormatter(date)
        //binding.weightChart.axisLeft.valueFormatter = IndexAxisValueFormatter(weight)

        binding.weightChart.apply {
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            setDrawGridBackground(true)
            extraLeftOffset = 15F
            extraRightOffset = 15F
            xAxis.setDrawGridLines(false)
            axisLeft.setDrawGridLines(false)
            axisRight.setDrawGridLines(false)
            axisRight.isEnabled = false
            xAxis.granularity = 1f;
            xAxis.setCenterAxisLabels(true);
            xAxis.isEnabled = true;
            xAxis.setDrawGridLines(false);
            xAxis.position = XAxis.XAxisPosition.BOTTOM;
            dataSet.setDrawValues(false)
            xAxis.setCenterAxisLabels(false)



        }

        binding.weightChart.data = data
        binding.weightChart.invalidate()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        binding.apply {
            addDailyWeight.setOnClickListener {
                buildDialog()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val contactPopupView: View = layoutInflater.inflate(R.layout.progress_add_weight, null)
        weightEdit = contactPopupView.findViewById(R.id.progress_add_current_weight)
        add = contactPopupView.findViewById(R.id.progress_add_weight)
        cancel = contactPopupView.findViewById(R.id.progress_cancle_operation)

        dialogBuilder.setView(contactPopupView)
        dialogBuilder.create()
            .setTitle("Add your current weight.")
        val alertDialog = dialogBuilder.show()

        add.setOnClickListener {
            val email = sharedPreferences.getString("Email", null)

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            if (weightEdit.text.toString().toFloat() > 0 && weightEdit.text.toString().toFloat() < 300) {
                if (!email.isNullOrBlank() && !weightEdit.text.toString().isNullOrBlank())
                    try {
                        GlobalScope.launch(Dispatchers.IO) {
                            val response = diaryRepo.addDailyWeight(
                                AddDailyWeight(
                                    email = email,
                                    date = LocalDateTime.now().format(formatter).toString(),
                                    current_weight = weightEdit.text.toString().toFloat()
                                )
                            )
                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    response.body()!!.apply {
                                        if (Success)
                                            Toast.makeText(
                                                requireContext(),
                                                Message,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        personalViewModel.setDiaryRecords(requireContext(), email)
                                    }

                                }
                            }
                        }
                    } catch (e: Exception) {

                    }
            }
            else
                Toast.makeText(requireContext(), "Please enter a weight between the range: 0 to 300 KG", Toast.LENGTH_SHORT).show()

            alertDialog?.dismiss()
        }

        cancel.setOnClickListener {
            alertDialog?.dismiss()
        }

    }


}
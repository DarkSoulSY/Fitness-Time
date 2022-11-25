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

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<DailyWeight>
    lateinit var imageId: Array<Int>
    lateinit var weight: Array<String>
    lateinit var date: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {_binding = FragmentProgressBinding.inflate(inflater, container, false)
        val view = binding.root

        imageId = arrayOf(
            R.drawable.nig,
            R.drawable.nig,
            R.drawable.nig
        )
        weight = arrayOf(
            "90",
            "70",
            "80"

        )
        date = arrayOf(
            "1992-01-20",
            "2005-01-20",
            "2010-01-20"

        )

        newRecyclerView = progress_recycler_view
        newRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf<DailyWeight>()
        getUserdata()
        return view
    }

    private fun getUserdata(){
        for (i in imageId.indices){
            val dailyWeight = DailyWeight(imageId[i],weight[i],date[i])
            newArrayList.add(dailyWeight)
        }
        newRecyclerView.adapter = MyRecyclerViewAdapter(newArrayList)

    }
}
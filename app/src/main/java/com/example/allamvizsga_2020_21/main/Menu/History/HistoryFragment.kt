package com.example.allamvizsga_2020_21.main.Menu.History

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allamvizsga_2020_21.Firebase.Data.HistoryData
import com.example.allamvizsga_2020_21.R
import java.time.LocalDateTime

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onResume() {
        super.onResume()

        val dataSet = arrayListOf<HistoryData>()
        dataSet.add(HistoryData(LocalDateTime.now(), "Camera Name", 5))
        dataSet.add(HistoryData(LocalDateTime.now(), "Camera Name", 5))
        dataSet.add(HistoryData(LocalDateTime.now(), "Camera Name", 5))
        dataSet.add(HistoryData(LocalDateTime.now(), "Camera Name", 5))

        val adapter = HistoryRecyclerViewAdapter(dataSet)
        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.history_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}
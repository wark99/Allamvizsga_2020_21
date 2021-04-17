package com.example.allamvizsga_2020_21.main.Menu.History

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.allamvizsga_2020_21.Firebase.Data.HistoryData
import com.example.allamvizsga_2020_21.R

class HistoryRecyclerViewAdapter(
    private var dataSet: ArrayList<HistoryData>
) : RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var timeTextView: TextView = view.findViewById(R.id.timeTextView)
        var cameraNameTextView: TextView = view.findViewById(R.id.cameraNameTextView)
        var severityTextView: TextView = view.findViewById(R.id.severityTextView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_history, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.timeTextView.text = dataSet[position].time.toString()
        viewHolder.cameraNameTextView.text = dataSet[position].camera
        viewHolder.severityTextView.text = dataSet[position].severity.toString()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}
package com.example.allamvizsga_2020_21.main.Menu.History

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allamvizsga_2020_21.Firebase.Data.HistoryData
import com.example.allamvizsga_2020_21.Firebase.LoadingSwitch
import com.example.allamvizsga_2020_21.R
import kotlinx.coroutines.Dispatchers

class HistoryFragment : Fragment(), HistoryContract.View, LoadingSwitch {

    private val presenter: HistoryContract.Presenter = HistoryPresenter(this)

    private lateinit var currentLayout: ConstraintLayout
    private lateinit var loadingLayout: ConstraintLayout

    private lateinit var recyclerView: RecyclerView
    private lateinit var errorTextView: TextView

    private lateinit var adapter: HistoryRecyclerViewAdapter
    private lateinit var dataSet: ArrayList<HistoryData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onResume() {
        super.onResume()

        currentLayout = requireActivity().findViewById(R.id.historyLayout)
        loadingLayout = requireActivity().findViewById(R.id.historyLoadingLayout)

        recyclerView = requireActivity().findViewById(R.id.history_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.visibility = View.VISIBLE

        errorTextView = requireActivity().findViewById(R.id.historyErrorTextView)
        errorTextView.visibility = View.INVISIBLE

        Dispatchers.Main.run {
            showLoading()
        }

        Dispatchers.IO.run {
            presenter.loadHistory()
        }
    }

    override fun historyLoaded(historyList: ArrayList<HistoryData>) {
        dataSet = historyList

        adapter = HistoryRecyclerViewAdapter(dataSet)
        recyclerView.adapter = adapter

        stopLoading()
    }

    override fun loadingError() {
        recyclerView.visibility = View.INVISIBLE
        errorTextView.visibility = View.VISIBLE

        stopLoading()
    }

    override fun showLoading() {
        currentLayout.visibility = View.INVISIBLE
        loadingLayout.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        loadingLayout.visibility = View.INVISIBLE
        currentLayout.visibility = View.VISIBLE
    }
}
package com.example.allamvizsga_2020_21.main.Menu.History

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.allamvizsga_2020_21.Firebase.Data.HistoryData
import com.example.allamvizsga_2020_21.R
import com.example.allamvizsga_2020_21.Utils.ConnectionChecker
import com.example.allamvizsga_2020_21.Utils.LoadingSwitch
import kotlinx.coroutines.Dispatchers

class HistoryFragment : Fragment(), HistoryContract.View {

    private val presenter: HistoryContract.Presenter = HistoryPresenter(this)

    private lateinit var currentLayout: ConstraintLayout
    private lateinit var loadingLayout: ConstraintLayout

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var historyContentLayout: ConstraintLayout
    private lateinit var historyErrorLayout: ConstraintLayout

    private lateinit var recyclerView: RecyclerView
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

        swipeRefreshLayout = requireActivity().findViewById(R.id.historySwipeLayout)

        historyContentLayout = requireActivity().findViewById(R.id.historyContentLayout)
        historyErrorLayout = requireActivity().findViewById(R.id.historyErrorLayout)
        LoadingSwitch().showLoading(historyContentLayout, historyErrorLayout)

        recyclerView = requireActivity().findViewById(R.id.history_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadMainLayout()

        swipeRefreshLayout.setOnRefreshListener {
            loadMainLayout()
        }
    }

    private fun loadMainLayout() {
        if (ConnectionChecker(requireContext()).isConnected()) {
            Dispatchers.Main.run {
                LoadingSwitch().showLoading(currentLayout, loadingLayout)
            }

            Dispatchers.IO.run {
                presenter.loadHistory()
            }
        } else {
            presenter.connectionError()
        }
    }

    override fun historyLoaded(historyList: ArrayList<HistoryData>) {
        dataSet = historyList
        adapter = HistoryRecyclerViewAdapter(dataSet)
        recyclerView.adapter = adapter

        LoadingSwitch().stopLoading(historyErrorLayout, historyContentLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun loadingError() {
        LoadingSwitch().stopLoading(historyContentLayout, historyErrorLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
    }
}
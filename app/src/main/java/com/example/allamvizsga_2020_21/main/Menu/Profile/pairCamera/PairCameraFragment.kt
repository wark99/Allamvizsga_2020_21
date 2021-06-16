package com.example.allamvizsga_2020_21.main.Menu.Profile.pairCamera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.allamvizsga_2020_21.Firebase.Data.PairCameraData
import com.example.allamvizsga_2020_21.R
import com.example.allamvizsga_2020_21.Utils.ConnectionChecker
import com.example.allamvizsga_2020_21.Utils.LoadingSwitch
import kotlinx.coroutines.Dispatchers

class PairCameraFragment : Fragment(), PairCameraContract.View,
    PairCameraRecyclerViewAdapter.OnConnectClickListener,
    PairCameraRecyclerViewAdapter.OnDisconnectClickListener {

    private val presenter: PairCameraContract.Presenter = PairCameraPresenter(this)

    private lateinit var loadingLayout: ConstraintLayout
    private lateinit var currentLayout: ConstraintLayout

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var pairCameraContentLayout: ConstraintLayout
    private lateinit var pairCameraErrorLayout: ConstraintLayout

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PairCameraRecyclerViewAdapter
    private lateinit var dataSet: ArrayList<PairCameraData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pair_camera, container, false)
    }

    override fun onResume() {
        super.onResume()

        currentLayout = requireActivity().findViewById(R.id.pairCameraLayout)
        loadingLayout = requireActivity().findViewById(R.id.pairCameraLoadingLayout)

        swipeRefreshLayout = requireActivity().findViewById(R.id.pairCameraSwipeLayout)

        pairCameraContentLayout = requireActivity().findViewById(R.id.pairCameraContentLayout)
        pairCameraErrorLayout = requireActivity().findViewById(R.id.pairCameraErrorLayout)
        LoadingSwitch().showLoading(pairCameraContentLayout, pairCameraErrorLayout)

        recyclerView = requireActivity().findViewById(R.id.pairCameraRecyclerView)
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
                presenter.loadCameras()
            }
        } else {
            presenter.connectionError()
        }
    }

    override fun loadingError() {
        LoadingSwitch().stopLoading(pairCameraContentLayout, pairCameraErrorLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun cameraLoaded(pairCameraData: ArrayList<PairCameraData>) {
        dataSet = pairCameraData
        adapter = PairCameraRecyclerViewAdapter(dataSet, this, this)
        recyclerView.adapter = adapter

        LoadingSwitch().stopLoading(pairCameraErrorLayout, pairCameraContentLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun connectionSuccess() {
        LoadingSwitch().stopLoading(pairCameraErrorLayout, pairCameraContentLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
        Toast.makeText(requireContext(), "Camera connected", Toast.LENGTH_SHORT).show()
    }

    override fun connectionFail(errorMessage: String) {
        LoadingSwitch().stopLoading(pairCameraContentLayout, pairCameraErrorLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
        Toast.makeText(requireContext(), "Connection Failed", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectClick(position: Int) {
        if (ConnectionChecker(requireContext()).isConnected()) {
            Dispatchers.Main.run {
                LoadingSwitch().showLoading(currentLayout, loadingLayout)
            }

            Dispatchers.IO.run {
                presenter.connectCamera(dataSet[position])
            }
        } else {
            presenter.connectionError()
        }
    }

    override fun onDisconnectClick(position: Int) {
        if (ConnectionChecker(requireContext()).isConnected()) {
            Dispatchers.Main.run {
                LoadingSwitch().showLoading(currentLayout, loadingLayout)
            }

            Dispatchers.IO.run {
                presenter.disconnectCamera(dataSet[position])
            }
        } else {
            presenter.connectionError()
        }
    }
}
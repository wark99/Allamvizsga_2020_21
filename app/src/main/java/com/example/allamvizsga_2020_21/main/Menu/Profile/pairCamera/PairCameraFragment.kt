package com.example.allamvizsga_2020_21.main.Menu.Profile.pairCamera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.allamvizsga_2020_21.Firebase.Data.CameraSetting
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

    private lateinit var feedbackTextView: TextView
    private lateinit var saveButton: Button
    private lateinit var pairCameraError: TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PairCameraRecyclerViewAdapter
    private lateinit var cameraDataSet: ArrayList<PairCameraData>
    private lateinit var settingsDataSet: ArrayList<CameraSetting>

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

        feedbackTextView = requireActivity().findViewById(R.id.feedbackTextView)
        saveButton = requireActivity().findViewById(R.id.saveSettingsButton)
        pairCameraError = requireActivity().findViewById(R.id.pairCameraErrorTextView)
        recyclerView = requireActivity().findViewById(R.id.pairCameraRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadMainLayout()

        saveButton.setOnClickListener {
            saveChanges()
        }

        swipeRefreshLayout.setOnRefreshListener {
            loadMainLayout()
        }
    }

    private fun saveChanges() {
        if (ConnectionChecker(requireContext()).isConnected()) {
            Dispatchers.Main.run {
                LoadingSwitch().showLoading(currentLayout, loadingLayout)
            }

            Dispatchers.IO.run {
                presenter.saveSettings(cameraDataSet, settingsDataSet)
            }
        } else {
            presenter.connectionError()
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
        pairCameraError.text = "Network Error"
        LoadingSwitch().stopLoading(pairCameraContentLayout, pairCameraErrorLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun cameraLoaded(
        pairCameraData: ArrayList<PairCameraData>,
        settingList: ArrayList<CameraSetting>
    ) {
        cameraDataSet = pairCameraData
        settingsDataSet = settingList
        adapter = PairCameraRecyclerViewAdapter(cameraDataSet, settingsDataSet, this, this)
        recyclerView.adapter = adapter

        LoadingSwitch().stopLoading(pairCameraErrorLayout, pairCameraContentLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun connectionSuccess() {
        feedbackTextView.text = "Camera connected"
        LoadingSwitch().stopLoading(pairCameraErrorLayout, pairCameraContentLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun disconnectionSuccess() {
        feedbackTextView.text = "Camera disconnected"
        LoadingSwitch().stopLoading(pairCameraErrorLayout, pairCameraContentLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
        Toast.makeText(requireContext(), "Camera disconnected", Toast.LENGTH_SHORT).show()
    }

    override fun connectionFail(errorMessage: String) {
        feedbackTextView.text = ""
        pairCameraError.text = errorMessage
        LoadingSwitch().stopLoading(pairCameraContentLayout, pairCameraErrorLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun disconnectionFail(errorMessage: String) {
        feedbackTextView.text = ""
        pairCameraError.text = errorMessage
        LoadingSwitch().stopLoading(pairCameraContentLayout, pairCameraErrorLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onSaveSuccess() {
        feedbackTextView.text = "Save Success"
        LoadingSwitch().stopLoading(pairCameraErrorLayout, pairCameraContentLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onSaveFail(message: String) {
        feedbackTextView.text = ""
        pairCameraError.text = message
        LoadingSwitch().stopLoading(pairCameraContentLayout, pairCameraErrorLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onConnectClick(position: Int) {
        if (ConnectionChecker(requireContext()).isConnected()) {
            Dispatchers.Main.run {
                LoadingSwitch().showLoading(currentLayout, loadingLayout)
            }

            Dispatchers.IO.run {
                presenter.connectCamera(cameraDataSet[position])
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
                presenter.disconnectCamera(cameraDataSet[position])
            }
        } else {
            presenter.connectionError()
        }
    }
}
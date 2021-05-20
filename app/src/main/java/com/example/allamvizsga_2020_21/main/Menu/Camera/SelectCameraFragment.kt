package com.example.allamvizsga_2020_21.main.Menu.Camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.allamvizsga_2020_21.R
import com.example.allamvizsga_2020_21.Utils.ConnectionChecker
import com.example.allamvizsga_2020_21.Utils.LoadingSwitch
import com.example.allamvizsga_2020_21.mvvm.VideoViewModel
import kotlinx.coroutines.Dispatchers


class SelectCameraFragment : Fragment(), SelectCameraContract.View,
    CameraSelectionRecyclerViewAdapter.OnItemClickListener {

    private val presenter: SelectCameraContract.Presenter = SelectCameraPresenter(this)
    private lateinit var viewModel: VideoViewModel

    private lateinit var currentLayout: ConstraintLayout
    private lateinit var loadingLayout: ConstraintLayout

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var cameraContentLayout: ConstraintLayout
    private lateinit var cameraErrorLayout: ConstraintLayout

    private lateinit var navController: NavController

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataSet: ArrayList<String>
    private lateinit var adapter: CameraSelectionRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(VideoViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onResume() {
        super.onResume()

        navController = findNavController()

        currentLayout = requireActivity().findViewById(R.id.cameraSelectionLayout)
        loadingLayout = requireActivity().findViewById(R.id.cameraSelectionLoadingLayout)

        swipeRefreshLayout = requireActivity().findViewById(R.id.cameraSwipeLayout)

        cameraContentLayout = requireActivity().findViewById(R.id.cameraContentLayout)
        cameraErrorLayout = requireActivity().findViewById(R.id.cameraErrorLayout)
        LoadingSwitch().showLoading(cameraContentLayout, cameraErrorLayout)

        recyclerView = requireActivity().findViewById(R.id.cameraListRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

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

    override fun camerasLoaded(cameraList: ArrayList<String>) {
        dataSet = cameraList
        adapter = CameraSelectionRecyclerViewAdapter(dataSet, this)
        recyclerView.adapter = adapter

        LoadingSwitch().showLoading(cameraErrorLayout, cameraContentLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun loadingError() {
        LoadingSwitch().showLoading(cameraContentLayout, cameraErrorLayout)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onItemClick(position: Int) {
        viewModel.data.value = dataSet[position]

        navController.navigate(R.id.toLiveFromCamera)
    }
}
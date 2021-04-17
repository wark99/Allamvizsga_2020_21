package com.example.allamvizsga_2020_21.main.Menu.Camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allamvizsga_2020_21.Firebase.LoadingSwitch
import com.example.allamvizsga_2020_21.R
import com.example.allamvizsga_2020_21.mvvm.VideoViewModel
import kotlinx.coroutines.Dispatchers


class SelectCameraFragment : Fragment(), SelectCameraContract.View,
    CameraSelectionRecyclerViewAdapter.OnItemClickListener, LoadingSwitch {

    private val presenter: SelectCameraContract.Presenter = SelectCameraPresenter(this)
    private lateinit var viewModel: VideoViewModel

    private lateinit var currentLayout: ConstraintLayout
    private lateinit var loadingLayout: ConstraintLayout

    private lateinit var navController: NavController

    private lateinit var recyclerView: RecyclerView
    private lateinit var errorMessage: TextView

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

        recyclerView = requireActivity().findViewById(R.id.cameraListRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.visibility = View.VISIBLE

        errorMessage = requireActivity().findViewById(R.id.networkErrorTextView)
        errorMessage.visibility = View.INVISIBLE

        Dispatchers.Main.run {
            showLoading()
        }

        Dispatchers.IO.run {
            presenter.loadCameras()
        }
    }

    override fun onItemClick(position: Int) {
        viewModel.data.value = dataSet[position]

        navController.navigate(R.id.toLiveFromCamera)
    }

    override fun camerasLoaded(cameraList: ArrayList<String>) {
        dataSet = cameraList

        adapter = CameraSelectionRecyclerViewAdapter(dataSet, this)
        recyclerView.adapter = adapter

        stopLoading()
    }

    override fun loadingError() {
        recyclerView.visibility = View.INVISIBLE
        errorMessage.visibility = View.VISIBLE

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
package com.example.allamvizsga_2020_21.main.Menu.Camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allamvizsga_2020_21.R


class SelectCameraFragment : Fragment(), SelectCameraContract.View,
    CameraSelectionRecyclerViewAdapter.OnItemClickListener {

    private val presenter: SelectCameraContract.Presenter = SelectCameraPresenter(this)

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

    override fun onResume() {
        super.onResume()

        navController = findNavController()

        recyclerView = requireActivity().findViewById(R.id.cameraListRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.visibility = View.VISIBLE

        errorMessage = requireActivity().findViewById(R.id.networkErrorTextView)
        errorMessage.visibility = View.INVISIBLE

        presenter.loadCameras()
    }

    override fun onItemClick(position: Int) {
        navController.navigate(R.id.toLiveFromCamera)
    }

    override fun camerasLoaded(cameraList: ArrayList<String>) {
        dataSet = cameraList

        adapter = CameraSelectionRecyclerViewAdapter(dataSet, this)
        recyclerView.adapter = adapter
    }

    override fun loadingError() {
        recyclerView.visibility = View.INVISIBLE
        errorMessage.visibility = View.VISIBLE
    }
}
package com.example.allamvizsga_2020_21.main.Menu.Camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allamvizsga_2020_21.R


class SelectCameraFragment : Fragment(), CameraSelectionRecyclerViewAdapter.OnItemClickListener {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_camera, container, false)
    }

    override fun onResume() {
        super.onResume()

        val dataSet = arrayListOf<String>()
        dataSet.add("https://images.unsplash.com/photo-1550355291-bbee04a92027?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=676&q=80")
        dataSet.add("https://images.unsplash.com/photo-1549399542-7e3f8b79c341?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=634&q=80")
        dataSet.add("https://firebasestorage.googleapis.com/v0/b/allamvizsga-b617a.appspot.com/o/Cat%20pushed%20dumpster.mp4?alt=media&token=973683a3-283e-4b40-8e57-cf90e933924e")

        val adapter = CameraSelectionRecyclerViewAdapter(dataSet, this)
        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.cameraListRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        navController = findNavController()
    }

    override fun onItemClick(position: Int) {
        navController.navigate(R.id.toLiveFromCamera)
    }
}
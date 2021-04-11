package com.example.allamvizsga_2020_21.main.Menu.Profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allamvizsga_2020_21.Firebase.Data.ProfileData
import com.example.allamvizsga_2020_21.R

class ProfileFragment : Fragment(), ProfileRecyclerViewAdapter.OnItemLongClickListener {

    private val dataSet = arrayListOf<ProfileData>()
    private lateinit var adapter: ProfileRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onResume() {
        super.onResume()

        val uri = Uri.parse("android.resource://com.example.allamvizsga_2020_21/drawable/profile")

        dataSet.add(ProfileData(uri, "name"))
        dataSet.add(ProfileData(uri, "name"))
        dataSet.add(ProfileData(uri, "name"))
        dataSet.add(ProfileData(uri, "name"))
        dataSet.add(ProfileData(uri, "name"))
        dataSet.add(ProfileData(uri, "name"))
        dataSet.add(ProfileData(uri, "name"))

        adapter = ProfileRecyclerViewAdapter(dataSet, this)
        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.trustListRecyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }

    override fun onItemLongClick(position: Int) {
        dataSet.removeAt(position)
        adapter.notifyDataSetChanged()
    }
}
package com.example.allamvizsga_2020_21.main.Menu.Profile.removePerson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.allamvizsga_2020_21.R

class RemovePersonFragment : Fragment() {

    private lateinit var loadingLayout: ConstraintLayout
    private lateinit var currentLayout: ConstraintLayout

    private lateinit var recyclerView: RecyclerView
    private lateinit var errorTextView: TextView
    private lateinit var saveButton: Button

    private lateinit var adapter: ProfileRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_remove_person, container, false)
    }

    override fun onResume() {
        super.onResume()

        loadingLayout = requireActivity().findViewById(R.id.removePersonLoadingLayout)
        currentLayout = requireActivity().findViewById(R.id.removePersonLayout)

        recyclerView = requireActivity().findViewById(R.id.removePersonRecyclerView)
        errorTextView = requireActivity().findViewById(R.id.removePersonErrorTextView)
        saveButton = requireActivity().findViewById(R.id.saveButton)

        saveButton.setOnClickListener {

        }
    }
}
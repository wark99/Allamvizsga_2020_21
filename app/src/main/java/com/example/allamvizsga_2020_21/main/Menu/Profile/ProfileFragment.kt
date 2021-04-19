package com.example.allamvizsga_2020_21.main.Menu.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.allamvizsga_2020_21.R

class ProfileFragment : Fragment() {

    private lateinit var navController: NavController

    private lateinit var addPersonButton: ImageButton
    private lateinit var removePersonButton: ImageButton
    private lateinit var addCameraButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onResume() {
        super.onResume()

        navController = findNavController()

        addPersonButton = requireActivity().findViewById(R.id.addPersonImageButton)
        removePersonButton = requireActivity().findViewById(R.id.removePersonImageButton)
        addCameraButton = requireActivity().findViewById(R.id.addCameraImageButton)

        addPersonButton.setOnClickListener {

        }
        removePersonButton.setOnClickListener {

        }
        addCameraButton.setOnClickListener {

        }
    }
}
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

        addPersonButton.setOnClickListener {
            navController.navigate(R.id.to_addPersonFragment_from_profileFragment)
        }
        removePersonButton.setOnClickListener {
            navController.navigate(R.id.to_removePersonFragment_from_profileFragment_from_profile)
        }
    }
}
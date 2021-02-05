package com.example.allamvizsga_2020_21.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.allamvizsga_2020_21.R

class MainFragment : Fragment() {

    private lateinit var camerasButton: ImageButton
    private lateinit var historyButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var logOutButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onResume() {
        super.onResume()

        val currentActivity = requireActivity()
        camerasButton = currentActivity.findViewById(R.id.CameraImageButton)
        historyButton = currentActivity.findViewById(R.id.HistoryImageButton)
        profileButton = currentActivity.findViewById(R.id.ProfileImageButton)
        logOutButton = currentActivity.findViewById(R.id.LogOutImageButton)

        val navController = findNavController()

        logOutButton.setOnClickListener {
            navController.navigate(R.id.to_login_from_main)
        }

        currentActivity.onBackPressedDispatcher.addCallback(this) {
            currentActivity.finishAffinity()
        }
    }
}
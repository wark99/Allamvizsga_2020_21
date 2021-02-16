package com.example.allamvizsga_2020_21.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.allamvizsga_2020_21.R
import kotlinx.coroutines.Dispatchers

class MainFragment : Fragment(), MainContract.View {

    private val presenter: MainContract.Presenter = MainPresenter(this)

    private lateinit var camerasButton: ImageButton
    private lateinit var historyButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var logOutButton: ImageButton

    private lateinit var navController: NavController

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

        navController = findNavController()

        logOutButton.setOnClickListener {
            Dispatchers.IO.run {
                presenter.logOut()
            }
        }

        currentActivity.onBackPressedDispatcher.addCallback(this) {
            currentActivity.finishAffinity()
        }
    }

    override fun onLogOut() {
        navController.navigate(R.id.to_login_from_main)
    }
}
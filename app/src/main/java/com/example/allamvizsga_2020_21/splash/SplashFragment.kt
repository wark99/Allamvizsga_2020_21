package com.example.allamvizsga_2020_21.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.allamvizsga_2020_21.R
import kotlinx.coroutines.Dispatchers

class SplashFragment : Fragment(), SplashContract.View {

    private val presenter: SplashContract.Presenter = SplashPresenter(this)

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onResume() {
        super.onResume()

        navController = findNavController()

        Dispatchers.IO.run {
            presenter.showNextScreen()
        }
    }

    override fun showLoginScree() {
        navController.navigate(R.id.to_login_form_splash)
    }

    override fun showMainScreen() {
        navController.navigate(R.id.to_main_from_splash)
    }
}
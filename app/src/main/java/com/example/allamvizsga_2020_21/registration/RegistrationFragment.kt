package com.example.allamvizsga_2020_21.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.allamvizsga_2020_21.R


class RegistrationFragment : Fragment() {

    private lateinit var termsButton: Button
    private lateinit var signUpButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onResume() {
        super.onResume()

        val currentActivity = requireActivity()
        currentActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        termsButton = currentActivity.findViewById(R.id.TermsButton)
        signUpButton = currentActivity.findViewById(R.id.RegistrationButton)

        val navController = findNavController()

        termsButton.setOnClickListener {
            navController.navigate(R.id.to_terms_from_registration)
        }

        signUpButton.setOnClickListener {
            navController.navigate(R.id.to_main_from_registration)
        }
    }
}
package com.example.allamvizsga_2020_21.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.allamvizsga_2020_21.Firebase.Listeners.DataSnapshotListener
import com.example.allamvizsga_2020_21.Firebase.FirebaseWriteRead
import com.example.allamvizsga_2020_21.R
import com.google.firebase.database.DataSnapshot

class LoginFragment : Fragment() {

    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button
    private lateinit var changePasswordButton: Button

    private val firebase = FirebaseWriteRead

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onResume() {
        super.onResume()

        firebase.readFromDatabase("__init__", object : DataSnapshotListener {
            override fun onDataSnapshotReady(dataSnapshot: DataSnapshot) {
                Toast.makeText(requireContext(), dataSnapshot.value.toString(), Toast.LENGTH_LONG)
                    .show()
            }

            override fun onError() {
                Toast.makeText(requireContext(), "Hiba!", Toast.LENGTH_LONG).show()
            }
        })

        val currentActivity = requireActivity()
        signInButton = currentActivity.findViewById(R.id.LoginButton)
        signUpButton = currentActivity.findViewById(R.id.SignUpButton)
        changePasswordButton = currentActivity.findViewById(R.id.ChangePasswordButton)

        val navController = findNavController()

        signInButton.setOnClickListener {
            navController.navigate(R.id.to_main_form_login)
        }

        signUpButton.setOnClickListener {
            navController.navigate(R.id.to_registration_from_login)
        }

        changePasswordButton.setOnClickListener {
            navController.navigate(R.id.to_pasword_from_login)
        }

        currentActivity.onBackPressedDispatcher.addCallback(this) {
            currentActivity.finishAffinity()
        }
    }
}
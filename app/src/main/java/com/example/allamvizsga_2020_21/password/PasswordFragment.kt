package com.example.allamvizsga_2020_21.password

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.allamvizsga_2020_21.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PasswordFragment : Fragment() {

    private lateinit var submitButton: Button
    private lateinit var mailsFloatingActionButton: FloatingActionButton
    private var submitWasPressed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onResume() {
        super.onResume()

        val currentActivity = requireActivity()
        submitButton = currentActivity.findViewById(R.id.SubmitButton)
        mailsFloatingActionButton = currentActivity.findViewById(R.id.MailFloatingActionButton)

        val navController = findNavController()

        submitButton.setOnClickListener {
            submitWasPressed = true
        }

        mailsFloatingActionButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

            //TODO: When to redirect the user?
            if (submitWasPressed) {
                navController.navigate(R.id.to_login_from_password)
                submitWasPressed = false
            }
        }
    }
}
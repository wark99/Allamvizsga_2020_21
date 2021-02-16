package com.example.allamvizsga_2020_21.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.allamvizsga_2020_21.Firebase.LoadingSwitch
import com.example.allamvizsga_2020_21.R
import kotlinx.coroutines.Dispatchers

class LoginFragment : Fragment(), LoginContract.View, LoadingSwitch {

    private val presenter: LoginContract.Presenter = LoginPresenter(this)

    private lateinit var currentLayout: ConstraintLayout
    private lateinit var loadingLayout: ConstraintLayout

    private lateinit var error: TextView
    private lateinit var mail: EditText
    private lateinit var password: EditText

    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button
    private lateinit var changePasswordButton: Button

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onResume() {
        super.onResume()

        val currentActivity = requireActivity()
        navController = findNavController()

        loadingLayout = currentActivity.findViewById(R.id.logInLoading)
        currentLayout = currentActivity.findViewById(R.id.signInLayout)

        error = currentActivity.findViewById(R.id.LoginErrorOutputTextView)
        mail = currentActivity.findViewById(R.id.LoginMailInputEditText)
        password = currentActivity.findViewById(R.id.LoginPasswordEditText)

        signInButton = currentActivity.findViewById(R.id.LoginButton)
        signUpButton = currentActivity.findViewById(R.id.SignUpButton)
        changePasswordButton = currentActivity.findViewById(R.id.ChangePasswordButton)

        signInButton.setOnClickListener {
            Dispatchers.Main.run {
                showLoading()
            }

            Dispatchers.IO.run {
                presenter.signIn(
                    mail.text.toString(),
                    password.text.toString()
                )
            }
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

    override fun error(errorMessage: String) {
        error.text = errorMessage
        stopLoading()
    }

    override fun verified() {
        navController.navigate(R.id.to_main_form_login)
        stopLoading()
    }

    override fun showLoading() {
        currentLayout.visibility = View.INVISIBLE
        loadingLayout.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        loadingLayout.visibility = View.INVISIBLE
        currentLayout.visibility = View.VISIBLE
    }
}
package com.example.allamvizsga_2020_21.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.allamvizsga_2020_21.Firebase.LoadingSwitch
import com.example.allamvizsga_2020_21.R
import kotlinx.coroutines.Dispatchers

class RegistrationFragment : Fragment(), RegistrationContract.View, LoadingSwitch {

    private val presenter: RegistrationContract.Presenter = RegistrationPresenter(this)

    private lateinit var currentLayout: ConstraintLayout
    private lateinit var loadingLayout: ConstraintLayout

    private lateinit var error: TextView
    private lateinit var username: EditText
    private lateinit var mail: EditText
    private lateinit var password: EditText
    private lateinit var passwordAgain: EditText
    private lateinit var terms: CheckBox

    private lateinit var termsButton: Button
    private lateinit var signUpButton: Button

    private lateinit var navController: NavController

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
        navController = findNavController()

        loadingLayout = currentActivity.findViewById(R.id.registrationLoading)
        currentLayout = currentActivity.findViewById(R.id.signUpLayout)

        error = currentActivity.findViewById(R.id.RegistrationErrorOutputTextView)
        username = currentActivity.findViewById(R.id.RegistrationUsernameInputEditText)
        mail = currentActivity.findViewById(R.id.RegistrationMailInputEditText)
        password = currentActivity.findViewById(R.id.RegistrationPasswordInputEditText)
        passwordAgain = currentActivity.findViewById(R.id.RegistrationPasswordInputEditTextAgain)
        terms = currentActivity.findViewById(R.id.CheckBox)

        termsButton = currentActivity.findViewById(R.id.TermsButton)
        signUpButton = currentActivity.findViewById(R.id.RegistrationButton)

        termsButton.setOnClickListener {
            navController.navigate(R.id.to_terms_from_registration)
        }

        signUpButton.setOnClickListener {
            Dispatchers.Main.run {
                showLoading()
            }

            Dispatchers.IO.run {
                presenter.singUp(
                    username.text.toString(),
                    mail.text.toString(),
                    password.text.toString(),
                    passwordAgain.text.toString(),
                    terms.isChecked
                )
            }
        }
    }

    override fun error(errorMessage: String) {
        error.text = errorMessage
        stopLoading()
    }

    override fun verified() {
        navController.navigate(R.id.to_main_from_registration)
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
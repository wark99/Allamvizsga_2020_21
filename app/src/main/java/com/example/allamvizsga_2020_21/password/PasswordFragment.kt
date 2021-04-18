package com.example.allamvizsga_2020_21.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.allamvizsga_2020_21.LoadingSwitch
import com.example.allamvizsga_2020_21.R
import kotlinx.coroutines.Dispatchers

class PasswordFragment : Fragment(), PasswordContract.View {

    private val presenter: PasswordContract.Presenter = PasswordPresenter(this)

    private lateinit var loadingLayout: ConstraintLayout
    private lateinit var currentLayout: ConstraintLayout

    private lateinit var mail: EditText
    private lateinit var error: TextView

    private lateinit var submitButton: Button

    private lateinit var navController: NavController

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

        loadingLayout = currentActivity.findViewById(R.id.PasswordLoading)
        currentLayout = currentActivity.findViewById(R.id.PasswordLayout)

        mail = currentActivity.findViewById(R.id.PassworMailInputEditText)
        error = currentActivity.findViewById(R.id.PasswordErrorOutputTextView)

        submitButton = currentActivity.findViewById(R.id.SubmitButton)

        navController = findNavController()

        submitButton.setOnClickListener {
            Dispatchers.Main.run {
                LoadingSwitch().showLoading(currentLayout, loadingLayout)
            }

            Dispatchers.IO.run {
                presenter.setNewPassword(mail.text.toString())
            }
        }
    }

    override fun error(errorMessage: String) {
        error.text = errorMessage
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
    }

    override fun verified() {
        navController.navigate(R.id.to_login_from_password)
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
    }
}
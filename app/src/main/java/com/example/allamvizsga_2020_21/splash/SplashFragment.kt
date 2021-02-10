package com.example.allamvizsga_2020_21.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.allamvizsga_2020_21.Firebase.FirebaseWriteRead
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener
import com.example.allamvizsga_2020_21.R

class SplashFragment : Fragment() {

    private val firebase = FirebaseWriteRead

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onResume() {
        super.onResume()

        val navController = findNavController()
        firebase.writeToDatabase("__init__", "Hello Stranger!", object : SuccessListener {
            override fun onSuccess() {
                navController.navigate(R.id.to_login_form_splash)
            }

            override fun onFail(exception: Exception) {
                Toast.makeText(requireContext(), exception.toString(), Toast.LENGTH_LONG).show()
            }

        })
    }
}
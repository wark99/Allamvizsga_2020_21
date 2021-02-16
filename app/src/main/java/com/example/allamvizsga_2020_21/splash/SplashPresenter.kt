package com.example.allamvizsga_2020_21.splash

import com.example.allamvizsga_2020_21.Firebase.FirebaseAccountManager
import com.example.allamvizsga_2020_21.Firebase.Listeners.LogInListener

class SplashPresenter(view: SplashContract.View) : SplashContract.Presenter(view) {
    private val firebaseAccountManager = FirebaseAccountManager()

    override fun showNextScreen() {
        firebaseAccountManager.isLoggedIn(object : LogInListener {
            override fun onLogIn() {
                view!!.showMainScreen()
                firebaseAccountManager.stopListening()
            }

            override fun onLogOut() {
                view!!.showLoginScree()
                firebaseAccountManager.stopListening()
            }
        })
    }
}
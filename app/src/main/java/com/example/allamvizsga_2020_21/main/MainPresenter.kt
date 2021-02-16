package com.example.allamvizsga_2020_21.main

import com.google.firebase.auth.FirebaseAuth

class MainPresenter(view: MainContract.View) : MainContract.Presenter(view) {

    override fun logOut() {
        FirebaseAuth.getInstance().signOut()
        view!!.onLogOut()
    }
}
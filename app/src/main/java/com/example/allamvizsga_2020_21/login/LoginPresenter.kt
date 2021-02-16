package com.example.allamvizsga_2020_21.login

import com.example.allamvizsga_2020_21.Firebase.FirebaseAccountManager
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener
import com.example.allamvizsga_2020_21.Firebase.UserInputCheck

class LoginPresenter(view: LoginContract.View) : LoginContract.Presenter(view) {

    override fun signIn(mail: String, password: String) {
        val userInputCheck = UserInputCheck("")

        userInputCheck.mainCheck(mail)
        userInputCheck.logInPasswordCheck(password)

        val errorMessage = userInputCheck.getErrorMessage()

        if (errorMessage.isNotEmpty()) {
            view!!.error(errorMessage)
        } else {
            FirebaseAccountManager().logIn(mail, password, object : SuccessListener {
                override fun onSuccess() {
                    view!!.verified()
                }

                override fun onFail(exception: Exception) {
                    view!!.error(exception.message.toString())
                }
            })
        }
    }
}
package com.example.allamvizsga_2020_21.password

import com.example.allamvizsga_2020_21.Firebase.FirebaseAccountManager
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener
import com.example.allamvizsga_2020_21.Firebase.UserInputCheck

class PasswordPresenter(view: PasswordContract.View) : PasswordContract.Presenter(view) {

    override fun setNewPassword(mail: String) {

        val userInputCheck = UserInputCheck("")

        userInputCheck.mainCheck(mail)

        val errorMessage = userInputCheck.getErrorMessage()

        if (errorMessage.isNotEmpty()) {
            view!!.error(errorMessage)
        } else {
            FirebaseAccountManager().setNewPassword(mail, object : SuccessListener {
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
package com.example.allamvizsga_2020_21.registration

import com.example.allamvizsga_2020_21.Firebase.Data.UserData
import com.example.allamvizsga_2020_21.Firebase.FirebaseAccountManager
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener
import com.example.allamvizsga_2020_21.Firebase.UserAPIService
import com.example.allamvizsga_2020_21.Firebase.UserInputCheck

class RegistrationPresenter(view: RegistrationContract.View) :
    RegistrationContract.Presenter(view) {

    override fun singUp(
        username: String,
        mail: String,
        password: String,
        passwordAgain: String,
        terms: Boolean
    ) {
        val userInputCheck = UserInputCheck("")

        userInputCheck.usernameCheck(username)
        userInputCheck.mainCheck(mail)
        userInputCheck.singUpPasswordCheck(password, passwordAgain)
        userInputCheck.termsCheck(terms)

        val errorMessage = userInputCheck.getErrorMessage()

        if (errorMessage.isNotEmpty()) {
            view!!.error(errorMessage)
        } else {
            FirebaseAccountManager().signUp(mail, password, object : SuccessListener {
                override fun onSuccess() {
                    UserAPIService.writeUserData(UserData(username, mail, terms),
                        object : SuccessListener {
                            override fun onSuccess() {
                                view!!.verified()
                            }

                            override fun onFail(exception: Exception) {
                                view!!.error(exception.message.toString())
                            }
                        })
                }

                override fun onFail(exception: Exception) {
                    view!!.error(exception.message.toString())
                }
            })
        }
    }
}
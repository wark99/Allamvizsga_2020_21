package com.example.allamvizsga_2020_21.registration

import com.example.allamvizsga_2020_21.Firebase.Data.UserData
import com.example.allamvizsga_2020_21.Firebase.FirebaseAccountManager
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener
import com.example.allamvizsga_2020_21.Firebase.UserAPIService

class RegistrationPresenter(view: RegistrationContract.View) :
    RegistrationContract.Presenter(view) {

    private val usernameAndPasswordPattern = "[^a-zA-Z0-9]".toRegex()
    private val mailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

    override fun singUp(
        username: String,
        mail: String,
        password: String,
        passwordAgain: String,
        terms: Boolean
    ) {
        var error = false
        var errorMessage = ""

        if (username.length < 4) {
            error = true
            errorMessage += "Username is short or empty!\n"
        }

        if (usernameAndPasswordPattern.containsMatchIn(username)) {
            error = true
            errorMessage += "Username contains invalid characters!\n"
        }

        if (mail.isEmpty()) {
            error = true
            errorMessage += "Empty mail field!\n"
        }

        if (!mailPattern.matches(mail)) {
            error = true
            errorMessage += "Invalid email address!\n"
        }

        if (password.length < 6) {
            error = true
            errorMessage += "Password is short or empty!\n"
        }

        if (usernameAndPasswordPattern.containsMatchIn(password)) {
            error = true
            errorMessage += "Invalid password!\n"
        }

        if (passwordAgain != password) {
            error = true
            errorMessage += "Different passwords!\n"
        }

        if (!terms) {
            error = true
            errorMessage += "Check user agreement!"
        }

        if (error) {
            view!!.error(errorMessage)
        } else {
            FirebaseAccountManager().signUp(mail, password, object : SuccessListener {
                override fun onSuccess() {
                    UserAPIService.writeUserData(UserData(username, mail, password, terms),
                        object : SuccessListener {
                            override fun onSuccess() {
                                view!!.verified()
                            }

                            override fun onFail(exception: Exception) {
                                view!!.firebaseError(exception.message.toString())
                            }

                        })
                }

                override fun onFail(exception: Exception) {
                    view!!.firebaseError(exception.message.toString())
                }
            })
        }
    }
}
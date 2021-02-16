package com.example.allamvizsga_2020_21.Firebase

class UserInputCheck(private var errorMessage: String) {

    private val usernamePattern = Regex("[^a-zA-Z0-9]")
    private val mailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    private val passwordPattern = Regex("[^a-zA-Z0-9]")

    fun usernameCheck(username: String) {
        if (username.length < 4) {
            errorMessage += "Username is short or empty!\n"
        }

        if (usernamePattern.containsMatchIn(username)) {
            errorMessage += "Username contains invalid characters!\n"
        }
    }

    fun mainCheck(mail: String) {
        if (mail.isEmpty()) {
            errorMessage += "Empty mail field!\n"
        }

        if (!mailPattern.matches(mail)) {
            errorMessage += "Invalid email address!\n"
        }
    }

    fun passwordCheck(password: String, passwordAgain: String) {
        if (password.length < 6) {
            errorMessage += "Password is short or empty!\n"
        }

        if (passwordPattern.containsMatchIn(password)) {
            errorMessage += "Invalid password!\n"
        }

        if (passwordAgain != password) {
            errorMessage += "Different passwords!\n"
        }
    }

    fun termsCheck(terms: Boolean) {
        if (!terms) {
            errorMessage += "Check user agreement!"
        }
    }

    fun getErrorMessage(): String {
        return errorMessage
    }
}
package com.example.allamvizsga_2020_21.Firebase

import com.example.allamvizsga_2020_21.Firebase.Listeners.LogInListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener


class FirebaseAccountManager {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var authStateListener: AuthStateListener

    fun signUp(email: String, password: String, successListener: SuccessListener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            successListener.onSuccess()
        }.addOnFailureListener(successListener::onFail)
    }

    fun logIn(email: String, password: String, successListener: SuccessListener) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            successListener.onSuccess()
        }.addOnFailureListener(successListener::onFail)
    }

    fun setNewPassword(email: String, successListener: SuccessListener) {
        firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener {
            successListener.onSuccess()
        }.addOnFailureListener(successListener::onFail)
    }

    fun isLoggedIn(logInListener: LogInListener) {
        authStateListener = AuthStateListener {
            if (it.currentUser != null) {
                logInListener.onLogIn()
            } else {
                logInListener.onLogOut()
            }
        }
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener)
    }

    fun stopListening() {
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener)
    }
}
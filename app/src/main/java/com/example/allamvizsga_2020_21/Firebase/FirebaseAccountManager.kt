package com.example.allamvizsga_2020_21.Firebase

import com.google.firebase.auth.FirebaseAuth

class FirebaseAccountManager {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    fun signIn(email: String, password: String, successListener: SuccessListener) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
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
        authStateListener = FirebaseAuth.AuthStateListener {
            fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
                val firebaseUser = FirebaseAuth.getInstance().currentUser
                if (firebaseUser != null) {
                    logInListener.onLogIn()
                } else {
                    logInListener.onLogOut()
                }
            }
        }
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener)
    }

    fun stopListening() {
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener)
    }
}
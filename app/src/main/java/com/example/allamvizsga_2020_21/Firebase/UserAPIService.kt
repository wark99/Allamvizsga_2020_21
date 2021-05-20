package com.example.allamvizsga_2020_21.Firebase

import com.example.allamvizsga_2020_21.Firebase.Data.UserData
import com.example.allamvizsga_2020_21.Firebase.Listeners.CurrentUserListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.DataSnapshotListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot

object UserAPIService {

    fun writeUserData(userData: UserData, successListener: SuccessListener) {
        val userPath = "user/" + FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseOperations.writeToDatabase(userPath, userData, successListener)
    }

    fun readUserData(currentUserListener: CurrentUserListener) {
        val userPath = "user/" + FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseOperations.readFromDatabase(userPath, object : DataSnapshotListener {
            override fun onDataSnapshotReady(dataSnapshot: DataSnapshot) {
                val userData = dataSnapshot.getValue(UserData::class.java)!!
                currentUserListener.onSuccess(userData)
            }

            override fun onError() {
                currentUserListener.onError()
            }
        })
    }
}
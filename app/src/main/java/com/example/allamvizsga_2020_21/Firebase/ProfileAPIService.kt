package com.example.allamvizsga_2020_21.Firebase

import com.example.allamvizsga_2020_21.Firebase.Data.ProfileData
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener
import com.google.firebase.auth.FirebaseAuth

object ProfileAPIService {

    fun writeProfileData(profileData: ProfileData, successListener: SuccessListener) {
        val profilePath = "profile/" + FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseWriteRead.writeToDatabaseRandomPosition(profilePath, profileData, successListener)
    }
}
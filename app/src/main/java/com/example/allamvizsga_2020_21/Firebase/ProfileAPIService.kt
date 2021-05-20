package com.example.allamvizsga_2020_21.Firebase

import com.example.allamvizsga_2020_21.Firebase.Data.ProfileData
import com.example.allamvizsga_2020_21.Firebase.Listeners.DataSnapshotListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.ProfileListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import java.util.*

object ProfileAPIService {

    fun writeProfileData(profileData: ProfileData, successListener: SuccessListener) {
        val profilePath = "profile/" + FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseOperations.writeToDatabaseRandomPosition(profilePath, profileData, successListener)
    }

    fun readProfileData(profileListener: ProfileListener) {
        val path = "profile/" + FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseOperations.readFromDatabase(path, object : DataSnapshotListener {
            override fun onDataSnapshotReady(dataSnapshot: DataSnapshot) {
                val profileList = arrayListOf<ProfileData>()
                for (item in dataSnapshot.children) {
                    val profileData: ProfileData = item.getValue(ProfileData::class.java)!!
                    profileList.add(profileData)
                }

                profileListener.onReadSuccess(profileList)
            }

            override fun onError() {
                profileListener.onError()
            }
        })
    }

    fun removeData(removableData: ArrayList<ProfileData>, successListener: SuccessListener) {
        if(removableData.isEmpty()){
            successListener.onSuccess()
        }
        val path = "profile/" + FirebaseAuth.getInstance().currentUser!!.uid
        for (person in removableData){
            val imagePath=FirebaseAuth.getInstance().currentUser!!.uid+"/"+person.personName
            FirebaseOperations.removeFromStorage(imagePath, object:SuccessListener{
                override fun onSuccess() {
                    FirebaseOperations.removeFromDatabase(path, person, successListener)
                }

                override fun onFail(exception: Exception) {
                    successListener.onFail(exception)
                }
            })
        }
    }
}
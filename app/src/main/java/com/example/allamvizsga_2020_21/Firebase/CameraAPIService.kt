package com.example.allamvizsga_2020_21.Firebase

import com.example.allamvizsga_2020_21.Firebase.Listeners.DataSnapshotListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.LivePictureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot

object CameraAPIService {

    fun readLiveCamera(livePictureListener: LivePictureListener) {
        val path = "camera/" + FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseOperations.readFromDatabase(path, object : DataSnapshotListener {
            override fun onDataSnapshotReady(dataSnapshot: DataSnapshot) {
                val cameraList = arrayListOf<String>()
                for (item in dataSnapshot.children) {
                    val cameraData = item.value.toString()
                    cameraList.add(cameraData)
                }
                livePictureListener.onReadSuccess(cameraList)
            }

            override fun onError() {
                livePictureListener.onError()
            }
        })
    }
}
package com.example.allamvizsga_2020_21.Firebase

import com.example.allamvizsga_2020_21.Firebase.Data.PairCameraData
import com.example.allamvizsga_2020_21.Firebase.Listeners.CameraPoolListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.DataSnapshotListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.LivePictureListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener
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

    fun getCameraPool(cameraPoolListener: CameraPoolListener) {
        val path = "camera_pool"
        FirebaseOperations.readFromDatabase(path, object : DataSnapshotListener {
            override fun onDataSnapshotReady(dataSnapshot: DataSnapshot) {
                val cameraPoolList = arrayListOf<PairCameraData>()
                for (item in dataSnapshot.children) {
                    val pairCameraData: PairCameraData = item.getValue(PairCameraData::class.java)!!
                    cameraPoolList.add(pairCameraData)
                }
                cameraPoolListener.onReadSuccess(cameraPoolList)
            }

            override fun onError() {
                cameraPoolListener.onError()
            }
        })
    }

    fun connectCamera(pairCameraData: PairCameraData, successListener: SuccessListener) {
        val path =
            "camera/" + FirebaseAuth.getInstance().currentUser!!.uid + '/' + pairCameraData.cameraName.split(
                '#'
            )[1]
        FirebaseOperations.writeToDatabase(
            path,
            pairCameraData.cameraPicture,
            object : SuccessListener {
                override fun onSuccess() {
                    successListener.onSuccess()
                }

                override fun onFail(exception: Exception) {
                    successListener.onFail(exception)
                }

            })
    }

    fun disconnectCamera(pairCameraData: PairCameraData, successListener: SuccessListener) {
        val path =
            "camera/" + FirebaseAuth.getInstance().currentUser!!.uid + '/' + pairCameraData.cameraName.split(
                '#'
            )[1]
        FirebaseOperations.removeFromDatabase(path, object : SuccessListener {
            override fun onSuccess() {
                successListener.onSuccess()
            }

            override fun onFail(exception: Exception) {
                successListener.onFail(exception)
            }
        })
    }
}
package com.example.allamvizsga_2020_21.Firebase

import com.example.allamvizsga_2020_21.Firebase.Data.CameraSetting
import com.example.allamvizsga_2020_21.Firebase.Data.PairCameraData
import com.example.allamvizsga_2020_21.Firebase.Listeners.*
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

    fun updateSettings(
        pairCameraData: PairCameraData,
        settingData: CameraSetting,
        successListener: SuccessListener
    ) {
        val path =
            "settings/" + FirebaseAuth.getInstance().currentUser!!.uid + '/' + pairCameraData.cameraName.split(
                '#'
            )[1]
        FirebaseOperations.writeToDatabase(path, settingData, object : SuccessListener {
            override fun onSuccess() {
                successListener.onSuccess()
            }

            override fun onFail(exception: Exception) {
                successListener.onFail(exception)
            }
        })
    }

    fun getCameraSettings(cameraSettingListener: CameraSettingListener) {
        val path = "settings/" + FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseOperations.readFromDatabase(path, object : DataSnapshotListener {
            override fun onDataSnapshotReady(dataSnapshot: DataSnapshot) {
                val settingList = arrayListOf<CameraSetting>()
                for (item in dataSnapshot.children) {
                    val settingData: CameraSetting = item.getValue(CameraSetting::class.java)!!
                    settingList.add(settingData)
                }

                cameraSettingListener.onReadSuccess(settingList)
            }

            override fun onError() {
                cameraSettingListener.onError()
            }
        })
    }

    fun defaultCameraSettings(
        cameraList: ArrayList<PairCameraData>,
        settingList: ArrayList<CameraSetting>,
        successListener: SuccessListener
    ) {
        for (i in 0 until cameraList.size) {
            updateSettings(cameraList[i], settingList[i], object : SuccessListener {
                override fun onSuccess() {
                    if (i == cameraList.size - 1) {
                        successListener.onSuccess()
                    }
                }

                override fun onFail(exception: Exception) {
                    successListener.onFail(exception)
                }
            })
        }
    }
}
package com.example.allamvizsga_2020_21.Firebase.Listeners

import com.example.allamvizsga_2020_21.Firebase.Data.PairCameraData

interface CameraPoolListener {
    fun onReadSuccess(cameraList: ArrayList<PairCameraData>)
    fun onError()
}
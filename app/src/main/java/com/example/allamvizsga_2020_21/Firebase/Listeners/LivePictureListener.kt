package com.example.allamvizsga_2020_21.Firebase.Listeners

interface LivePictureListener {
    fun onReadSuccess(cameraList: ArrayList<String>)
    fun onError()
}
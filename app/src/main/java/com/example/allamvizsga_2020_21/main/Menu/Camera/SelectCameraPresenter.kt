package com.example.allamvizsga_2020_21.main.Menu.Camera

import com.example.allamvizsga_2020_21.Firebase.CameraAPIService
import com.example.allamvizsga_2020_21.Firebase.Listeners.LivePictureListener

class SelectCameraPresenter(view: SelectCameraContract.View) :
    SelectCameraContract.Presenter(view) {

    override fun loadCameras() {
        CameraAPIService.readLiveCamera(object : LivePictureListener {
            override fun onReadSuccess(cameraList: ArrayList<String>) {
                view!!.camerasLoaded(cameraList)
            }

            override fun onError() {
                view!!.loadingError()
            }

        })
    }

    override fun connectionError() {
        view!!.loadingError()
    }
}
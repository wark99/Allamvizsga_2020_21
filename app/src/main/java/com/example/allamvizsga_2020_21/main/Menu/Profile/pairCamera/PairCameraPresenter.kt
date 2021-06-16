package com.example.allamvizsga_2020_21.main.Menu.Profile.pairCamera

import com.example.allamvizsga_2020_21.Firebase.CameraAPIService
import com.example.allamvizsga_2020_21.Firebase.Data.PairCameraData
import com.example.allamvizsga_2020_21.Firebase.Listeners.CameraPoolListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener

class PairCameraPresenter(view: PairCameraContract.View) : PairCameraContract.Presenter(view) {
    override fun loadCameras() {
        CameraAPIService.getCameraPool(object : CameraPoolListener {
            override fun onReadSuccess(cameraList: ArrayList<PairCameraData>) {
                view!!.cameraLoaded(cameraList)
            }

            override fun onError() {
                view!!.loadingError()
            }
        })
    }

    override fun connectionError() {
        view!!.loadingError()
    }

    override fun connectCamera(pairCameraData: PairCameraData) {
        CameraAPIService.connectCamera(pairCameraData.cameraPicture, object : SuccessListener {
            override fun onSuccess() {
                view!!.connectionSuccess()
            }

            override fun onFail(exception: Exception) {
                view!!.connectionFail(exception.message!!)
            }
        })
    }

    override fun disconnectCamera(pairCameraData: PairCameraData) {
        CameraAPIService.disconnectCamera(pairCameraData, object : SuccessListener {
            override fun onSuccess() {

            }

            override fun onFail(exception: Exception) {

            }
        })
    }
}
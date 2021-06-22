package com.example.allamvizsga_2020_21.main.Menu.Profile.pairCamera

import com.example.allamvizsga_2020_21.Firebase.CameraAPIService
import com.example.allamvizsga_2020_21.Firebase.Data.CameraSetting
import com.example.allamvizsga_2020_21.Firebase.Data.PairCameraData
import com.example.allamvizsga_2020_21.Firebase.Listeners.CameraPoolListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.CameraSettingListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener

class PairCameraPresenter(view: PairCameraContract.View) : PairCameraContract.Presenter(view) {
    override fun loadCameras() {
        CameraAPIService.getCameraPool(object : CameraPoolListener {

            override fun onReadSuccess(cameraList: ArrayList<PairCameraData>) {
                CameraAPIService.getCameraSettings(object : CameraSettingListener {

                    override fun onReadSuccess(settingList: ArrayList<CameraSetting>) {
                        if (settingList.size != cameraList.size) {

                            for (i in 0 until cameraList.size) {
                                settingList.add(CameraSetting())
                            }

                            CameraAPIService.defaultCameraSettings(
                                cameraList,
                                settingList,
                                object : SuccessListener {
                                    override fun onSuccess() {
                                        view!!.cameraLoaded(cameraList, settingList)
                                    }

                                    override fun onFail(exception: Exception) {
                                        view!!.loadingError()
                                    }
                                })
                        } else {
                            view!!.cameraLoaded(cameraList, settingList)
                        }
                    }

                    override fun onError() {
                        view!!.loadingError()
                    }
                })
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
        CameraAPIService.connectCamera(pairCameraData, object : SuccessListener {
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
                view!!.disconnectionSuccess()
            }

            override fun onFail(exception: Exception) {
                view!!.disconnectionFail(exception.message!!)
            }
        })
    }

    override fun saveSettings(
        cameraDataSet: ArrayList<PairCameraData>,
        settingsDataSet: ArrayList<CameraSetting>
    ) {
        for (i in 0 until cameraDataSet.size) {
            CameraAPIService.updateSettings(
                cameraDataSet[i],
                settingsDataSet[i],
                object : SuccessListener {
                    override fun onSuccess() {
                        if (i == cameraDataSet.size - 1) {
                            view!!.onSaveSuccess()
                        }
                    }

                    override fun onFail(exception: Exception) {
                        view!!.onSaveFail(exception.message.toString())
                    }
                })
        }
    }
}
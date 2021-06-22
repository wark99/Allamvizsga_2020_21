package com.example.allamvizsga_2020_21.main.Menu.Profile.pairCamera

import com.example.allamvizsga_2020_21.Firebase.Data.CameraSetting
import com.example.allamvizsga_2020_21.Firebase.Data.PairCameraData
import com.example.allamvizsga_2020_21.mvp.BasePresenter
import com.example.allamvizsga_2020_21.mvp.BaseView

interface PairCameraContract {

    interface View : BaseView {
        fun loadingError()
        fun cameraLoaded(
            pairCameraData: ArrayList<PairCameraData>,
            settingList: ArrayList<CameraSetting>
        )

        fun connectionSuccess()
        fun disconnectionSuccess()
        fun connectionFail(errorMessage: String)
        fun disconnectionFail(errorMessage: String)
        fun onSaveSuccess()
        fun onSaveFail(message: String)
    }

    abstract class Presenter(view: View) : BasePresenter<View>(view) {
        abstract fun loadCameras()
        abstract fun connectionError()
        abstract fun connectCamera(pairCameraData: PairCameraData)
        abstract fun disconnectCamera(pairCameraData: PairCameraData)
        abstract fun saveSettings(
            cameraDataSet: ArrayList<PairCameraData>,
            settingsDataSet: ArrayList<CameraSetting>
        )
    }
}
package com.example.allamvizsga_2020_21.main.Menu.Camera

import com.example.allamvizsga_2020_21.mvp.BasePresenter
import com.example.allamvizsga_2020_21.mvp.BaseView

interface SelectCameraContract {

    interface View : BaseView {
        fun camerasLoaded(cameraList: ArrayList<String>)
        fun loadingError()
    }

    abstract class Presenter(view: View) : BasePresenter<View>(view) {
        abstract fun loadCameras()
        abstract fun connectionError()
    }
}
package com.example.allamvizsga_2020_21.splash

import com.example.allamvizsga_2020_21.mvp.BasePresenter
import com.example.allamvizsga_2020_21.mvp.BaseView

interface SplashContract {
    interface View : BaseView {
        fun showLoginScree()
        fun showMainScreen()
    }

    abstract class Presenter(view: View) : BasePresenter<View>(view) {
        abstract fun showNextScreen()
    }
}
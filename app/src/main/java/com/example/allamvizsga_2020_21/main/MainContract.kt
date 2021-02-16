package com.example.allamvizsga_2020_21.main

import com.example.allamvizsga_2020_21.mvp.BasePresenter
import com.example.allamvizsga_2020_21.mvp.BaseView

interface MainContract {
    interface View : BaseView {
        fun onLogOut()
    }

    abstract class Presenter(view: View) : BasePresenter<View>(view) {
        abstract fun logOut()
    }
}
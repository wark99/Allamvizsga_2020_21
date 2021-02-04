package com.example.allamvizsga_2020_21

import com.example.allamvizsga_2020_21.mvp.BasePresenter
import com.example.allamvizsga_2020_21.mvp.BaseView

interface MainContract {

    interface View : BaseView {
    }

    abstract class Presenter(view: View) : BasePresenter<View>(view) {

    }
}
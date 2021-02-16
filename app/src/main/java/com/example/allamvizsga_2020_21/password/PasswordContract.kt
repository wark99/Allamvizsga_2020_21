package com.example.allamvizsga_2020_21.password

import com.example.allamvizsga_2020_21.mvp.BasePresenter
import com.example.allamvizsga_2020_21.mvp.BaseView

interface PasswordContract {
    interface View : BaseView {
        fun error(errorMessage: String)
        fun verified()
    }

    abstract class Presenter(view: View) : BasePresenter<View>(view) {
        abstract fun setNewPassword(mail: String)
    }
}
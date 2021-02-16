package com.example.allamvizsga_2020_21.login

import com.example.allamvizsga_2020_21.mvp.BasePresenter
import com.example.allamvizsga_2020_21.mvp.BaseView

class LoginContract {

    interface View : BaseView {
        fun error(errorMessage: String)
        fun verified()
    }

    abstract class Presenter(view: View) : BasePresenter<View>(view) {
        abstract fun signIn(mail: String, password: String)
    }
}
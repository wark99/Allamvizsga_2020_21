package com.example.allamvizsga_2020_21.registration

import com.example.allamvizsga_2020_21.mvp.BasePresenter
import com.example.allamvizsga_2020_21.mvp.BaseView

interface RegistrationContract {

    interface View : BaseView {
        fun error(errorMessage: String)
        fun verified()
        fun firebaseError(exception: String)
    }

    abstract class Presenter(view: View) : BasePresenter<View>(view) {
        abstract fun singUp(
            username: String,
            mail: String,
            password: String,
            passwordAgain: String,
            terms: Boolean
        )
    }
}
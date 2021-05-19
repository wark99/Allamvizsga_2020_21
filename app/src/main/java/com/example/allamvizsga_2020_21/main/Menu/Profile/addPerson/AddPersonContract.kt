package com.example.allamvizsga_2020_21.main.Menu.Profile.addPerson

import android.net.Uri
import com.example.allamvizsga_2020_21.mvp.BasePresenter
import com.example.allamvizsga_2020_21.mvp.BaseView

interface AddPersonContract {

    interface View : BaseView {
        fun uploadSuccess()
        fun uploadFail(error: String)
    }

    abstract class Presenter(view: View) : BasePresenter<View>(view) {
        abstract fun addPerson(selectedPicture: Uri, personName: String)
        abstract fun connectionError()
    }
}
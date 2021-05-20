package com.example.allamvizsga_2020_21.main.Menu.Profile.removePerson

import com.example.allamvizsga_2020_21.Firebase.Data.ProfileData
import com.example.allamvizsga_2020_21.mvp.BasePresenter
import com.example.allamvizsga_2020_21.mvp.BaseView

interface RemovePersonContract {

    interface View : BaseView {
        fun loadingSuccess(profiles: ArrayList<ProfileData>)
        fun loadingFail(error: String)
        fun savingSuccess()
    }

    abstract class Presenter(view: View) : BasePresenter<View>(view) {
        abstract fun loadPersons()
        abstract fun connectionError()
        abstract fun saveChanges(removableData: ArrayList<ProfileData>)
    }
}
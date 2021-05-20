package com.example.allamvizsga_2020_21.main.Menu.History

import com.example.allamvizsga_2020_21.Firebase.Data.HistoryData
import com.example.allamvizsga_2020_21.mvp.BasePresenter
import com.example.allamvizsga_2020_21.mvp.BaseView

interface HistoryContract {

    interface View : BaseView {
        fun historyLoaded(historyList: ArrayList<HistoryData>)
        fun loadingError()
    }

    abstract class Presenter(view: View) : BasePresenter<View>(view) {
        abstract fun loadHistory()
        abstract fun connectionError()
    }
}
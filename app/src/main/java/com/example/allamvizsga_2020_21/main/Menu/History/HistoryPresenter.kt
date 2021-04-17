package com.example.allamvizsga_2020_21.main.Menu.History

import com.example.allamvizsga_2020_21.Firebase.Data.HistoryData
import com.example.allamvizsga_2020_21.Firebase.HistoryAPIService
import com.example.allamvizsga_2020_21.Firebase.Listeners.HistoryListener

class HistoryPresenter(view: HistoryContract.View) : HistoryContract.Presenter(view) {

    override fun loadHistory() {
        HistoryAPIService.readHistory(object : HistoryListener {
            override fun onReadSuccess(historyList: ArrayList<HistoryData>) {
                view!!.historyLoaded(historyList)
            }

            override fun onError() {
                view!!.loadingError()
            }
        })
    }

    override fun networkError() {
        view!!.loadingError()
    }
}
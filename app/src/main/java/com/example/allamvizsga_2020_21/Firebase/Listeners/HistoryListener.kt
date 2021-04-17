package com.example.allamvizsga_2020_21.Firebase.Listeners

import com.example.allamvizsga_2020_21.Firebase.Data.HistoryData

interface HistoryListener {
    fun onReadSuccess(historyList: ArrayList<HistoryData>)
    fun onError()
}
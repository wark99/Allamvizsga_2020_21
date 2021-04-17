package com.example.allamvizsga_2020_21.Firebase

import com.example.allamvizsga_2020_21.Firebase.Data.HistoryData
import com.example.allamvizsga_2020_21.Firebase.Listeners.DataSnapshotListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.HistoryListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot

object HistoryAPIService {

    fun readHistory(historyListener: HistoryListener) {
        val path = "history/" + FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseWriteRead.readFromDatabase(path, object : DataSnapshotListener {
            override fun onDataSnapshotReady(dataSnapshot: DataSnapshot) {
                val historyList = arrayListOf<HistoryData>()
                for (item in dataSnapshot.children) {
                    val historyData: HistoryData = item.getValue(HistoryData::class.java)!!
                    historyList.add(historyData)
                }
                historyListener.onReadSuccess(historyList)
            }

            override fun onError() {
                historyListener.onError()
            }
        })
    }
}
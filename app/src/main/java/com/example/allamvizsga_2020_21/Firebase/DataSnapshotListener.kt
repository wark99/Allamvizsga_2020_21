package com.example.allamvizsga_2020_21.Firebase

import com.google.firebase.database.DataSnapshot

interface DataSnapshotListener {
    fun onDataSnapshotReady(dataSnapshot: DataSnapshot)
    fun onError()
}
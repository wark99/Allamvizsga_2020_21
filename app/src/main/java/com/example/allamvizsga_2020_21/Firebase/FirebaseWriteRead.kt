package com.example.allamvizsga_2020_21.Firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseWriteRead {

    private var instance: FirebaseWriteRead? = null
    private val databaseReference = FirebaseDatabase.getInstance().reference

    fun getInstance(): FirebaseWriteRead? {
        if (instance == null) {
            instance = FirebaseWriteRead()
        }
        return instance
    }

    fun writeToDatabase(path: String, structure: Any, successListener: SuccessListener) {
        databaseReference.child(path).setValue(structure).addOnSuccessListener {
            successListener.onSuccess()
        }.addOnFailureListener(successListener::onFail)
    }

    fun readFromDatabase(path: String, dataSnapshotListener: DataSnapshotListener) {
        databaseReference.child(path).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataSnapshotListener.onDataSnapshotReady(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                dataSnapshotListener.onError()
            }
        })
    }
}
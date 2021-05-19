package com.example.allamvizsga_2020_21.Firebase

import android.net.Uri
import com.example.allamvizsga_2020_21.Firebase.Listeners.DataSnapshotListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask


object FirebaseWriteRead {

    private val databaseReference = FirebaseDatabase.getInstance().reference

    fun writeToDatabase(path: String, structure: Any, successListener: SuccessListener) {
        databaseReference.child(path).setValue(structure).addOnSuccessListener {
            successListener.onSuccess()
        }.addOnFailureListener(successListener::onFail)
    }

    fun writeToDatabaseRandomPosition(path: String, structure: Any, successListener: SuccessListener) {
        databaseReference.child(path).push().setValue(structure).addOnSuccessListener {
            successListener.onSuccess()
        }.addOnFailureListener(successListener::onFail)
    }

    fun readFromDatabase(path: String, dataSnapshotListener: DataSnapshotListener) {
        databaseReference.child(path).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataSnapshotListener.onDataSnapshotReady(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                dataSnapshotListener.onError()
            }
        })
    }

    fun uploadImage(path:String, image: Uri, imageName: String, successListener: SuccessListener) {
        val storageReference = FirebaseStorage.getInstance().reference.child(path)

        val uploadTask: UploadTask = storageReference.putFile(image)
        uploadTask.addOnSuccessListener {
            successListener.onSuccess()
        }.addOnFailureListener {
            successListener.onFail(uploadTask.exception!!)
        }
    }
}
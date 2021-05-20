package com.example.allamvizsga_2020_21.Firebase

import android.net.Uri
import com.example.allamvizsga_2020_21.Firebase.Data.ProfileData
import com.example.allamvizsga_2020_21.Firebase.Listeners.DataSnapshotListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.UploadSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask


object FirebaseOperations {

    private val databaseReference = FirebaseDatabase.getInstance().reference

    fun writeToDatabase(path: String, structure: Any, successListener: SuccessListener) {
        databaseReference.child(path).setValue(structure).addOnSuccessListener {
            successListener.onSuccess()
        }.addOnFailureListener(successListener::onFail)
    }

    fun writeToDatabaseRandomPosition(
        path: String,
        structure: Any,
        successListener: SuccessListener
    ) {
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

    fun uploadImage(path: String, image: Uri, successListener: UploadSuccessListener) {
        val storageReference = FirebaseStorage.getInstance().reference.child(path)

        val uploadTask: UploadTask = storageReference.putFile(image)
        uploadTask.addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener {
                successListener.onSuccess(it)
            }
        }.addOnFailureListener {
            successListener.onFail(uploadTask.exception!!)
        }
    }

    fun removeFromDatabase(path: String, person: ProfileData, successListener: SuccessListener) {
        databaseReference.child(path).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val item: ProfileData = data.getValue(ProfileData::class.java)!!
                    if (item.personName == person.personName) {
                        data.ref.removeValue()
                    }
                }
                successListener.onSuccess()
            }

            override fun onCancelled(error: DatabaseError) {
                successListener.onFail(error.toException())
            }
        })
    }

    fun removeFromStorage(path: String, successListener: SuccessListener) {
        val storageReference = FirebaseStorage.getInstance().reference.child(path)

        storageReference.delete().addOnSuccessListener {
            successListener.onSuccess()
        }.addOnFailureListener {
            successListener.onFail(Exception("Failed to remove!"))
        }
    }
}
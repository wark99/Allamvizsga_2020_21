package com.example.allamvizsga_2020_21.main.Menu.Profile.addPerson

import android.net.Uri
import com.example.allamvizsga_2020_21.Firebase.Data.ProfileData
import com.example.allamvizsga_2020_21.Firebase.FirebaseOperations
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.UploadSuccessListener
import com.example.allamvizsga_2020_21.Firebase.ProfileAPIService
import com.example.allamvizsga_2020_21.Firebase.UserInputCheck
import com.google.firebase.auth.FirebaseAuth

class AddPersonPresenter(view: AddPersonContract.View) : AddPersonContract.Presenter(view) {

    override fun addPerson(selectedPicture: Uri, personName: String) {

        val userInputCheck = UserInputCheck("")

        userInputCheck.imageCheck(selectedPicture.toString())
        userInputCheck.nameCheck(personName)

        val errorMessage = userInputCheck.getErrorMessage()

        if (errorMessage.isNotEmpty()) {
            view!!.uploadFail(errorMessage)
        } else {
            val path: String = FirebaseAuth.getInstance().currentUser!!.uid + "/" + personName
            FirebaseOperations.uploadImage(
                path,
                selectedPicture,
                object : UploadSuccessListener {
                    override fun onSuccess(uri: Uri) {
                        ProfileAPIService.writeProfileData(
                            ProfileData(personName, uri.toString()),
                            object : SuccessListener {
                                override fun onSuccess() {
                                    view!!.uploadSuccess()
                                }

                                override fun onFail(exception: Exception) {
                                    view!!.uploadFail(exception.message.toString())
                                }

                            })
                    }

                    override fun onFail(exception: Exception) {
                        view!!.uploadFail(exception.message.toString())
                    }
                })
        }
    }

    override fun connectionError() {
        view!!.uploadFail("Network Error")
    }
}
package com.example.allamvizsga_2020_21.main.Menu.Profile.removePerson

import com.example.allamvizsga_2020_21.Firebase.Data.ProfileData
import com.example.allamvizsga_2020_21.Firebase.Listeners.ProfileListener
import com.example.allamvizsga_2020_21.Firebase.Listeners.SuccessListener
import com.example.allamvizsga_2020_21.Firebase.ProfileAPIService

class RemovePersonPresenter(view: RemovePersonContract.View) :
    RemovePersonContract.Presenter(view) {

    override fun loadPersons() {
        ProfileAPIService.readProfileData(object : ProfileListener {
            override fun onReadSuccess(profileList: ArrayList<ProfileData>) {
                view!!.loadingSuccess(profileList)
            }

            override fun onError() {
                view!!.loadingFail("Loading Error!")
            }
        })
    }

    override fun connectionError() {
        view!!.loadingFail("Network Error!")
    }

    override fun saveChanges(removableData: ArrayList<ProfileData>) {
        ProfileAPIService.removeData(removableData, object : SuccessListener {
            override fun onSuccess() {
                view!!.savingSuccess()
            }

            override fun onFail(exception: Exception) {
                view!!.loadingFail(exception.message.toString())
            }
        })
    }
}
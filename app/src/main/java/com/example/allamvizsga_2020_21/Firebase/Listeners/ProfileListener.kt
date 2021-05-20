package com.example.allamvizsga_2020_21.Firebase.Listeners

import com.example.allamvizsga_2020_21.Firebase.Data.ProfileData

interface ProfileListener {
    fun onReadSuccess(profileList: ArrayList<ProfileData>)
    fun onError()
}
package com.example.allamvizsga_2020_21.Firebase.Listeners

import com.example.allamvizsga_2020_21.Firebase.Data.UserData

interface CurrentUserListener {
    fun onSuccess(userData: UserData)
    fun onError()
}
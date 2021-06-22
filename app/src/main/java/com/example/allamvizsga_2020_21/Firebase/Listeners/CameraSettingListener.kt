package com.example.allamvizsga_2020_21.Firebase.Listeners

import com.example.allamvizsga_2020_21.Firebase.Data.CameraSetting

interface CameraSettingListener {
    fun onReadSuccess(settingList: ArrayList<CameraSetting>)
    fun onError()
}
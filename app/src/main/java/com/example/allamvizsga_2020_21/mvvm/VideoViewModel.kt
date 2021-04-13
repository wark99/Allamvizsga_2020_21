package com.example.allamvizsga_2020_21.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VideoViewModel : ViewModel() {

    val data = MutableLiveData<String>()

    fun data(item: String) {
        data.value = item
    }
}
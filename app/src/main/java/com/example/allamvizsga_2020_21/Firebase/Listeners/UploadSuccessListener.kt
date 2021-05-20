package com.example.allamvizsga_2020_21.Firebase.Listeners

import android.net.Uri

interface UploadSuccessListener {
    fun onSuccess(uri: Uri)
    fun onFail(exception: Exception)
}
package com.example.allamvizsga_2020_21

import android.content.Context
import android.net.ConnectivityManager

class ConnectionChecker(private var requireContext: Context) {

    fun isConnected(): Boolean {
        val connectivityManager =
            requireContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo

        return activeNetwork?.isConnectedOrConnecting == true
    }
}
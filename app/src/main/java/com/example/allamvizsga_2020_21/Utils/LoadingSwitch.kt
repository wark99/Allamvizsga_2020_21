package com.example.allamvizsga_2020_21.Utils

import android.view.View

class LoadingSwitch {
    fun showLoading(viewToHide: View, viewToShow: View) {
        viewToHide.visibility = View.INVISIBLE
        viewToShow.visibility = View.VISIBLE
    }

    fun stopLoading(viewToHide: View, viewToShow: View) {
        viewToHide.visibility = View.INVISIBLE
        viewToShow.visibility = View.VISIBLE
    }
}
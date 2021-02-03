package com.example.allamvizsga_2020_21.mvp

abstract class BasePresenter<V : BaseView?>(view: V) {
    protected var view: V?
    fun onDetached() {
        view = null
    }

    init {
        this.view = view
    }
}
package com.fatonhoti.flags

import android.app.Application


class MyApplication : Application() {

    companion object {
        private lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getInstance() : MyApplication {
        return instance
    }
}
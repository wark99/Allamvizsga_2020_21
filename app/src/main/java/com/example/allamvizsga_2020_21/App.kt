package com.example.allamvizsga_2020_21

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

public class App : Application() {

    private val CHANNEL_ID = "notificationServiceChannel"

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Notification Channel",
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(serviceChannel)
    }
}
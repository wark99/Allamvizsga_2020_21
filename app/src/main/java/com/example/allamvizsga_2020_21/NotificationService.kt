package com.example.allamvizsga_2020_21

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class NotificationService : Service() {
    private val CHANNEL_ID = "notificationServiceChannel"

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivities(this, 0, arrayOf(notificationIntent), 0)

        val notification =
            NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("Security Service")
                .setContentText("Alert!").setSmallIcon(R.mipmap.app_icon)
                .setContentIntent(pendingIntent).build()

        startForeground(1, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
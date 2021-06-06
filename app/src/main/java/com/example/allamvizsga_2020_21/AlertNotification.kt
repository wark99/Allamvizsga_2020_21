package com.example.allamvizsga_2020_21

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlertNotification() {

    private val channelID = "channel_id_example_101"
    private val notificationID = 101

    fun createNotificationChannel(requiredContext: Context) {
        val name = "PromoSec"
        val descriptionText = "Alert"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            requiredContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun sendNotification(requiredContext: Context) {
        val intent = Intent(requiredContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(requiredContext, 0, intent, 0)

        val bitmap = BitmapFactory.decodeResource(
            requiredContext.applicationContext.resources,
            R.mipmap.app_icon
        )

        val builder = NotificationCompat.Builder(requiredContext, channelID)
            .setSmallIcon(R.mipmap.app_icon)
            .setContentTitle("PromoSec")
            .setContentText("Alert")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setLargeIcon(bitmap)

        with(NotificationManagerCompat.from(requiredContext)) {
            notify(notificationID, builder.build())
        }
    }
}
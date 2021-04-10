package com.example.allamvizsga_2020_21

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NotificationRunnable().run()
    }

    inner class NotificationRunnable : Runnable {

        override fun run() {
            startService()
        }
    }

    private fun startService() {
        val serviceIntent = Intent(this, NotificationService::class.java)

        startForegroundService(serviceIntent)
    }
}
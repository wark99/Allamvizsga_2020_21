package com.example.allamvizsga_2020_21.JobScheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.example.allamvizsga_2020_21.AlertNotification
import com.google.firebase.auth.FirebaseAuth
import java.io.OutputStream
import java.net.Socket
import java.util.*

class NetworkJobService : JobService() {
    private var jobScheduled = false

    private val TAG = "NetworkJobService"

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Job started")
        alarmCheck(params)

        return true
    }

    private fun alarmCheck(params: JobParameters?) {
        Thread(Runnable {
            Log.d(TAG, "run")
            client("192.168.100.100", 8080)
            Log.d(TAG, "Job finished")
            jobFinished(params, true)
            Log.d("NetworkJobService", "Job scheduled")
        }).start()
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Job cancelled before completion")
        jobScheduled = true

        return true
    }

    private fun client(address: String, port: Int) {
        val connection = Socket(address, port)
        val writer: OutputStream = connection.getOutputStream()
        val message = "/" + FirebaseAuth.getInstance().currentUser!!.uid
        writer.write(message.toByteArray())

        val reader = Scanner(connection.getInputStream())
        var input = ""

        while (reader.hasNextLine()) {
            input += reader.nextLine()
            Log.d("NetworkJobService", "Message from server: " + input)

            if (input == "ALERT") {
                val alert = AlertNotification()
                alert.createNotificationChannel(applicationContext)
                alert.sendNotification(applicationContext)
                Log.d("NetworkJobService", "Notify")
                break
            }

            input = ""
        }

        reader.close()
        writer.close()
        connection.close()
    }
}
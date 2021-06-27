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

    private lateinit var connection: Socket
    private lateinit var writer: OutputStream
    private lateinit var reader: Scanner

    private val TAG = "NetworkJobService"

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Job started")

        Thread(Runnable {
            Log.d(TAG, "Job running")
            try {
                client("192.168.100.102", 8080)
            } catch (e: Exception) {
                Log.d(TAG, "Client Exception")
            }
            Log.d(TAG, "Job finished")
            jobFinished(params, true)
            Log.d(TAG, "Job scheduled")
        }).start()

        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Thread(Runnable {
            try {
                writer.close()
            } catch (e: Exception) {
                Log.d("NetworkJobService", "Writer Exception")
            }

            try {
                reader.close()
            } catch (e: Exception) {
                Log.d("NetworkJobService", "Reader Exception")
            }

            try {
                connection.close()
            } catch (e: Exception) {
                Log.d("NetworkJobService", "Socket Exception")
            }
        }).start()

        Log.d(TAG, "Job cancelled before completion")
        jobScheduled = true

        return true
    }

    private fun client(address: String, port: Int) {
        connection = Socket(address, port)
        writer = connection.getOutputStream()

        val message = "/" + FirebaseAuth.getInstance().currentUser!!.uid
        writer.write((message.length.toString() + message).toByteArray())

        reader = Scanner(connection.getInputStream())
        while (reader.hasNextLine()) {
            val input = reader.nextLine()
            Log.d(TAG, input)
            when (input) {
                "VIOLENCE" -> {
                    sendAlert("VIOLENCE DETECTED!")
                    break
                }
                "UNKNOWN" -> {
                    sendAlert("UNKNOWN PERSON DETECTED!")
                    break
                }
                "FEAR" -> {
                    sendAlert("FEAR DETECTED!")
                    break
                }
                "ANGRY" -> {
                    sendAlert("ANGER DETECTED")
                    break
                }
            }
            if (input.split(": ")[1] != FirebaseAuth.getInstance().currentUser!!.uid) {
                break
            }
        }
    }

    private fun sendAlert(message: String) {
        val alert = AlertNotification()
        alert.createNotificationChannel(applicationContext)
        alert.sendNotification(applicationContext, message)
        Log.d(TAG, "Notify")
    }
}
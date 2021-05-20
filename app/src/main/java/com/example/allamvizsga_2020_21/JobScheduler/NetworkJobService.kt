package com.example.allamvizsga_2020_21.JobScheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

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
            for (i in 0..9) {
                Log.d(TAG, "run: $i")
                if (jobScheduled) {
                    return@Runnable
                }
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            Log.d(TAG, "Job finished")
            jobFinished(params, true)
        }).start()
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Job cancelled before completion")
        jobScheduled = true

        return true
    }
}
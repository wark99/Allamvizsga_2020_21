package com.example.allamvizsga_2020_21.main

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context.JOB_SCHEDULER_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.addCallback
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.allamvizsga_2020_21.AlertNotification
import com.example.allamvizsga_2020_21.JobScheduler.NetworkJobService
import com.example.allamvizsga_2020_21.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import java.io.OutputStream
import java.net.Socket
import java.util.*


class MainFragment : Fragment(), MainContract.View {

    private val presenter: MainContract.Presenter = MainPresenter(this)

    private val JOB_ID = 100

    private lateinit var camerasButton: ImageButton
    private lateinit var historyButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var logOutButton: ImageButton
    private lateinit var armingSwitch: SwitchCompat

    private lateinit var navController: NavController

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var connection: Socket
    private lateinit var writer: OutputStream
    private lateinit var reader: Scanner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onResume() {
        super.onResume()

        val currentActivity = requireActivity()

        sharedPreferences =
            currentActivity.getSharedPreferences("com.example.allamvizsga_2020_21", MODE_PRIVATE)

        camerasButton = currentActivity.findViewById(R.id.CameraImageButton)
        historyButton = currentActivity.findViewById(R.id.HistoryImageButton)
        profileButton = currentActivity.findViewById(R.id.ProfileImageButton)
        logOutButton = currentActivity.findViewById(R.id.LogOutImageButton)
        armingSwitch = currentActivity.findViewById(R.id.armingSwitch)

        armingSwitch.isChecked = sharedPreferences.getBoolean("service_status", false)

        navController = findNavController()

        camerasButton.setOnClickListener {
            navController.navigate(R.id.to_camera_selection_from_main)
        }

        historyButton.setOnClickListener {
            navController.navigate(R.id.to_history_from_main)
        }

        profileButton.setOnClickListener {
            navController.navigate(R.id.to_profile_from_main)
        }

        logOutButton.setOnClickListener {
            sharedPreferences.edit().putBoolean("service_status", false).apply()
            cancelJob()
            Dispatchers.IO.run {
                presenter.logOut()
            }
        }

        armingSwitch.setOnClickListener {
            jobManager()
        }

        currentActivity.onBackPressedDispatcher.addCallback(this) {
            currentActivity.finishAffinity()
        }
    }

    override fun onLogOut() {
        navController.navigate(R.id.to_login_from_main)
    }

    private fun jobManager() {
        sharedPreferences.edit().putBoolean("service_status", armingSwitch.isChecked).apply()
        if (armingSwitch.isChecked) {
            scheduleJob()
        } else {
            cancelJob()
        }
    }

    private fun scheduleJob() {
        val componentName = ComponentName(requireContext(), NetworkJobService::class.java)
        val jobInfo =
            JobInfo.Builder(JOB_ID, componentName).setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true).setPeriodic(1000).build()
        val jobScheduler = requireActivity().getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = jobScheduler.schedule(jobInfo)

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("NetworkJobService", "Job scheduled")

            Thread(Runnable {
                try {
                    client("192.168.100.101", 8080)
                } catch (e: Exception) {
                    Log.d("NetworkJobService", "Exception")
                }
            }).start()

        } else {
            Log.d("NetworkJobService", "Job scheduling failed")
        }
    }

    private fun cancelJob() {
        val jobScheduler = requireActivity().getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(JOB_ID)
        Log.d("NetworkJobService", "Job canceled")

        Thread(Runnable {
            try {
                reader.close()
            } catch (e: Exception) {
                Log.d("NetworkJobService", "Exception")
            }

            try {
                writer.close()
            } catch (e: Exception) {
                Log.d("NetworkJobService", "Exception")
            }

            try {
                connection.close()
            } catch (e: Exception) {
                Log.d("NetworkJobService", "Exception")
            }
        }).start()
    }

    private fun client(address: String, port: Int) {
        connection = Socket(address, port)
        writer = connection.getOutputStream()

        val message = "/" + FirebaseAuth.getInstance().currentUser!!.uid
        writer.write((message.length.toString() + message).toByteArray())

        Log.d("NetworkJobService", "Message: $message")

        reader = Scanner(connection.getInputStream())
        while (reader.hasNextLine()) {
            val input = reader.nextLine()
            Log.d("NetworkJobService", input)
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
        alert.createNotificationChannel(requireContext().applicationContext)
        alert.sendNotification(requireContext().applicationContext, message)
        Log.d("NetworkJobService", "Notify")
    }
}
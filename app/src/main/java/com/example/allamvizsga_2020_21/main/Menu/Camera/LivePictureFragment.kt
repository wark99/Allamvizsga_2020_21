package com.example.allamvizsga_2020_21.main.Menu.Camera

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.allamvizsga_2020_21.R

class LivePictureFragment : Fragment() {

    private lateinit var videoView: VideoView
    private lateinit var mediaController: MediaController
    private val onlineSource =
        Uri.parse("https://firebasestorage.googleapis.com/v0/b/allamvizsga-b617a.appspot.com/o/Cat%20pushed%20dumpster.mp4?alt=media&token=973683a3-283e-4b40-8e57-cf90e933924e")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_live_video, container, false)

        videoView = view.findViewById(R.id.liveVideo)
        mediaController = MediaController(requireContext())
        mediaController.setMediaPlayer(videoView)
        videoView.setMediaController(mediaController)

        return view
    }

    private fun initializePlayer() {
        videoView.setVideoURI(onlineSource)
        videoView.start()
    }

    private fun releasePlayer() {
        videoView.stopPlayback()
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        videoView.pause()
    }


}
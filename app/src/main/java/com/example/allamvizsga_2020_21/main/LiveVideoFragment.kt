package com.example.allamvizsga_2020_21.main

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.allamvizsga_2020_21.R

class LiveVideoFragment : Fragment() {

    private lateinit var videoView: VideoView
    private lateinit var mediaController: MediaController
    private val onlineSource =
        Uri.parse("https://cdn.api.video/vod/vi3pfGrhpRkGPTJkZOhdHmJ6/mp4/720/source.mp4")

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
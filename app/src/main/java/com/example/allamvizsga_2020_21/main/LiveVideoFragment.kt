package com.example.allamvizsga_2020_21.main

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

    private lateinit var videoPlayer: VideoView
    private lateinit var mediaController: MediaController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_video, container, false)
    }

    override fun onResume() {
        super.onResume()

        val currentActivity = requireActivity()

        videoPlayer = currentActivity.findViewById(R.id.liveVideo)
        mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoPlayer)

        val onlineUri = Uri.parse("https://cdn.api.video/vod/vi3m7zwazDTZ6IFdnlQ1xHTJ/mp4/720/source.mp4")
        videoPlayer.setMediaController(mediaController)
        videoPlayer.setVideoURI(onlineUri)
        videoPlayer.requestFocus()
        videoPlayer.start()
    }
}
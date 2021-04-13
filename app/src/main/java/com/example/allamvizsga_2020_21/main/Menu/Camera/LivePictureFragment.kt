package com.example.allamvizsga_2020_21.main.Menu.Camera

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.allamvizsga_2020_21.R
import com.example.allamvizsga_2020_21.mvvm.VideoViewModel

class LivePictureFragment : Fragment() {
    private lateinit var viewModel: VideoViewModel

    private lateinit var videoView: VideoView
    private lateinit var mediaController: MediaController
    private lateinit var onlineSource: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_video, container, false)
    }

    override fun onResume() {
        super.onResume()

        videoView = requireActivity().findViewById(R.id.liveVideo)
        mediaController = MediaController(requireContext())
        mediaController.setMediaPlayer(videoView)
        videoView.setMediaController(mediaController)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(VideoViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.data.observe(viewLifecycleOwner, {
            val videoUri = viewModel.data.value
            onlineSource = Uri.parse(videoUri)
            initializePlayer()
        })
    }

    private fun initializePlayer() {
        videoView.setVideoURI(onlineSource)
        videoView.start()
    }
}
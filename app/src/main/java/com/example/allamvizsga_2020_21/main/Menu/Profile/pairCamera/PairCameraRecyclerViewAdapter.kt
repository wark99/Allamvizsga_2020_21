package com.example.allamvizsga_2020_21.main.Menu.Profile.pairCamera

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.allamvizsga_2020_21.Firebase.Data.CameraSetting
import com.example.allamvizsga_2020_21.Firebase.Data.PairCameraData
import com.example.allamvizsga_2020_21.R

class PairCameraRecyclerViewAdapter(
    private var cameraDataset: ArrayList<PairCameraData>,
    private var settingsDataset: ArrayList<CameraSetting>,
    private var onConnectClickListener: OnConnectClickListener,
    private var onDisconnectClickListener: OnDisconnectClickListener
) : RecyclerView.Adapter<PairCameraRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), OnConnectClickListener,
        OnDisconnectClickListener {
        var cameraThumbnail: ImageView = view.findViewById(R.id.cameraImageView)
        var cameraName: TextView = view.findViewById(R.id.pairCameraNameTextView)
        var connectButton: Button = view.findViewById(R.id.pairCameraAddButton)
        var disconnectButton: Button = view.findViewById(R.id.pairCameraRemoveButton)

        var faceSwitch: SwitchCompat = view.findViewById(R.id.faceSwitch)
        var emotionSwitch: SwitchCompat = view.findViewById(R.id.emotionSwitch)
        var voiceSwitch: SwitchCompat = view.findViewById(R.id.voiceSwitch)
        var violenceSwitch: SwitchCompat = view.findViewById(R.id.violenceSwitch)

        init {
            connectButton.setOnClickListener {
                onConnectClick(adapterPosition)
            }
            disconnectButton.setOnClickListener {
                onDisconnectClick(adapterPosition)
            }
            faceSwitch.setOnClickListener {
                settingsDataset[adapterPosition].face = !settingsDataset[adapterPosition].face
                faceSwitch.isChecked = settingsDataset[adapterPosition].face
            }
            emotionSwitch.setOnClickListener {
                settingsDataset[adapterPosition].emotion = !settingsDataset[adapterPosition].emotion
                emotionSwitch.isChecked = settingsDataset[adapterPosition].emotion
            }
            voiceSwitch.setOnClickListener {
                settingsDataset[adapterPosition].voice = !settingsDataset[adapterPosition].voice
                voiceSwitch.isChecked = settingsDataset[adapterPosition].voice
            }
            violenceSwitch.setOnClickListener {
                settingsDataset[adapterPosition].violence =
                    !settingsDataset[adapterPosition].violence
                violenceSwitch.isChecked = settingsDataset[adapterPosition].violence
            }
        }

        override fun onConnectClick(position: Int) {
            if (position != RecyclerView.NO_POSITION) {
                onConnectClickListener.onConnectClick(position)
            }
        }

        override fun onDisconnectClick(position: Int) {
            if (position != RecyclerView.NO_POSITION) {
                onDisconnectClickListener.onDisconnectClick(position)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_pair_camera, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.cameraThumbnail).load(cameraDataset[position].cameraPicture)
            .into(viewHolder.cameraThumbnail)
        viewHolder.cameraName.text = cameraDataset[position].cameraName
        viewHolder.faceSwitch.isChecked = settingsDataset[position].face
        viewHolder.emotionSwitch.isChecked = settingsDataset[position].emotion
        viewHolder.voiceSwitch.isChecked = settingsDataset[position].voice
        viewHolder.violenceSwitch.isChecked = settingsDataset[position].violence
    }

    override fun getItemCount(): Int {
        return cameraDataset.size
    }

    interface OnConnectClickListener {
        fun onConnectClick(position: Int)
    }

    interface OnDisconnectClickListener {
        fun onDisconnectClick(position: Int)
    }
}
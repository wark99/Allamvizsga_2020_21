package com.example.allamvizsga_2020_21.main.Menu.Profile.pairCamera

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.allamvizsga_2020_21.Firebase.Data.PairCameraData
import com.example.allamvizsga_2020_21.R

class PairCameraRecyclerViewAdapter(
    private var dataset: ArrayList<PairCameraData>,
    private var onConnectClickListener: OnConnectClickListener,
    private var onDisconnectClickListener: OnDisconnectClickListener
) : RecyclerView.Adapter<PairCameraRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), OnConnectClickListener,
        OnDisconnectClickListener {
        var cameraThumbnail: ImageView = view.findViewById(R.id.cameraImageView)
        var cameraName: TextView = view.findViewById(R.id.pairCameraNameTextView)
        var connectButton: Button = view.findViewById(R.id.pairCameraAddButton)
        var disconnectButton: Button = view.findViewById(R.id.pairCameraRemoveButton)

        init {
            connectButton.setOnClickListener {
                onConnectClick(adapterPosition)
            }
            disconnectButton.setOnClickListener {
                onDisconnectClick(adapterPosition)
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
        Glide.with(viewHolder.cameraThumbnail).load(dataset[position].cameraPicture)
            .into(viewHolder.cameraThumbnail)
        viewHolder.cameraName.text = dataset[position].cameraName
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    interface OnConnectClickListener {
        fun onConnectClick(position: Int)
    }

    interface OnDisconnectClickListener {
        fun onDisconnectClick(position: Int)
    }
}
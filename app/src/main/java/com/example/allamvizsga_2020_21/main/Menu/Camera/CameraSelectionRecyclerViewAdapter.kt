package com.example.allamvizsga_2020_21.main.Menu.Camera

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.allamvizsga_2020_21.R

class CameraSelectionRecyclerViewAdapter(
    private var dataset: ArrayList<String>,
    private var onClickListener: OnItemClickListener
) : RecyclerView.Adapter<CameraSelectionRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var cameraThumbnail: ImageView = view.findViewById(R.id.thumbnailImageButton)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onClickListener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_select_camera, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.cameraThumbnail).load(dataset[position])
            .into(viewHolder.cameraThumbnail)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
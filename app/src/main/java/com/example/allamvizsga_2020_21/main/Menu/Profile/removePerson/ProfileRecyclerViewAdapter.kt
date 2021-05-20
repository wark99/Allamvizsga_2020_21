package com.example.allamvizsga_2020_21.main.Menu.Profile.removePerson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.allamvizsga_2020_21.Firebase.Data.ProfileData
import com.example.allamvizsga_2020_21.R

class ProfileRecyclerViewAdapter(
    private var dataset: ArrayList<ProfileData>,
    private var onLongClickListener: OnItemLongClickListener
) : RecyclerView.Adapter<ProfileRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {
        var personImage: ImageView = view.findViewById(R.id.listItemProfile)
        var personName: TextView = view.findViewById(R.id.listItemName)

        init {
            view.setOnLongClickListener(this)
        }

        override fun onLongClick(view: View?): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onLongClickListener.onItemLongClick(position)
            }
            return true
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_profile, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.personImage).load(dataset[position].pictureURL)
            .into(viewHolder.personImage)
        viewHolder.personName.text = dataset[position].personName
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }
}
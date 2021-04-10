package com.example.allamvizsga_2020_21.main.Menu.Profile

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
    private var dataset: ArrayList<ProfileData>
) : RecyclerView.Adapter<ProfileRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var personImage: ImageView = view.findViewById(R.id.listItemProfile)
        var personName: TextView = view.findViewById(R.id.listItemName)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_profile, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.personImage).load(dataset[position]).into(viewHolder.personImage)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
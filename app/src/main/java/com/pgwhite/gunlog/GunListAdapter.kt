package com.pgwhite.gunlog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GunListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<GunListAdapter.GunViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var guns = emptyList<Gun>() // Cached copy of guns

    inner class GunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gunItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GunViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return GunViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GunViewHolder, position: Int) {
        val current = guns[position]
        val listText = current.mfr + " " + current.model
        holder.gunItemView.text = listText
    }

    internal fun setGuns(guns: List<Gun>) {
        this.guns = guns
        notifyDataSetChanged()
    }

    override fun getItemCount() = guns.size
}
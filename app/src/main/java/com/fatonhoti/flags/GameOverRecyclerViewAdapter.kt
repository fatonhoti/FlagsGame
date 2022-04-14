package com.fatonhoti.flags

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(items: MutableList<ListItem>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val listItems: MutableList<ListItem> = items

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.game_over_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvGame.text = listItems[position].game
        holder.tvDescription.text = listItems[position].description
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvGame: TextView = view.findViewById(R.id.tvGame)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
    }


}
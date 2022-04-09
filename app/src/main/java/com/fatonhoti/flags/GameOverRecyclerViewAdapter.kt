package com.fatonhoti.flags

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameOverRecyclerViewAdapter(items: MutableList<GameOverRecyclerViewListItem>) :
    RecyclerView.Adapter<GameOverRecyclerViewAdapter.ViewHolder>() {

    private val listItems: MutableList<GameOverRecyclerViewListItem> = items

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.game_over_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivFlag.setImageResource(listItems[position].flag)
        holder.tvAnswer.text = listItems[position].answer
        if(holder.tvAnswer.text == "Correct") {
            holder.tvAnswer.setTextColor(Color.parseColor("#43A047"))
        } else {
            holder.tvAnswer.setTextColor(Color.parseColor("#F44336"))
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivFlag: ImageView = view.findViewById(R.id.ivFlag)
        val tvAnswer: TextView = view.findViewById(R.id.tvAnswer)
    }


}
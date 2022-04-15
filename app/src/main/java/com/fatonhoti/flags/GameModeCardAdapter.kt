package com.fatonhoti.flags

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameModeCardAdapter(cards: MutableList<GameModeCard>) : RecyclerView.Adapter<GameModeCardAdapter.ViewHolder>() {

    private val cards = cards

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // val ivImage = view.findViewById<ImageView>(R.id.CardImage)
        val tvTitle: TextView = view.findViewById(R.id.CardTitle)
        val tvSecondaryText: TextView = view.findViewById(R.id.CardSecondaryText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.game_mode_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // holder.tvImage.setImageResource(cards[position].image)
        holder.tvTitle.text = cards[position].title
        holder.tvSecondaryText.text = cards[position].secondaryText
    }

    override fun getItemCount(): Int {
        return cards.size
    }

}
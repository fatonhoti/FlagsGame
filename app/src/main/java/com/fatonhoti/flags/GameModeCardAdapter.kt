package com.fatonhoti.flags

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameModeCardAdapter(private val cards: MutableList<GameModeCard>) : RecyclerView.Adapter<GameModeCardAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.CardImage)
        val tvTitle: TextView = view.findViewById(R.id.CardTitle)
        val tvSecondaryText: TextView = view.findViewById(R.id.CardSecondaryText)
        val btnStart: Button = view.findViewById(R.id.btnStart)
        val context: Context = view.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.game_mode_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivImage.setImageResource(cards[position].image)
        holder.tvTitle.text = cards[position].title
        holder.tvSecondaryText.text = cards[position].secondaryText
        holder.btnStart.setOnClickListener {
            val gameMode = holder.tvTitle.text
            val i = Intent(holder.context, GameRegionMenuActivity::class.java).also {
                it.putExtra("MODE", gameMode)
            }
            holder.context.startActivity(i)
            (holder.context as Activity).finish()
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }

}
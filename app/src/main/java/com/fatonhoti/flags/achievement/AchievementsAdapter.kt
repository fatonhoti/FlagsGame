package com.fatonhoti.flags.achievement

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fatonhoti.flags.R

class AchievementsAdapter(private val cards: MutableList<AchievementCard>) : RecyclerView.Adapter<AchievementsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAchievement: LinearLayout = view.findViewById(R.id.tvAchievement)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvProgress: TextView = view.findViewById(R.id.tvProgress)
    }

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.achievement_card, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card: AchievementCard = cards[position]
        if(card.completed == "true") {
            holder.tvAchievement.setBackgroundColor(ContextCompat.getColor(context,
                R.color.primaryColor
            ))
        }

        holder.tvTitle.text = card.title
        holder.tvDescription.text = card.description
        holder.tvDate.text = card.date
        holder.tvProgress.text = card.progress.toString() + "/" + card.limit.toString()
    }

    override fun getItemCount(): Int {
        return cards.size
    }

}
package com.fatonhoti.flags

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameOverResultCardAdapter(private val cards: MutableList<GameOverResultCard>) : RecyclerView.Adapter<GameOverResultCardAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivCountryFlag)
        val tvCountryName: TextView = view.findViewById(R.id.tvCountryName)
        val tvCorrectAnswer: TextView = view.findViewById(R.id.tvCorrect)
        val tvIncorrectAnswer: TextView = view.findViewById(R.id.tvIncorrect)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.game_over_result_card, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivImage.setImageResource(cards[position].image)
        holder.tvCountryName.text = cards[position].countryName
        holder.tvCorrectAnswer.text = "The correct answer is '" + cards[position].correctAnswer + "'"
        holder.tvIncorrectAnswer.text = "You answered '" + cards[position].incorrectAnswer + "'"
    }

    override fun getItemCount(): Int {
        return cards.size
    }

}
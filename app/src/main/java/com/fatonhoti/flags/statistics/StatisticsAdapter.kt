package com.fatonhoti.flags.statistics

import android.annotation.SuppressLint
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import com.fatonhoti.flags.R

class StatisticsAdapter(private val cards: MutableList<StatisticCard>) : RecyclerView.Adapter<StatisticsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvGameMode: TextView = view.findViewById(R.id.tvGameMode)
        val tvGamesPlayed: TextView = view.findViewById(R.id.tvGamesPlayed)
        val tvGuesses: TextView = view.findViewById(R.id.tvGuesses)
        val tvCorrect: TextView = view.findViewById(R.id.tvCorrect)
        val tvIncorrect: TextView = view.findViewById(R.id.tvIncorrect)
        val tvAverage: TextView = view.findViewById(R.id.tvAverage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.statistic_card, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(cards[position].gameMode == "General statistics") {
            holder.tvGameMode.text = SpannableStringBuilder().bold { append(cards[position].gameMode) }
        }else {
            holder.tvGameMode.text = SpannableStringBuilder()
                .bold{append("Game mode: ")}
                .append(cards[position].gameMode)
        }
        holder.tvGamesPlayed.text = SpannableStringBuilder()
            .bold{append("Games played: ")}
            .append(cards[position].gamesPlayed.toString())
        holder.tvGuesses.text = SpannableStringBuilder()
            .bold{append("Guessed: ")}
            .append(cards[position].guesses.toString())
        holder.tvCorrect.text = SpannableStringBuilder()
            .bold{append("Correct guesses: ")}
            .append(cards[position].correctGuesses.toString())
        holder.tvIncorrect.text = SpannableStringBuilder()
            .bold{append("Incorrect guesses: ")}
            .append(cards[position].incorrectGuesses.toString())
        holder.tvAverage.text = SpannableStringBuilder()
            .bold{append("Average correct: ")}
            .append(cards[position].averageCorrect.toString())
    }

    override fun getItemCount(): Int {
        return cards.size
    }

}
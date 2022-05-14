package com.fatonhoti.flags.statistics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatonhoti.flags.R
import com.fatonhoti.flags.achievement.AchievementsActivity

class StatisticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        // Create adapter cards
        val sharedPref = getSharedPreferences("statistics", MODE_PRIVATE)
        val cards = mutableListOf(
            // General statistics
            StatisticCard("General statistics",
                sharedPref.getInt("Total games played", 0),
                sharedPref.getInt("Total guesses", 0),
                sharedPref.getInt("Total correct guesses", 0),
                sharedPref.getInt("Total incorrect guesses", 0),
                sharedPref.getFloat("Average correct guesses", 0.0f)
            ),
            // Flags
            StatisticCard("Flags",
                sharedPref.getInt("Flag games played", 0),
                sharedPref.getInt("Flags guessed", 0),
                sharedPref.getInt("Correct flags guessed", 0),
                sharedPref.getInt("Incorrect flags guessed", 0),
                sharedPref.getFloat("Average correct guesses (flags)", 0.0f)
            ),
            // Capitals
            StatisticCard("Capitals",
                sharedPref.getInt("Capital games played", 0),
                sharedPref.getInt("Capitals guessed", 0),
                sharedPref.getInt("Correct capitals guessed", 0),
                sharedPref.getInt("Incorrect capitals guessed", 0),
                sharedPref.getFloat("Average correct guesses (capitals)", 0.0f)
            ),
            // Currencies
            StatisticCard("Currencies",
                sharedPref.getInt("Currency games played", 0),
                sharedPref.getInt("Currencies guessed", 0),
                sharedPref.getInt("Correct currencies guessed", 0),
                sharedPref.getInt("Incorrect currencies guessed", 0),
                sharedPref.getFloat("Average correct guesses (currencies)", 0.0f)
            ),
            // Languages
            StatisticCard("Languages",
                sharedPref.getInt("Language games played", 0),
                sharedPref.getInt("Languages guessed", 0),
                sharedPref.getInt("Correct languages guessed", 0),
                sharedPref.getInt("Incorrect languages guessed", 0),
                sharedPref.getFloat("Average correct guesses (languages)", 0.0f)
            ),
            // Continents
            StatisticCard("Continents",
                sharedPref.getInt("Continent games played", 0),
                sharedPref.getInt("Continents guessed", 0),
                sharedPref.getInt("Correct continents guessed", 0),
                sharedPref.getInt("Incorrect continents guessed", 0),
                sharedPref.getFloat("Average correct guesses (continents)", 0.0f)
            )
        )

        val recyclerView: RecyclerView = findViewById(R.id.rvStatistics)
        val adapter = StatisticsAdapter(cards)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = adapter

        val btnAchievements = findViewById<Button>(R.id.btnAchievements)
        btnAchievements.setOnClickListener {
            Intent(this, AchievementsActivity::class.java).also { startActivity(it) }
            // finish()
        }

    }

}
package com.fatonhoti.flags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class GameOverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val region = intent.getStringExtra("region")
        val tvRegion = findViewById<TextView>(R.id.tvRegion)
        tvRegion.text = "Region: $region"

        val countCorrect = intent.getIntExtra("correctGuesses", 0)
        val correctGuesses = findViewById<TextView>(R.id.tvGuessesCorrect)
        correctGuesses.text = countCorrect.toString()

        val countIncorrect = intent.getIntExtra("incorrectGuesses", 0)
        val incorrectGuesses = findViewById<TextView>(R.id.tvGuessesIncorrect)
        incorrectGuesses.text = countIncorrect.toString()

        val btnMainMenu = findViewById<Button>(R.id.btnMainMenu)
        btnMainMenu.setOnClickListener {
            finish()
        }

    }
}
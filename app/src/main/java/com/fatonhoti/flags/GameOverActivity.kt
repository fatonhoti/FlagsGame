package com.fatonhoti.flags

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameOverActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
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
            Intent(this, GameMenuActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}
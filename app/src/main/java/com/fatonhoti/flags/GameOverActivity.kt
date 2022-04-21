/*
MIT License

Copyright (c) 2022 Faton Hoti

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.fatonhoti.flags

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blongho.country_data.Country
import java.lang.Exception

class GameOverActivity : AppCompatActivity() {

    private fun buildCards(guesses: HashMap<Country, Pair<Boolean, String>>, gameMode: String) : MutableList<GameOverResultCard> {
        val items = mutableListOf<GameOverResultCard>()
        guesses.forEach {
            val image = it.key.flagResource
            val countryName = it.key.name
            var correctAnswer = ""
            when(gameMode) {
                "FLAGS" -> { correctAnswer = it.key.name }
                "CAPITALS" -> { correctAnswer = it.key.capital }
                "CURRENCIES" -> { correctAnswer = it.key.currency.name }
                "LANGUAGES" -> { correctAnswer = it.key.languages.split(",")[0] }
            }
            val incorrectAnswer = it.value.second
            items.add(GameOverResultCard(countryName, correctAnswer, incorrectAnswer, image))
        }
        return items
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        // Fetch the game data
        val guesses = intent.getSerializableExtra("guesses") as HashMap<Country, Pair<Boolean, String>>
        val gameMode = intent.getStringExtra("gameMode")!!

        // Build the cards
        val items = buildCards(guesses, gameMode)

        // Setup the RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.rvResults)
        val adapter = GameOverResultCardAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Display other information
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
            Intent(this, GameModesMenuActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}
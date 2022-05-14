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

package com.fatonhoti.flags.gameOver

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
import com.blongho.country_data.World
import com.fatonhoti.flags.*
import com.fatonhoti.flags.gameModes.GameModesMenuActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate


class GameOverActivity : AppCompatActivity() {

    private var db = MyApplication.applicationContext().getDbAchievements()

    @Suppress("UNCHECKED_CAST")
    private fun buildCards(guesses: HashMap<*, *>, gameMode: String) : MutableList<GameOverResultCard> {
        val items = mutableListOf<GameOverResultCard>()
        val defType = "drawable"
        val defPackage = "com.fatonhoti.flags"
        if(gameMode == "CONTINENTS") {
            (guesses as HashMap<World.Continent, String>).forEach {
                val flag: Int = when(it.key) {
                    World.Continent.EUROPE -> { resources.getIdentifier("europe", defType, defPackage) }
                    World.Continent.ASIA -> {resources.getIdentifier("asia", defType, defPackage) }
                    World.Continent.AFRICA -> { resources.getIdentifier("africa", defType, defPackage) }
                    World.Continent.NORTH_AMERICA -> { resources.getIdentifier("north_america", defType, defPackage) }
                    World.Continent.SOUTH_AMERICA -> { resources.getIdentifier("south_america", defType, defPackage) }
                    World.Continent.ANTARCTICA -> { resources.getIdentifier("antarctica", defType, defPackage) }
                    World.Continent.OCEANIA -> { resources.getIdentifier("oceania", defType, defPackage) }
                }
                items.add(GameOverResultCard(it.key.name.replace("_", " "), it.key.name.replace("_", " "), it.value.replace("_", " "), flag))
            }
        } else {
            (guesses as HashMap<Country, String>).forEach {
                var correctAnswer = ""
                when(gameMode) {
                    "FLAGS"      -> { correctAnswer = it.key.name }
                    "CAPITALS"   -> { correctAnswer = it.key.capital }
                    "CURRENCIES" -> { correctAnswer = it.key.currency.name }
                    "LANGUAGES"  -> { correctAnswer = it.key.languages.split(",")[0] }
                }
                items.add(GameOverResultCard(it.key.name, correctAnswer, it.value, it.key.flagResource))
            }
        }
        return items
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        // Fetch the game data
        val guesses = intent.getSerializableExtra("guesses") as HashMap<*, *>
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

        // Update statistics
        updateStatistics(gameMode, countCorrect, countIncorrect)

        // Update achievement progress
        updateAchievements(gameMode, countCorrect)

    }

    private fun updateStatistics(gameMode: String, countCorrect: Int, countIncorrect: Int) {
        val sharedPref = getSharedPreferences("statistics", MODE_PRIVATE)
        val editor = sharedPref.edit()

        var a = ""
        var b = ""
        var c = ""
        when(gameMode) {
            "FLAGS"      -> { a = "Flag"; b = "Flags"; c = "flags" }
            "CAPITALS"   -> { a = "Capital"; b = "Capitals"; c = "capitals" }
            "CURRENCIES" -> { a = "Currency"; b = "Currencies"; c = "currencies" }
            "LANGUAGES"  -> { a = "Language"; b = "Languages"; c = "languages" }
            "CONTINENTS" -> { a = "Continent"; b = "Continents"; c = "continents" }
        }

        // General
        val totalGamesPlayed = sharedPref.getInt("Total games played", 0)
        val totalGuesses = sharedPref.getInt("Total guesses", 0)
        val totalCorrectGuesses = sharedPref.getInt("Total correct guesses", 0)
        val totalIncorrectGuesses = sharedPref.getInt("Total incorrect guesses", 0)
        editor.putInt("Total games played", totalGamesPlayed + 1)
        editor.putInt("Total guesses", totalGuesses + countCorrect + countIncorrect)
        editor.putInt("Total correct guesses", totalCorrectGuesses + countCorrect)
        editor.putInt("Total incorrect guesses", totalIncorrectGuesses + countIncorrect)
        editor.putFloat("Average correct guesses", (totalCorrectGuesses + countCorrect) * 100.0f / (totalGuesses + countIncorrect + countIncorrect))

        // Game mode specific
        val gamesPlayed = sharedPref.getInt("$a games played", 0)
        val guesses = sharedPref.getInt("$b guessed", 0)
        val correctGuesses = sharedPref.getInt("Correct $c guessed", 0)
        val incorrectGuesses = sharedPref.getInt("Incorrect $c guessed", 0)
        editor.putInt("$a games played", gamesPlayed + 1)
        editor.putInt("$b guessed", guesses + countCorrect + countIncorrect)
        editor.putInt("Correct $c guessed", correctGuesses + countCorrect)
        editor.putInt("Incorrect $c guessed", incorrectGuesses + countIncorrect)
        editor.putFloat("Average correct guesses ($c)", (correctGuesses + countCorrect) * 100.0f / (guesses + countCorrect + countIncorrect))

        // Apply changes
        editor.apply()

    }

    @Suppress("UNCHECKED_CAST")
    private fun updateAchievements(gameMode: String, countCorrect: Int) {
        GlobalScope.launch {
            val achievements = db.getAllGameMode(gameMode)
            achievements.forEach {
                it.progress += countCorrect
                if(it.completed == "true") {
                    it.progress = it.limit
                } else if(it.completed == "false" && it.progress >= it.limit) {
                    it.completed = "true"
                    it.date = LocalDate.now().toString()
                    it.progress = it.limit
                }
                db.updateAchievement(it)
            }
        }
    }

}
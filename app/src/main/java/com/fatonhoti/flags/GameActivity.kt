package com.fatonhoti.flags

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blongho.country_data.Country
import com.blongho.country_data.World
import java.lang.Exception
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class GameActivity : AppCompatActivity() {

    private var correctGuesses = 0
    private var incorrectGuesses = 0
    private var usedCountries = HashSet<Country>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Fetch used views
        val ivFlagToGuess = findViewById<ImageView>(R.id.ivFlagToGuess)
        val tvCountriesLeft = findViewById<TextView>(R.id.tvCountriesLeft)
        val btnChoices = listOf<Button>(
            findViewById(R.id.btnChoiceOne),
            findViewById(R.id.btnChoiceTwo),
            findViewById(R.id.btnChoiceThree),
            findViewById(R.id.btnChoiceFour)
        )

        // ===== Fetch information from game lobby
        val max = intent.getIntExtra("MAX", 1)

        // ===== Fetch countries of the user selected region
        //val countries = intent.getSerializableExtra("COUNTRIES")
        //val countries = World.getCountriesFrom(World.Continent.ASIA)
        val countries = World.getAllCountries()

        // ===== Generate starting country

        // Select 'max' countries at random
        val selectedCountries = pickNRandomElements(countries, max)!!
        tvCountriesLeft.text = "Left: " + selectedCountries.size.toString()

        // Take one country at random to be the country to guess
        val startingCountry = pickCountry(selectedCountries)
        ivFlagToGuess.setImageResource(World.getFlagOf(startingCountry.id))

        // Take four countries at random to display as choices
        setChoices(btnChoices, countries, startingCountry)

        // ===== Setup event listeners
        btnChoices.forEach { btn ->
            btn.setOnClickListener {
                try {
                    val guess = btn.text.toString()
                    if(guess == startingCountry.name) {
                        // The user guessed the correct
                        correctGuesses++
                        // TODO: Make guess button green to indicate correct
                    } else {
                        // The user was incorrect
                        incorrectGuesses++
                        // TODO: Make guess button red to indicate incorrect
                    }

                    // If cycled through all
                    if(selectedCountries.isEmpty()) {
                        Intent(this, GameOverActivity::class.java).also {
                            it.putExtra("correctGuesses", correctGuesses)
                            it.putExtra("incorrectGuesses", incorrectGuesses)
                            it.putExtra("region", countries[0].continent)
                            startActivity(it)
                            finish()
                        }
                    } else {
                        // Set new flag to guess
                        val newCountry = pickCountry(selectedCountries)
                        ivFlagToGuess.setImageResource(World.getFlagOf(newCountry.id))

                        // Update count
                        tvCountriesLeft.text = "Left: ${selectedCountries.size}"

                        // Generate new choices
                        setChoices(btnChoices, countries, newCountry)
                    }
                } catch(e: Exception) {
                    Log.e("GAME", e.stackTraceToString())
                }
            }
        }
    }

    private fun pickCountry(selectedCountries: MutableList<Country>): Country {
        val country = selectedCountries[ThreadLocalRandom.current().nextInt(selectedCountries.size)]
        selectedCountries.remove(country)
        return country
    }

    private fun setChoices(btnChoices: List<Button>, countries: MutableList<Country>, country: Country) {
        val choices = pickNRandomElements(countries, 4)!!
        val randIndex = ThreadLocalRandom.current().nextInt(4)
        for(i in 0 until choices.size) {
            if (i == randIndex) {
                // At least one of the choices must be the actually correct one
                btnChoices[i].text = country.name
            } else {
                btnChoices[i].text = choices[i].name
            }
        }
    }

    private fun pickNRandomElements(list: MutableList<Country>, n: Int, r: Random): MutableList<Country>? {
        val length = list.size
        if (length < n) return null

        // We don't need to shuffle the whole list
        for (i in length - 1 downTo length - n) {
            Collections.swap(list, i, r.nextInt(i + 1))
        }
        val sublist = list.subList(length - n, length)

        // Make a unique copy
        val copy = mutableListOf<Country>()
        sublist.forEach {
            copy.add(it)
        }
        return copy

    }

    private fun pickNRandomElements(list: MutableList<Country>, n: Int) : MutableList<Country>? {
        return pickNRandomElements(list, n, ThreadLocalRandom.current())
    }

}
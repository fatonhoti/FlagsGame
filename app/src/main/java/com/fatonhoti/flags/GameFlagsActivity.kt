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
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blongho.country_data.Country
import com.blongho.country_data.World
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.collections.HashMap

class GameFlagsActivity : AppCompatActivity() {

    private var correctGuesses = 0
    private var incorrectGuesses = 0
    private lateinit var ivFlagToGuess: ImageView
    private lateinit var tvCountriesLeft: TextView
    private lateinit var btnChoices: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_flags)

        // Fetch used views
        ivFlagToGuess = findViewById(R.id.ivFlagToGuess)
        tvCountriesLeft = findViewById(R.id.tvCountriesLeft)
        btnChoices = listOf<Button>(
            findViewById(R.id.btnChoiceOne),
            findViewById(R.id.btnChoiceTwo),
            findViewById(R.id.btnChoiceThree),
            findViewById(R.id.btnChoiceFour)
        )

        // Start the game
        run()

    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Do you want to go back?")
            .setMessage("Going back will lose you the progress!")
            .setPositiveButton(android.R.string.ok) { _, _ -> finish()}
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .create().show()
    }

    @SuppressLint("SetTextI18n")
    private fun run() {

        // ===== Fetch information from game lobby
        @Suppress("UNCHECKED_CAST")
        val countries = intent.getSerializableExtra("COUNTRIES") as MutableList<Country>
        val max = intent.getIntExtra("MAX", 1)
        tvCountriesLeft.text = "Left:$max"

        // Select 'max' countries at random that the user will try to guess
        val selectedCountries = pickNRandomElements(countries, max)!!

        // Take one starting country at random to be the country to guess
        var countryToGuess = pickCountry(selectedCountries)
        ivFlagToGuess.setImageResource(World.getFlagOf(countryToGuess.id))

        // Take four countries at random to display as choices
        setChoices(countries, countryToGuess)

        // ===== Setup event listeners
        btnChoices.forEach { btn ->
            btn.setOnClickListener {
                val guess = btn.text.toString()
                if(guess == countryToGuess.name) {
                    correctGuesses++
                } else {
                    incorrectGuesses++
                }

                if(selectedCountries.isEmpty()) {
                    // All countries have been guessed
                    Intent(this, GameOverActivity::class.java).also {
                        it.putExtra("correctGuesses", correctGuesses)
                        it.putExtra("incorrectGuesses", incorrectGuesses)
                        it.putExtra("region", countries[0].continent)
                        startActivity(it)
                        finish()
                    }
                } else {
                    tvCountriesLeft.text = "Left: " + selectedCountries.size.toString()

                    // Set new flag to guess
                    countryToGuess = pickCountry(selectedCountries)
                    ivFlagToGuess.setImageResource(World.getFlagOf(countryToGuess.alpha2))

                    // Generate new choices
                    setChoices(countries, countryToGuess)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun pickCountry(selectedCountries: MutableList<Country>): Country {
        val country = selectedCountries[ThreadLocalRandom.current().nextInt(selectedCountries.size)]
        selectedCountries.remove(country)
        return country
    }

    private fun setChoices(countries: MutableList<Country>, country: Country) {
        val remaining = countries.filter { c -> c.name != country.name } as MutableList<Country>
        val choices = pickNRandomElements(remaining, 4)!!
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
        copy.shuffle()
        return copy

    }

    private fun pickNRandomElements(list: MutableList<Country>, n: Int) : MutableList<Country>? {
        return pickNRandomElements(list, n, ThreadLocalRandom.current())
    }

}
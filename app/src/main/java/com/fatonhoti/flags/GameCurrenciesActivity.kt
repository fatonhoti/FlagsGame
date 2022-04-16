package com.fatonhoti.flags

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.blongho.country_data.Country
import java.lang.Exception
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class GameCurrenciesActivity : AppCompatActivity() {

    private var correctGuesses = 0
    private var incorrectGuesses = 0
    private lateinit var ivFlagToGuess: ImageView
    private lateinit var tvCountriesLeft: TextView
    private lateinit var btnChoices: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_currencies)

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
        try {
            run()
        } catch(e: Exception) {
            Log.e("curr", e.toString())
            Log.e("curr", e.stackTraceToString())
        }

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

        // Select 'max' countries at random whose currencies the user will try to guess
        val selectedCountries = pickNRandomElements(countries, max)!!

        // Take one starting country at random to be the countries currency to guess
        var country = pickCountry(selectedCountries)
        ivFlagToGuess.setImageResource(country.flagResource)

        // Take four currencies at random to display as choices
        setChoices(countries, country)

        // ===== Setup event listeners
        btnChoices.forEach { btn ->
            btn.setOnClickListener {
                val guess = btn.text.toString()
                if(guess == country.currency.name) {
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
                    country = pickCountry(selectedCountries)
                    ivFlagToGuess.setImageResource(country.flagResource)

                    // Generate new choices
                    setChoices(countries, country)
                }
            }
        }

    }

    private fun getCurrencies(countries: MutableList<Country>, country: Country) : MutableList<String> {
        val currencies = countries.map { c -> c.currency.name }.distinct().toMutableList()
        currencies.remove(country.currency.name)
        return currencies.shuffled().take(4).toMutableList()
    }

    private fun pickCountry(selectedCapitals: MutableList<Country>): Country {
        val country = selectedCapitals[ThreadLocalRandom.current().nextInt(selectedCapitals.size)]
        selectedCapitals.remove(country)
        return country
    }

    private fun setChoices(countries: MutableList<Country>, country: Country) {
        val currencies = getCurrencies(countries, country)
        val randIndex = ThreadLocalRandom.current().nextInt(4)
        for(i in currencies.indices) {
            if (i == randIndex) {
                // At least one of the choices must be the actually correct one
                btnChoices[i].text = country.currency.name
            } else {
                btnChoices[i].text = currencies[i]
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
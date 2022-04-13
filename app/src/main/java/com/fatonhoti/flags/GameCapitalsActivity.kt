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
import com.blongho.country_data.World
import java.lang.Exception
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class GameCapitalsActivity : AppCompatActivity() {

    private var correctGuesses = 0
    private var incorrectGuesses = 0
    private lateinit var ivFlagToGuess: ImageView
    private lateinit var tvCountriesLeft: TextView
    private lateinit var btnChoices: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_capitals)

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
        val capitals = getCapitals(countries)
        val max = intent.getIntExtra("MAX", 1)
        tvCountriesLeft.text = "Left:$max"

        // Select 'max' countries at random that the user will try to guess
        val selectedCapitals = pickNRandomElements(capitals, max)!!

        // Take one starting country at random to be the country to guess
        var capitalToGuess = pickCountry(selectedCapitals)
        ivFlagToGuess.setImageResource(World.getCountryFrom(capitalToGuess).flagResource)

        // Take four countries at random to display as choices
        setChoices(capitals, capitalToGuess)

        // ===== Setup event listeners
        btnChoices.forEach { btn ->
            btn.setOnClickListener {
                val guess = btn.text.toString()
                if(guess == capitalToGuess) {
                    correctGuesses++
                } else {
                    incorrectGuesses++
                }

                if(selectedCapitals.isEmpty()) {
                    // All countries have been guessed
                    Intent(this, GameOverActivity::class.java).also {
                        it.putExtra("correctGuesses", correctGuesses)
                        it.putExtra("incorrectGuesses", incorrectGuesses)
                        it.putExtra("region", countries[0].continent)
                        startActivity(it)
                        finish()
                    }
                } else {
                    tvCountriesLeft.text = "Left: " + selectedCapitals.size.toString()

                    // Set new flag to guess
                    capitalToGuess = pickCountry(selectedCapitals)
                    ivFlagToGuess.setImageResource(World.getFlagOf(capitalToGuess))

                    // Generate new choices
                    setChoices(capitals, capitalToGuess)
                }
            }
        }

    }

    private fun getCapitals(countries: MutableList<Country>) : MutableList<String> {
        val capitals = mutableListOf<String>()
        countries.forEach {
            capitals.add(it.capital)
        }
        return capitals
    }

    private fun pickCountry(selectedCapitals: MutableList<String>): String {
        val capital = selectedCapitals[ThreadLocalRandom.current().nextInt(selectedCapitals.size)]
        selectedCapitals.remove(capital)
        return capital
    }

    private fun setChoices(capitals: MutableList<String>, capital: String) {
        val remaining = capitals.filter { c -> c != capital } as MutableList<String>
        val choices = pickNRandomElements(remaining, 4)!!
        val randIndex = ThreadLocalRandom.current().nextInt(4)
        for(i in 0 until choices.size) {
            if (i == randIndex) {
                // At least one of the choices must be the actually correct one
                btnChoices[i].text = capital
            } else {
                btnChoices[i].text = choices[i]
            }
        }
    }

    private fun pickNRandomElements(list: MutableList<String>, n: Int, r: Random): MutableList<String>? {
        val length = list.size
        if (length < n) return null

        // We don't need to shuffle the whole list
        for (i in length - 1 downTo length - n) {
            Collections.swap(list, i, r.nextInt(i + 1))
        }
        val sublist = list.subList(length - n, length)

        // Make a unique copy
        val copy = mutableListOf<String>()
        sublist.forEach {
            copy.add(it)
        }
        copy.shuffle()
        return copy

    }

    private fun pickNRandomElements(list: MutableList<String>, n: Int) : MutableList<String>? {
        return pickNRandomElements(list, n, ThreadLocalRandom.current())
    }

}
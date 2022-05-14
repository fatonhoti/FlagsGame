package com.fatonhoti.flags.gameModes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blongho.country_data.Country
import com.fatonhoti.flags.R
import com.fatonhoti.flags.gameOver.GameOverActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.concurrent.ThreadLocalRandom

class GameModeCurrenciesActivity: AppCompatActivity(), GameMode {

    private lateinit var name: String
    private lateinit var image: ImageView
    private lateinit var guessesLeft: TextView
    private lateinit var buttons: List<Button>
    private lateinit var countries: MutableList<Country>
    private lateinit var userGuesses: HashMap<Country, String>
    private var maxGuesses: Int = 0
    private var correct: Int = 0
    private var incorrect: Int = 0
    private var guessCounter: Int = 0

    private lateinit var selectedCountries: MutableList<Country>
    private lateinit var selectedCountry: Country

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_currencies)
        fetchDataFromActivity()
        initializeValues()
        run()
    }

    override fun onBackPressed() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.GoBackDialogTitle))
            .setMessage(resources.getString(R.string.GoBackDialogSupportingText))
            .setNegativeButton(resources.getString(R.string.GoBackDialogCancel)) { _, _ -> }
            .setPositiveButton(resources.getString(R.string.GoBackDialogYes)) { _, _ -> finish() }
            .show()
    }

    override fun run() {
        // Select 'maxGuesses' amount of countries at random
        selectedCountries = getNRandom(countries, maxGuesses)

        // Setup the event listeners of the choice buttons
        buttons.forEach { btn ->
            btn.setOnClickListener {
                checkGuess(btn.text.toString())
                next()
            }
        }

        // Start the first round
        next()
    }

    override fun checkGuess(guess: String) {
        if(guess == selectedCountry.currency.name) {
            correct++
            userGuesses[selectedCountry] = guess
        } else {
            incorrect++
            userGuesses[selectedCountry] = guess
        }
    }

    override fun next() {
        // Has the user guessed all the flags?
        if(guessCounter-- > 0) {
            // Select a country at random from the list of selected countries
            selectedCountry = selectedCountries.shuffled().take(1)[0]
            updateViews()
            selectedCountries.remove(selectedCountry)
        } else {
            // All flags have been guessed
            Intent(this, GameOverActivity::class.java).also {
                it.putExtra("correctGuesses", correct)
                it.putExtra("incorrectGuesses", incorrect)
                it.putExtra("region", countries[0].continent)
                it.putExtra("guesses", userGuesses)
                it.putExtra("gameMode", intent.getStringExtra("MODE"))
                startActivity(it)
                finish()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateViews() {
        // Update guesses left text
        guessesLeft.text = "Left: " + selectedCountries.size.toString()

        // Set the image to display the flag of the currently selected country
        image.setImageResource(selectedCountry.flagResource)

        // Set the choices for the user
        val choices = countries.filter { c -> c.currency.name != selectedCountry.currency.name }
                                .map { c -> c.currency.name }.distinct()
                                .shuffled().take(4).toMutableList()
        val randIndex = ThreadLocalRandom.current().nextInt(4)
        for(i in 0 until choices.size) {
            if (i == randIndex) {
                // At least one of the choices must be the actually correct one
                buttons[i].text = selectedCountry.currency.name
            } else {
                buttons[i].text = choices[i]
            }
        }

    }

    @SuppressLint("SetTextI18n")
    override fun initializeValues() {
        this.name = "CURRENCIES"
        this.image = findViewById(R.id.ivFlagToGuess)
        this.guessesLeft = findViewById(R.id.tvCountriesLeft)
        guessesLeft.text = "Left:$maxGuesses"
        this.buttons = listOf<Button>(
            findViewById(R.id.btnChoiceOne),
            findViewById(R.id.btnChoiceTwo),
            findViewById(R.id.btnChoiceThree),
            findViewById(R.id.btnChoiceFour)
        )
        userGuesses = HashMap()
        guessCounter = maxGuesses
        correct = 0
        incorrect = 0
    }

    override fun fetchDataFromActivity() {
        @Suppress("UNCHECKED_CAST")
        countries = intent.getSerializableExtra("COUNTRIES") as MutableList<Country>
        maxGuesses = intent.getIntExtra("MAX", 1)
    }

    private fun getNRandom(list: MutableList<Country>, n: Int) : MutableList<Country> {
        return list.shuffled().take(n).toMutableList()
    }

}
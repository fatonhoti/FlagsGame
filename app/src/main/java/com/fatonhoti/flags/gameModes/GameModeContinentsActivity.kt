package com.fatonhoti.flags.gameModes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blongho.country_data.World
import com.fatonhoti.flags.R
import com.fatonhoti.flags.gameOver.GameOverActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.concurrent.ThreadLocalRandom

class GameModeContinentsActivity: AppCompatActivity(), GameMode {

    private lateinit var name: String
    private lateinit var image: ImageView
    private lateinit var guessesLeft: TextView
    private lateinit var buttons: List<Button>
    private val userGuesses = HashMap<World.Continent, String>()
    private var correct: Int = 0
    private var incorrect: Int = 0
    private var guessCounter: Int = 7

    private val allContinents = mutableListOf(
        World.Continent.SOUTH_AMERICA,
        World.Continent.OCEANIA,
        World.Continent.NORTH_AMERICA,
        World.Continent.ANTARCTICA,
        World.Continent.AFRICA,
        World.Continent.ASIA,
        World.Continent.EUROPE
    )
    private lateinit var selectedContinents: MutableList<World.Continent>
    private lateinit var selectedContinent: World.Continent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_continents)
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
        selectedContinents = allContinents.toMutableList()

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
        if(guess == selectedContinent.name) {
            correct++
            userGuesses[selectedContinent] = guess
        } else {
            incorrect++
            userGuesses[selectedContinent] = guess
        }
    }

    override fun next() {
        // Has the user guessed all the flags?
        if(guessCounter-- > 0) {
            // Select a country at random from the list of selected countries
            selectedContinent = selectedContinents.shuffled().take(1)[0]
            updateViews()
            selectedContinents.remove(selectedContinent)
        } else {
            // All flags have been guessed
            Intent(this, GameOverActivity::class.java).also {
                it.putExtra("correctGuesses", correct)
                it.putExtra("incorrectGuesses", incorrect)
                it.putExtra("region", "Continents")
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
        guessesLeft.text = "Left: " + selectedContinents.size.toString()

        // Set the image to display the flag of the currently selected country
        val defType = "drawable"
        val defPackage = "com.fatonhoti.flags"
        val flag: Int = when(selectedContinent) {
            World.Continent.EUROPE -> {
                resources.getIdentifier("europe", defType, defPackage)
            }
            World.Continent.ASIA -> {
                resources.getIdentifier("asia", defType, defPackage)
            }
            World.Continent.AFRICA -> {
                resources.getIdentifier("africa", defType, defPackage)
            }
            World.Continent.NORTH_AMERICA -> {
                resources.getIdentifier("north_america", defType, defPackage)
            }
            World.Continent.SOUTH_AMERICA -> {
                resources.getIdentifier("south_america", defType, defPackage)
            }
            World.Continent.ANTARCTICA -> {
                resources.getIdentifier("antarctica", defType, defPackage)
            }
            World.Continent.OCEANIA -> {
                resources.getIdentifier("oceania", defType, defPackage)
            }
        }
        image.setImageResource(flag)

        // Set the choices for the user
        val choices = allContinents.filter { c -> c != selectedContinent}.shuffled().take(4).toMutableList()
        val randIndex = ThreadLocalRandom.current().nextInt(4)
        for(i in 0 until choices.size) {
            if (i == randIndex) {
                // At least one of the choices must be the actually correct one
                var name = selectedContinent.name
                if(name.contains("_")) {
                    name = name.replace("_", " ")
                }
                buttons[i].text = name
            } else {
                var name = choices[i].name
                if(name.contains("_")) {
                    name = name.replace("_", " ")
                }
                buttons[i].text = name
            }
        }

    }

    @SuppressLint("SetTextI18n")
    override fun initializeValues() {
        this.name = "CAPITALS"
        this.image = findViewById(R.id.ivFlagToGuess)
        this.guessesLeft = findViewById(R.id.tvCountriesLeft)
        guessesLeft.text = "Left: 7"
        this.buttons = listOf<Button>(
            findViewById(R.id.btnChoiceOne),
            findViewById(R.id.btnChoiceTwo),
            findViewById(R.id.btnChoiceThree),
            findViewById(R.id.btnChoiceFour)
        )
    }

    override fun fetchDataFromActivity() { }

}
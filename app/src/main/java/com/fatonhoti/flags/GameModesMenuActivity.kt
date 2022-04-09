package com.fatonhoti.flags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class GameModesMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_modes_menu)

        val btnFlags = findViewById<Button>(R.id.btnFlags)
        btnFlags.setOnClickListener {
            Intent(this, GameFlagsMenuActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

        val btnCapitals = findViewById<Button>(R.id.btnCapitals)
        btnCapitals.setOnClickListener {
            // TODO: Start "Capitals" game mode activity
            Toast.makeText(this, "This game mode has not been implemented yet!", Toast.LENGTH_SHORT).show()
        }

        val btnCurrencies = findViewById<Button>(R.id.btnCurrencies)
        btnCurrencies.setOnClickListener {
            // TODO: Start "Capitals" game mode activity
            Toast.makeText(this, "This game mode has not been implemented yet!", Toast.LENGTH_SHORT).show()
        }

        val btnGoBack = findViewById<Button>(R.id.btnGoBack)
        btnGoBack.setOnClickListener { finish() }

    }
}
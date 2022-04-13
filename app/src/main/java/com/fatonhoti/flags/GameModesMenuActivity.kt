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
            Intent(this, GameRegionMenuActivity::class.java).also {
                it.putExtra("MODE", "FLAG")
                startActivity(it)
                finish()
            }
        }

        val btnCapitals = findViewById<Button>(R.id.btnCapitals)
        btnCapitals.setOnClickListener {
            Intent(this, GameRegionMenuActivity::class.java).also {
                it.putExtra("MODE", "CAPITAL")
                startActivity(it)
                finish()
            }
        }

        val btnCurrencies = findViewById<Button>(R.id.btnCurrencies)
        btnCurrencies.setOnClickListener {
            Intent(this, GameRegionMenuActivity::class.java).also {
                it.putExtra("MODE", "CURRENCY")
                startActivity(it)
                finish()
            }
        }

        val btnGoBack = findViewById<Button>(R.id.btnGoBack)
        btnGoBack.setOnClickListener { finish() }

    }
}
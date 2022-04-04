package com.fatonhoti.flags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class GameMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_menu)

        val btnRegions = listOf<Button>(
            findViewById(R.id.btnWorld),
            findViewById(R.id.btnEurope),
            findViewById(R.id.btnAsia),
            findViewById(R.id.btnAfrica),
            findViewById(R.id.btnNorthAmerica),
            findViewById(R.id.btnSouthAmerica),
            findViewById(R.id.btnOceania)
        )
        btnRegions.forEach { btnView ->
            btnView.setOnClickListener() {
                Intent(this, GameLobbyActivity::class.java).also {
                    it.putExtra("REGION", btnView.text.toString())
                    startActivity(it)
                    finish()
                }
            }
        }

        val btnGoBack = findViewById<Button>(R.id.btnGoBack)
        btnGoBack.setOnClickListener {
            finish()
        }

    }

}
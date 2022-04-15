package com.fatonhoti.flags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameModesMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_modes_menu)

        val items = mutableListOf<GameModeCard>()
        items.add(GameModeCard("FLAGS", "Do you know all the flags?"))
        items.add(GameModeCard("CAPITALS", "What about the capitals?"))
        items.add(GameModeCard("CURRENCIES", "Surely you don't know the currencies..."))

        val recyclerView: RecyclerView = findViewById(R.id.rvCards)
        val adapter = GameModeCardAdapter(items)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }
}
package com.fatonhoti.flags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameModesMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_modes_menu)

        // TODO: Make buttons in each card in the RecyclerView actually clickable and reactive
        // TODO: Make cards in RecyclerView actually display suitable images

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
package com.fatonhoti.flags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameModesMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_modes_menu)

        val items = mutableListOf<GameModeCard>()

        val flagsImage = resources.getIdentifier("game_mode_flags_image", "drawable", "com.fatonhoti.flags")
        val flags = GameModeCard("FLAGS", "Do you know all the flags?", flagsImage)
        items.add(flags)

        val capitalsImage = resources.getIdentifier("game_mode_capitals_image", "drawable", "com.fatonhoti.flags")
        val capitals = GameModeCard("CAPITALS", "What about the capitals?", capitalsImage)
        items.add(capitals)

        val currenciesImage = resources.getIdentifier("game_mode_currencies_image", "drawable", "com.fatonhoti.flags")
        val currencies = GameModeCard("CURRENCIES", "Surely you don't know all the currencies...", currenciesImage)
        items.add(currencies)

        val languagesImage = resources.getIdentifier("game_mode_currencies_image", "drawable", "com.fatonhoti.flags")
        val languages = GameModeCard("LANGUAGES", "Surely you don't know all the currencies...", languagesImage)
        items.add(languages)

        val recyclerView: RecyclerView = findViewById(R.id.rvCards)
        val adapter = GameModeCardAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }
}
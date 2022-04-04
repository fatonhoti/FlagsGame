package com.fatonhoti.flags

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blongho.country_data.Country
import com.blongho.country_data.World
import java.lang.Exception
import java.util.ArrayList


class GameLobbyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_lobby)

        // Fetch all the countries of the region
        var region = intent.getStringExtra("REGION")!!.uppercase()
        val countries: MutableList<Country>
        if(region == "WORLD") {
            countries = World.getAllCountries()
        } else {
            val fst = region.substring(0,1)
            if (fst == "N" || fst == "S") {
                // Need to add "_" between "North/South America" so API understands string
                region = region.substring(0,5) + "_AMERICA"
            }
            countries = World.getCountriesFrom(World.Continent.valueOf(region))
        }

        // Fetch max flags the user wants to learn
        var flagCount = 1
        val tvFlagCount = findViewById<TextView>(R.id.tvFlagCount)
        val sbFlagCount = findViewById<SeekBar>(R.id.sbAmountFlags)
        sbFlagCount.max = countries.size
        sbFlagCount.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) { }
            override fun onStartTrackingTouch(seekBar: SeekBar) { }
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                flagCount = if(progress == 0) {
                    progress + 1
                } else {
                    progress
                }
                tvFlagCount.text = flagCount.toString()
            }
        })

        // Start game
        val btnStart = findViewById<Button>(R.id.btnGameLobbyStart)
        btnStart.setOnClickListener {
            Intent(this, GameActivity::class.java).also{
                try {
                    it.putParcelableArrayListExtra("COUNTRIES", ArrayList(countries))
                    it.putExtra("MAX", flagCount)
                    startActivity(it)
                    finish()
                } catch (e: Exception) {
                    Log.e("GameLobbyError", e.toString())
                }
            }
        }

    }

}
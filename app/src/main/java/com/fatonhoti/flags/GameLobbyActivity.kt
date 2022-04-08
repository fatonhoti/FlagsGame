/*
MIT License

Copyright (c) 2022 Faton Hoti

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.fatonhoti.flags

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blongho.country_data.Country
import com.blongho.country_data.World
import java.util.ArrayList


class GameLobbyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_lobby)

        // Fetch all the countries of the region
        val region = intent.getStringExtra("REGION")!!.uppercase()
        val countries = getCountries(region)

        // Fetch max flags the user wants to learn
        val tvFlagCount = findViewById<TextView>(R.id.tvFlagCount)
        tvFlagCount.text = (countries.size / 2).toString()

        val sbFlagCount = findViewById<SeekBar>(R.id.sbFlagCount)
        sbFlagCount.max = countries.size
        sbFlagCount.progress = countries.size / 2

        var flagCount = countries.size / 2
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
                it.putParcelableArrayListExtra("COUNTRIES", ArrayList(countries))
                it.putExtra("MAX", flagCount)
                startActivity(it)
                finish()
            }
        }

    }

    private fun getCountries(region: String) : MutableList<Country> {
        return if(region == "WORLD") {
            World.getAllCountries()
        } else {
            val fst = region.substring(0,1)
            if (fst == "N" || fst == "S") {
                // Need to add "_" between "North/South America" so API understands string
                val regionModified = region.substring(0,5) + "_AMERICA"
                World.getCountriesFrom(World.Continent.valueOf(regionModified))
            } else {
                World.getCountriesFrom(World.Continent.valueOf(region))
            }
        }
    }

}
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
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.blongho.country_data.Country
import com.blongho.country_data.World
import com.google.android.material.slider.Slider
import java.lang.Exception
import java.util.ArrayList


class GameLobbyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_lobby)

        try {
            // Fetch all the countries of the region
            val region = intent.getStringExtra("REGION")!!.uppercase()
            val countries = getCountries(region)
            Log.i("cap", "Countries size: " + countries.size.toString())

            var flagCount = countries.size / 2
            val sbCounter = findViewById<Slider>(R.id.sbMaterial)
            sbCounter.valueFrom = 1F
            sbCounter.valueTo = countries.size.toFloat()
            sbCounter.value = (countries.size / 2).toFloat()
            sbCounter.addOnChangeListener { _, value, _ ->
                flagCount = value.toInt()
            }

            // Start game
            val btnStart = findViewById<Button>(R.id.btnGameLobbyStart)
            btnStart.setOnClickListener {
                val i: Intent? = when (intent.getStringExtra("MODE")) {
                    "FLAGS" -> createIntent(GameModeFlagsActivity::class.java, countries, flagCount)
                    "CAPITALS" -> createIntent(GameModeCapitalsActivity::class.java, countries, flagCount)
                    "CURRENCIES" -> createIntent(GameModeCurrenciesActivity::class.java, countries, flagCount)
                    "LANGUAGES" -> createIntent(GameModeLanguagesActivity::class.java, countries, flagCount)
                    else -> null
                }

                if(i == null) {
                    // Should never occur
                    finish()
                }

                startActivity(i)
                finish()
            }
        } catch(e: Exception) {
            Log.e("MAT", e.toString())
        }

    }

    private fun createIntent(ActivityClass: Class<*>?, countries: MutableList<Country>, flagCount: Int) : Intent {
        return Intent(this, ActivityClass).also{
            it.putParcelableArrayListExtra("COUNTRIES", ArrayList(countries))
            it.putExtra("MAX", flagCount)
            it.putExtra("MODE", intent.getStringExtra("MODE"))
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
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
import android.content.res.Configuration
import android.media.session.MediaSession
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.blongho.country_data.World
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {

    suspend fun test() : MediaSession.Token {
        return suspendCoroutine {  }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //applicationContext.deleteDatabase("database-name")
        val achievementDao = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,"database-name"
            ).build().achievementDao()

        GlobalScope.launch {
            achievementDao.insertAll(Achievement("title1", "desc1", "date1", "false"))
            achievementDao.insertAll(Achievement("title2", "desc2", "date2", "true"))
            Log.i("database", achievementDao.getAll().toString())
            Log.i("database", achievementDao.getAllComplete().toString())
            Log.i("database", achievementDao.getAllIncomplete().toString())
        }

        // Set relevant theme depending on phone's mode (LIGHT or DARK)
        val currentNightMode = (resources.configuration.uiMode
                and Configuration.UI_MODE_NIGHT_MASK)
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                // Night mode is not active
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                // Night mode is active
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> { }
        }

        // Load in resources from "worldCountryData" API
        World.init(applicationContext)

        val btnStart = findViewById<Button>(R.id.mm_btnStart)
        btnStart.setOnClickListener {
            Intent(this, GameModesMenuActivity::class.java).also { startActivity(it) }
        }

        val btnAchievements = findViewById<Button>(R.id.btnAchievements)
        btnAchievements.setOnClickListener {
            Intent(this, AchievementsActivity::class.java).also { startActivity(it) }
        }

        val btnSettings = findViewById<Button>(R.id.btnSettings)
        btnSettings.setOnClickListener {
            Intent(this, SettingsActivity::class.java).also { startActivity(it) }
        }

    }
}
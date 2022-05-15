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

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {

    private var db = MyApplication.applicationContext().getDbAchievements()

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchDarkmode = findViewById<Switch>(R.id.switchDarkmode)
        switchDarkmode.isChecked = AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_YES
        switchDarkmode.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
        }

        val btnClearStatistics = findViewById<Button>(R.id.btnClearStatistics)
        btnClearStatistics.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.GoBackDialogTitle))
                .setMessage("Pressing YES will reset all statistics and achievements!")
                .setNegativeButton(resources.getString(R.string.GoBackDialogCancel)) { _, _ -> }
                .setPositiveButton(resources.getString(R.string.GoBackDialogYes)) { _, _ -> resetStatistics() }
                .show()
        }

    }

    private fun resetStatistics()  {

        // Reset statistics
        val sharedPref = getSharedPreferences("statistics", MODE_PRIVATE)
        val editor = sharedPref.edit()

        // General statistics
        editor.putInt("Total games played", 0)
        editor.putInt("Total guesses", 0)
        editor.putInt("Total correct guesses", 0)
        editor.putInt("Total incorrect guesses", 0)
        editor.putFloat("Average correct guesses", 0.0f)

        // Flags
        editor.putInt("Flag games played", 0)
        editor.putInt("Flags guessed", 0)
        editor.putInt("Correct flags guessed", 0)
        editor.putInt("Incorrect flags guessed", 0)
        editor.putFloat("Average correct guesses (flags)", 0.0f)

        // Capitals
        editor.putInt("Capital games played", 0)
        editor.putInt("Capitals guessed", 0)
        editor.putInt("Correct capitals guessed", 0)
        editor.putInt("Incorrect capitals guessed", 0)
        editor.putFloat("Average correct guesses (capitals)", 0.0f)

        // Currencies
        editor.putInt("Currency games played", 0)
        editor.putInt("Currencies guessed", 0)
        editor.putInt("Correct currencies guessed", 0)
        editor.putInt("Incorrect currencies guessed", 0)
        editor.putFloat("Average correct guesses (currencies)", 0.0f)

        // Languages
        editor.putInt("Language games played", 0)
        editor.putInt("Languages guessed", 0)
        editor.putInt("Correct languages guessed", 0)
        editor.putInt("Incorrect languages guessed", 0)
        editor.putFloat("Average correct guesses (languages)", 0.0f)

        // Continents
        editor.putInt("Continent games played", 0)
        editor.putInt("Continents guessed", 0)
        editor.putInt("Correct continents guessed", 0)
        editor.putInt("Incorrect continents guessed", 0)
        editor.putFloat("Average correct guesses (continents)", 0.0f)

        // Apply changes
        editor.apply()

        // Reset achievements
        lifecycleScope.launch(Dispatchers.IO) {
            val achievements = db.getAll()
            achievements.forEach {
                it.progress = 0
                it.completed = "false"
                it.date = "Not completed yet"
                db.updateAchievement(it)
            }
        }
        Toast.makeText(this, "All statistics and achievements have been reset.", Toast.LENGTH_LONG).show()
    }

}
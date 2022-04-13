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
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class GameRegionMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_region_menu)

        val gameMode = intent.getStringExtra("MODE")!!

        val btnRegions = listOf<Button>(
            findViewById(R.id.btnFlags),
            findViewById(R.id.btnCapitals),
            findViewById(R.id.btnCurrencies),
            findViewById(R.id.btnAfrica),
            findViewById(R.id.btnNorthAmerica),
            findViewById(R.id.btnSouthAmerica),
            findViewById(R.id.btnOceania)
        )
        btnRegions.forEach { btnView ->
            btnView.setOnClickListener {
                Intent(this, GameLobbyActivity::class.java).also {
                    it.putExtra("MODE", gameMode)
                    it.putExtra("REGION", btnView.text.toString())
                    startActivity(it)
                    finish()
                }
            }
        }

        val btnGoBack = findViewById<Button>(R.id.btnGoBack)
        btnGoBack.setOnClickListener { finish() }

    }

}
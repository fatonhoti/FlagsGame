package com.fatonhoti.flags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.blongho.country_data.World
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load in resources from API
        World.init(applicationContext)

        val btnStart = findViewById<Button>(R.id.mm_btnStart)
        btnStart.setOnClickListener() {
            startActivity(Intent(this, GameMenuActivity::class.java))
        }

        val btnSettings = findViewById<Button>(R.id.mm_btnSettings)
        btnSettings.setOnClickListener() {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

    }
}
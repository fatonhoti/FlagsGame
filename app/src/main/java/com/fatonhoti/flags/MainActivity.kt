package com.fatonhoti.flags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.blongho.country_data.World

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load in resources from API
        World.init(applicationContext)

        val btnStart = findViewById<Button>(R.id.mm_btnStart)
        btnStart.setOnClickListener {
            startActivity(Intent(this, GameMenuActivity::class.java))
        }

    }
}
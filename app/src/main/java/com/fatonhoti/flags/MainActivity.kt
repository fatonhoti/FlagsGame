package com.fatonhoti.flags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.blongho.country_data.World
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        World.init(applicationContext)
    }

    override fun onStart() {
        super.onStart()
        val countries = World.getAllCountries()
        val country = countries[Random().nextInt(countries.size)]

        // Set the name of the country
        val countryName = findViewById<TextView>(R.id.tvCountryName)
        countryName.text = country.name

        // Set the image
        val flag = findViewById<ImageView>(R.id.main_menu_flag)
        flag.setImageResource(World.getFlagOf(country.id))
    }

}
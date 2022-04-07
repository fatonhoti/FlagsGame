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

class SettingsActivity : AppCompatActivity() {

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
            Toast.makeText(this, "This function has not been implemented yet.", Toast.LENGTH_SHORT).show()
        }

        val btnIntegrity = findViewById<Button>(R.id.btnIntegrity)
        btnIntegrity.setOnClickListener { openDialog() }

    }

    private fun openDialog() {
        IntegrityDialog().show(supportFragmentManager, "Integrity Dialog")
    }

}
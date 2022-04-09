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
import android.app.AlertDialog
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

        val btnAck = findViewById<Button>(R.id.btnAck)
        btnAck.setOnClickListener {
            val ackMessage = """
                The API used to fetch all the information of the different continents and countries is "worldCountryData".
                
                Check it out at "https://github.com/blongho/worldCountryData".
            """.trimIndent()
            openDialog("Acknowledgements", ackMessage)
        }

        val btnIntegrity = findViewById<Button>(R.id.btnIntegrity)
        btnIntegrity.setOnClickListener {
            val integrityMessage = """
                The application "Flags" and its creator does not store nor send any data to the creator or associated third parties in any shape or form.
                
                If you have any questions you can contact the creator/developer at hotifaton@outlook.com, specify 'Flags' in the title.
            """.trimIndent()
            openDialog("Data Integrity Policy", integrityMessage)
        }

    }

    private fun openDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .create().show()
    }

}
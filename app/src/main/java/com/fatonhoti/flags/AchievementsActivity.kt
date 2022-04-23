package com.fatonhoti.flags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AchievementsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)

        val items = mutableListOf<AchievementCard>()
        val a1 = AchievementCard("Master of flags", "You have correctly guessed 1000 flags.", "2022-04-23")
        val a2 = AchievementCard("Master of capitals", "You have correctly guessed 1000 capitals.", "2023-05-24")
        val a3 = AchievementCard("Master of currencies", "You have correctly guessed 1000 currencies.", "2024-06-25")
        val a4 = AchievementCard("Master of currencies", "You have correctly guessed 1000 currencies.", "2024-06-25")
        val a5 = AchievementCard("Master of currencies", "You have correctly guessed 1000 currencies.", "2024-06-25")
        val a6 = AchievementCard("Master of currencies", "You have correctly guessed 1000 currencies.", "2024-06-25")
        val a7 = AchievementCard("Master of currencies", "You have correctly guessed 1000 currencies.", "2024-06-25")
        val a8 = AchievementCard("Master of currencies", "You have correctly guessed 1000 currencies.", "2024-06-25")
        items.add(a1)
        items.add(a2)
        items.add(a3)
        items.add(a4)
        items.add(a5)
        items.add(a6)
        items.add(a7)
        items.add(a8)

        val recyclerView: RecyclerView = findViewById(R.id.rvAchievements)
        val adapter = AchievementsAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }
}
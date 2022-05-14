package com.fatonhoti.flags.achievement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatonhoti.flags.MyApplication
import com.fatonhoti.flags.R
import kotlinx.coroutines.*
import java.lang.Exception

class AchievementsActivity : AppCompatActivity() {

    private var db = MyApplication.applicationContext().getDbAchievements()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)

        GlobalScope.launch {
            try {
                val items = db.getAll()
                val cards = mutableListOf<AchievementCard>()
                items.forEach {
                    cards.add(AchievementCard(it.title, it.description, it.date, it.progress, it.limit, it.completed))
                }
                withContext(Dispatchers.Main) {
                    val recyclerView: RecyclerView = findViewById(R.id.rvAchievements)
                    val adapter = AchievementsAdapter(cards)
                    recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                    recyclerView.adapter = adapter
                }
            } catch(e: Exception) {
                Log.e("test", e.stackTraceToString())
            }
        }

    }
}
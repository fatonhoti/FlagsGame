package com.fatonhoti.flags

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fatonhoti.flags.achievement.Achievement
import com.fatonhoti.flags.achievement.AchievementDAO

@Database(entities = [Achievement::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun achievementDao(): AchievementDAO
}
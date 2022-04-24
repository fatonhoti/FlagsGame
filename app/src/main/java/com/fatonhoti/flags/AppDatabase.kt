package com.fatonhoti.flags

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Achievement::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun achievementDao(): AchievementDAO
}
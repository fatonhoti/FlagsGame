package com.fatonhoti.flags

import androidx.room.*

@Dao
interface AchievementDAO {
    @Query("SELECT * FROM achievement")
    fun getAll(): List<Achievement>

    @Query("SELECT * FROM achievement WHERE Completed = 'true'")
    fun getAllComplete(): List<Achievement>

    @Query("SELECT * FROM achievement WHERE Completed = 'false'")
    fun getAllIncomplete(): List<Achievement>

    @Query("SELECT * FROM achievement WHERE GameMode = :gameMode")
    fun getAllGameMode(gameMode: String): List<Achievement>

    @Update(entity = Achievement::class)
    fun updateAchievement(achievement: Achievement)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(achievements: MutableList<Achievement>)

    @Delete
    fun delete(achievement: Achievement)

    @Query("DELETE FROM achievement")
    fun deleteAll()
}
package com.fatonhoti.flags

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AchievementDAO {
    @Query("SELECT * FROM achievement")
    fun getAll(): List<Achievement>

    @Query("SELECT * FROM achievement WHERE Completed = 'true'")
    fun getAllComplete(): List<Achievement>

    @Query("SELECT * FROM achievement WHERE Completed = 'false'")
    fun getAllIncomplete(): List<Achievement>

    @Insert
    fun insertAll(vararg Achievements: Achievement)

    @Delete
    fun delete(achievement: Achievement)

    @Query("DELETE FROM achievement")
    fun deleteAll()
}
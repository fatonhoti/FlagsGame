package com.fatonhoti.flags.achievement

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Achievement(
    @ColumnInfo(name="Title") val title: String,
    @ColumnInfo(name="Description") val description: String,
    @ColumnInfo(name="GameMode") val gameMode: String,
    @ColumnInfo(name="Date") var date: String,
    @ColumnInfo(name="Progress") var progress: Int,
    @ColumnInfo(name="Limit") var limit: Int,
    @ColumnInfo(name="Completed") var completed: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
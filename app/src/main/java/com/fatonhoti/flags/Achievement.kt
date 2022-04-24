package com.fatonhoti.flags

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Achievement(
    @ColumnInfo(name="Title") val title: String,
    @ColumnInfo(name="Description") val description: String,
    @ColumnInfo(name="Date") val date: String,
    @ColumnInfo(name="Completed") val completed: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1
}
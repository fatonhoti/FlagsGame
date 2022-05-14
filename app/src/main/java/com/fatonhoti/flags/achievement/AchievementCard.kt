package com.fatonhoti.flags.achievement

data class AchievementCard(val title: String,
                           val description: String,
                           val date: String,
                            val progress: Int,
                           val limit: Int,
                            val completed: String)
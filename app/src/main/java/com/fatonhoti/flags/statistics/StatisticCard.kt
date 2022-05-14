package com.fatonhoti.flags.statistics

data class StatisticCard(val gameMode: String,
                         val gamesPlayed: Int,
                         val guesses: Int,
                         val correctGuesses: Int,
                         val incorrectGuesses: Int,
                         val averageCorrect: Float)
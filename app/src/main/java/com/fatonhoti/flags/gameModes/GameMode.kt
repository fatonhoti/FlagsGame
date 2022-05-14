package com.fatonhoti.flags.gameModes

interface GameMode {
    fun run()
    fun checkGuess(guess: String)
    fun next()
    fun initializeValues()
    fun fetchDataFromActivity()
}
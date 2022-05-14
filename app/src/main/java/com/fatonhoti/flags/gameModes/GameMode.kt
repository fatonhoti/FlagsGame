package com.fatonhoti.flags.gameModes

interface GameMode {
    // Methods
    fun run()
    fun checkGuess(guess: String)
    fun next()
    fun initializeValues()
    fun fetchDataFromActivity()
}
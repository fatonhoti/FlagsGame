package com.fatonhoti.flags

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fatonhoti.flags.achievement.Achievement
import com.fatonhoti.flags.achievement.AchievementDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyApplication : Application() {

    private lateinit var db: AchievementDAO

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null
        fun applicationContext() : MyApplication {
            return instance as MyApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        //deleteDatabase("db_achievements") //USE ONLY FOR DEBUG
        initializeStatistics()
        initializeAchievementDatabase()
    }

    private fun initializeStatistics() {
        val sharedPref = getSharedPreferences("statistics", MODE_PRIVATE)
        val editor = sharedPref.edit()

        // General statistics
        editor.putInt("Total games played", 0)
        editor.putInt("Total guesses", 0)
        editor.putInt("Total correct guesses", 0)
        editor.putInt("Total incorrect guesses", 0)
        editor.putFloat("Average correct guesses", 0.0f)

        // Flags
        editor.putInt("Flag games played", 0)
        editor.putInt("Flags guessed", 0)
        editor.putInt("Correct flags guessed", 0)
        editor.putInt("Incorrect flags guessed", 0)
        editor.putFloat("Average correct guesses (flags)", 0.0f)

        // Capitals
        editor.putInt("Capital games played", 0)
        editor.putInt("Capitals guessed", 0)
        editor.putInt("Correct capitals guessed", 0)
        editor.putInt("Incorrect capitals guessed", 0)
        editor.putFloat("Average correct guesses (capitals)", 0.0f)

        // Currencies
        editor.putInt("Currency games played", 0)
        editor.putInt("Currencies guessed", 0)
        editor.putInt("Correct currencies guessed", 0)
        editor.putInt("Incorrect currencies guessed", 0)
        editor.putFloat("Average correct guesses (currencies)", 0.0f)

        // Languages
        editor.putInt("Language games played", 0)
        editor.putInt("Languages guessed", 0)
        editor.putInt("Correct languages guessed", 0)
        editor.putInt("Incorrect languages guessed", 0)
        editor.putFloat("Average correct guesses (languages)", 0.0f)

        // Continents
        editor.putInt("Continent games played", 0)
        editor.putInt("Continents guessed", 0)
        editor.putInt("Correct continents guessed", 0)
        editor.putInt("Incorrect continents guessed", 0)
        editor.putFloat("Average correct guesses (continents)", 0.0f)

        // Apply changes
        editor.apply()
    }

    fun getDbAchievements() : AchievementDAO {
        return db
    }

    private fun initializeAchievementDatabase() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,"db_achievements"
        )
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                // Initialize the db with data on first-time creation
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    val items = mutableListOf (
                        Achievement(
                            "Novice Flag Guesser",
                            "You have correctly guessed 10 flags.",
                            "FLAGS",
                            "Not completed yet",
                            0,
                            10,
                            "false"
                        ),
                        Achievement(
                            "Advanced Beginner Flag Guesser",
                            "You have correctly guessed 100 flags.",
                            "FLAGS",
                            "Not completed yet",
                            0,
                            100,
                            "false"
                        ),
                        Achievement(
                            "Competent Flag Guesser",
                            "You have correctly guessed 200 flags.",
                            "FLAGS",
                            "Not completed yet",
                            0,
                            200,
                            "false"
                        ),
                        Achievement(
                            "Proficient Flag Guesser",
                            "You have correctly guessed 500 flags.",
                            "FLAGS",
                            "Not completed yet",
                            0,
                            500,
                            "false"
                        ),
                        Achievement(
                            "Expert Flag Guesser",
                            "You have correctly guessed 1000 flags.",
                            "FLAGS",
                            "Not completed yet",
                            0,
                            1000,
                            "false"
                        ), // ############################### CAPITALS
                        Achievement(
                            "Novice Capital Guesser",
                            "You have correctly guessed 10 capitals.",
                            "CAPITALS",
                            "Not completed yet",
                            0,
                            10,
                            "false"
                        ),
                        Achievement(
                            "Advanced Beginner Capital Guesser",
                            "You have correctly guessed 100 capitals.",
                            "CAPITALS",
                            "Not completed yet",
                            0,
                            100,
                            "false"
                        ),
                        Achievement(
                            "Competent Capital Guesser",
                            "You have correctly guessed 200 capitals.",
                            "CAPITALS",
                            "Not completed yet",
                            0,
                            200,
                            "false"
                        ),
                        Achievement(
                            "Proficient Capital Guesser",
                            "You have correctly guessed 500 capitals.",
                            "CAPITALS",
                            "Not completed yet",
                            0,
                            500,
                            "false"
                        ),
                        Achievement(
                            "Expert Capital Guesser",
                            "You have correctly guessed 1000 capitals.",
                            "CAPITALS",
                            "Not completed yet",
                            0,
                            1000,
                            "false"
                        ), // ############################### LANGUAGES
                        Achievement(
                            "Novice Language Guesser",
                            "You have correctly guessed 10 languages.",
                            "LANGUAGES",
                            "Not completed yet",
                            0,
                            10,
                            "false"
                        ),
                        Achievement(
                            "Advanced Beginner Language Guesser",
                            "You have correctly guessed 100 languages.",
                            "LANGUAGES",
                            "Not completed yet",
                            0,
                            100,
                            "false"
                        ),
                        Achievement(
                            "Competent Language Guesser",
                            "You have correctly guessed 200 languages.",
                            "LANGUAGES",
                            "Not completed yet",
                            0,
                            200,
                            "false"
                        ),
                        Achievement(
                            "Proficient Language Guesser",
                            "You have correctly guessed 500 languages.",
                            "LANGUAGES",
                            "Not completed yet",
                            0,
                            500,
                            "false"
                        ),
                        Achievement(
                            "Expert Language Guesser",
                            "You have correctly guessed 1000 languages.",
                            "LANGUAGES",
                            "Not completed yet",
                            0,
                            1000,
                            "false"
                        ), // ############################### Currencies
                        Achievement(
                            "Novice Currency Guesser",
                            "You have correctly guessed 10 currencies.",
                            "CURRENCIES",
                            "Not completed yet",
                            0,
                            10,
                            "false"
                        ),
                        Achievement(
                            "Advanced Beginner Currency Guesser",
                            "You have correctly guessed 100 currencies.",
                            "CURRENCIES",
                            "Not completed yet",
                            0,
                            100,
                            "false"
                        ),
                        Achievement(
                            "Competent Currency Guesser",
                            "You have correctly guessed 200 currencies.",
                            "CURRENCIES",
                            "Not completed yet",
                            0,
                            200,
                            "false"
                        ),
                        Achievement(
                            "Proficient Currency Guesser",
                            "You have correctly guessed 500 currencies.",
                            "CURRENCIES",
                            "Not completed yet",
                            0,
                            500,
                            "false"
                        ),
                        Achievement(
                            "Expert Currency Guesser",
                            "You have correctly guessed 1000 currencies.",
                            "CURRENCIES",
                            "Not completed yet",
                            0,
                            1000,
                            "false"
                        )
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        instance!!.getDbAchievements().insertAll(items)
                    }

                }
            })
            .build().achievementDao()
        this.db = db
    }

}
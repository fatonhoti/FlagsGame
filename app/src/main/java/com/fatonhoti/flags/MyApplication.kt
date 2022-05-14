package com.fatonhoti.flags

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.GlobalScope
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
        deleteDatabase("db_achievements") //USE ONLY FOR DEBUG
        initializeDatabase()
    }

    fun getDbAchievements() : AchievementDAO {
        return db
    }

    private fun initializeDatabase() {
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

                    GlobalScope.launch {
                        instance!!.getDbAchievements().insertAll(items)
                    }
                }
            })
            .build().achievementDao()
        this.db = db
    }

}
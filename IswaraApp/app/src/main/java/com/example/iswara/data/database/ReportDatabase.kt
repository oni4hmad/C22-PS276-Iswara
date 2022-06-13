package com.example.iswara.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Report::class, Chat::class, BotState::class],
    version = 1,
    exportSchema = false
)
abstract class ReportDatabase : RoomDatabase() {

    abstract fun reportDao(): ReportDao

    companion object {
        @Volatile
        private var INSTANCE: ReportDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ReportDatabase {
            if (INSTANCE == null) {
                synchronized(ReportDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ReportDatabase::class.java, "report_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as ReportDatabase
        }
    }

}
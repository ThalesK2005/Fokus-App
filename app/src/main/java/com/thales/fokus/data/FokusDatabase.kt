package com.thales.fokus.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thales.fokus.model.Task


@Database(entities = [Task::class], version = 2)
abstract class FokusDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: FokusDatabase? = null

        fun getDatabase(context: Context): FokusDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FokusDatabase::class.java,
                    "fokus_database"
                )
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}
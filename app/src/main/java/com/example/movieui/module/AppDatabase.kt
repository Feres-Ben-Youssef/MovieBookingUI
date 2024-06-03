package com.example.movieui.module

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieui.module.user.User
import com.example.movieui.module.user.UserDao

@Database(entities = [User::class], version = 2)  // Increment the version number
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()  // Use this only if you are okay with losing existing data during schema changes
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


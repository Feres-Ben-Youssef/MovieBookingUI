package com.example.movieui.module.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieui.module.user.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
}

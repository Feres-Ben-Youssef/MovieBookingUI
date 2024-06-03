package com.example.movieui.module.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieui.module.user.User
import com.example.movieui.module.user.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val userDao: UserDao) : ViewModel() {

    fun createUser(username: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("UserViewModel", "Checking if user exists: $username")
                val existingUser = userDao.getUserByUsername(username)
                if (existingUser != null) {
                    Log.e("UserViewModel", "Username already exists: $username")
                    callback(false)
                    return@launch
                }

                val user = User(username = username, password = password)
                userDao.insert(user)
                Log.d("UserViewModel", "User created successfully: $username")
                callback(true)
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error creating user", e)
                callback(false)
            }
        }
    }

    fun loginUser(username: String, password: String, callback: (User?) -> Unit) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                userDao.getUserByUsername(username)
            }
            if (user != null && user.password == password) {
                callback(user)
            } else {
                callback(null)
            }
        }
    }
}

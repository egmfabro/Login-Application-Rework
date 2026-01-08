package com.example.loginapplicationrework

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")

data class User(
    @PrimaryKey val username: String,
    val password: String
)

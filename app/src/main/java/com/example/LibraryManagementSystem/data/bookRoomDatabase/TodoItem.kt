package com.example.LibraryManagementSystem.data.bookRoomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    var isDone: Boolean = false
)
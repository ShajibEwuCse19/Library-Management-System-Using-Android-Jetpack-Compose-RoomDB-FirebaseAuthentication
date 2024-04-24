package com.example.LibraryManagementSystem.data.bookRoomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val publisher: String,
    val isbn: String,
    val price: String,
    val noOfPage: String,
    var isDone: Boolean = false
)
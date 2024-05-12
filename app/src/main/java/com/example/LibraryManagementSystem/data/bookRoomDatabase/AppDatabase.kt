package com.example.LibraryManagementSystem.data.bookRoomDatabase

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TodoItem::class, CheckoutItem::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    // 2. Abstract Functions
    abstract fun todoDao(): TodoDao
    abstract fun CheckoutDao(): CheckoutDao
}

package com.example.LibraryManagementSystem

//PracticeTwo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.LibraryManagementSystem.app.LibraryManagementApp
import com.example.LibraryManagementSystem.data.bookRoomDatabase.AppDatabase
import com.example.LibraryManagementSystem.data.bookRoomDatabase.CheckoutRepository
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.data.bookRoomDatabase.TodoRepository
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. Manual DB Creation
        val db = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "book-db")
            .build()
        // 2. Manual MainViewModel Creation
        val mainViewModel = MainViewModel(TodoRepository(db.todoDao()),CheckoutRepository(db.CheckoutDao()), ioDispatcher = Dispatchers.IO)
        setContent {
            LibraryManagementApp(mainViewModel)
        }
    }
}



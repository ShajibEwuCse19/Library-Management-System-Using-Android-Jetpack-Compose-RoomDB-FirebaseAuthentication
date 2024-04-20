package com.example.LibraryManagementSystem.data.bookRoomDatabase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MainViewModel(
    // 1. ViewModel Initialization
    private val repository: TodoRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    // 2. Todo Data Flow
    val todos = repository.allTodos


    // 3. Todo Operations
    fun addTodo(title1: String, author1: String) =
        viewModelScope.launch(ioDispatcher) {
            if (title1.trim().isNotEmpty() && author1.trim().isNotEmpty())
                repository.insert(TodoItem(title = title1, author = author1))
        }

    fun toggleTodo(todoItem: TodoItem) = viewModelScope.launch(ioDispatcher) {
        repository.insert(todoItem.copy(isDone = !todoItem.isDone))
    }

    fun removeTodo(todoItem: TodoItem) = viewModelScope.launch(ioDispatcher) {
        repository.delete(todoItem)
    }

}
package com.example.LibraryManagementSystem.data.bookRoomDatabase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MainViewModel(
    // 1. ViewModel Initialization
    private val repository: TodoRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    // 2. Todo Data Flow
    val todos = repository.allTodos


    // 3. Todo Operations
    fun addTodo(
        title: String,
        author: String,
        publisher: String,
        isbn: String,
        price: String,
        noOfPage: String
    ) =
        viewModelScope.launch(ioDispatcher) {
            if (title.trim().isNotEmpty() && author.trim().isNotEmpty() && publisher.trim()
                    .isNotEmpty()
                && isbn.trim().isNotEmpty() && price.trim().isNotEmpty() && noOfPage.trim()
                    .isNotEmpty()
            )
                repository.insert(
                    TodoItem(
                        title = title, author = author, publisher = publisher, isbn = isbn,
                        price = price, noOfPage = noOfPage
                    )
                )
        }

    fun toggleTodo(todoItem: TodoItem) = viewModelScope.launch(ioDispatcher) {
        repository.insert(todoItem.copy(isDone = !todoItem.isDone))
    }

    fun removeTodo(todoItem: TodoItem) = viewModelScope.launch(ioDispatcher) {
        repository.delete(todoItem)
    }

    // 4. Update Todo
    fun updateTodo(todoItem: TodoItem) = viewModelScope.launch(ioDispatcher) {
        if (todoItem.id != 0) { // Check if the todoItem has a valid id
            repository.update(todoItem)
        }
    }
}
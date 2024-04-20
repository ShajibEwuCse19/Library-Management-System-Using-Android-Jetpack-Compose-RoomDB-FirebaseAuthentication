package com.example.LibraryManagementSystem.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


sealed class Screen() {
    object SignUpScreen: Screen()
    object TermsAndConditionScreen: Screen()
    object LogInScreen: Screen()
    object HomeScreen: Screen()
    object HomeScreenNavDrawer: Screen()
    object AdminLoginScreen: Screen()
    object AdminHomeScreenNavDrawer: Screen()
    object CreateBook: Screen()
    object DeleteBook: Screen()
    object UpdateBook: Screen()
    object CheckoutBookScreen: Screen()
    object ReturnBookScreen: Screen()
    object BookListScreen: Screen()
    object AuthorListScreen: Screen()
}

object LibraryManagementAppRouter {
    val currentScreen: MutableState<Screen> = mutableStateOf(Screen.LogInScreen)

    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }
}
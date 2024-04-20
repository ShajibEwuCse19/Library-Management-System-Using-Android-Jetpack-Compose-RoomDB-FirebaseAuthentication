package com.example.LibraryManagementSystem.data.homePageAdmin

sealed class AdminHomePageNavScreens(val screen: String) {
    data object AdminHomeScreen: AdminHomePageNavScreens("adminHomeScreen")
}
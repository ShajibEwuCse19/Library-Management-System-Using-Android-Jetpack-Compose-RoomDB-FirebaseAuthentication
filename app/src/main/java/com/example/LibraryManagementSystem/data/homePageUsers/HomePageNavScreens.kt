package com.example.LibraryManagementSystem.data.homePageUsers

sealed class HomePageNavScreens(val screen: String) {
    data object HomeScreen: HomePageNavScreens("homePage")
    /*data object CheckoutBookScreen: HomePageNavScreens("checkoutBookScreen")
    data object ReturnBookScreen: HomePageNavScreens("returnBookScreen")
    data object BookListScreen: HomePageNavScreens("bookListScreen")
    data object AuthorListScreen: HomePageNavScreens("authorListScreen")
    data object ProfilePageScreen: HomePageNavScreens("ProfilePageScreen")
    data object SettingScreen: HomePageNavScreens("settingPage")*/
}
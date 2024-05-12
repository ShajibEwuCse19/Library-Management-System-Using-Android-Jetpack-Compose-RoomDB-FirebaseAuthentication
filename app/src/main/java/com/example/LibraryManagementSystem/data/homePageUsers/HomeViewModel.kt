package com.example.LibraryManagementSystem.data.homePageUsers

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.LibraryManagementSystem.data.signup.SignupViewModel
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel() {
    private val TAG = SignupViewModel::class.simpleName

    fun logout() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        //checking logout successful
        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Inside sign-out success.")

                LibraryManagementAppRouter.navigateTo(Screen.SignUpScreen)
            } else {
                Log.d(TAG, "Sign-out is not complete.")
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    //Admin Nav Drawer Menu
    fun AdminAddBook() {
        LibraryManagementAppRouter.navigateTo(Screen.CreateBook)
    }
    fun AdminUpdateBook() {
        LibraryManagementAppRouter.navigateTo(Screen.UpdateBook)
    }
    fun AdminDeleteBook() {
        LibraryManagementAppRouter.navigateTo(Screen.DeleteBook)
    }

    //User Nav Drawer Menu
    fun bookList() {
        LibraryManagementAppRouter.navigateTo(Screen.BookListScreen)
    }
    fun authorList() {
        LibraryManagementAppRouter.navigateTo(Screen.AuthorListScreen)
    }
    fun checkoutBook() {
        LibraryManagementAppRouter.navigateTo(Screen.CheckoutBookScreen)
    }
    //Admin Nav Drawer Menu
    fun returnBook() {
        LibraryManagementAppRouter.navigateTo(Screen.ReturnBookScreen)
    }
    fun adminBookList() {
        LibraryManagementAppRouter.navigateTo(Screen.AdminBookListScreen)
    }
}
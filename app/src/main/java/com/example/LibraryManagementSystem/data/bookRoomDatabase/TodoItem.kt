package com.example.LibraryManagementSystem.data.bookRoomDatabase

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Currency
import java.util.Date

@Entity
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val publisher: String,
    val isbn: String,
    val price: String,
    val currency: String,
    val date: String,
    val noOfPage: String,
    var isCheckout: Boolean = false, //show availability of an item
    var base64Image: String? = null
)

@Entity
data class CheckoutItem(
    @PrimaryKey
    val checkoutId: Int = 0,
    val email: String,
    val checkoutDate: String,
    val returnDate: String? = null,

    val title: String,
    val author: String,
    val publisher: String,
    val isbn: String,
    val price: String,
    val currency: String,
    val date: String,
    val noOfPage: String,
    var base64Image: String? = null
)

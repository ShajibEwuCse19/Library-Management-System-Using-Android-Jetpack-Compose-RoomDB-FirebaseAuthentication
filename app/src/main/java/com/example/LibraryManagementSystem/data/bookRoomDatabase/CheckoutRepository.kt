package com.example.LibraryManagementSystem.data.bookRoomDatabase

import kotlinx.coroutines.flow.Flow

class CheckoutRepository(private val checkoutDao: CheckoutDao) {
    val allCheckout: Flow<List<CheckoutItem>> = checkoutDao.getAllCheckout()

    suspend fun insert(item: CheckoutItem) {
        checkoutDao.insert(item)
    }

    suspend fun delete(item: CheckoutItem) {
        checkoutDao.delete(item)
    }

    fun allCheckoutFilteredByEmail(email: String) : Flow<List<CheckoutItem>> {
        return checkoutDao.getAllCheckoutFilteredByEmail(email)
    }
}


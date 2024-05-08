package com.example.LibraryManagementSystem.data.bookRoomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckoutDao {
    @Query("SELECT * FROM CheckoutItem")
    fun getAllCheckout(): Flow<List<CheckoutItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CheckoutItem)

    @Delete
    suspend fun delete(item: CheckoutItem)


    @Query("SELECT * FROM CheckoutItem WHERE email = :email")
    fun getAllCheckoutFilteredByEmail(email: String): Flow<List<CheckoutItem>>
}


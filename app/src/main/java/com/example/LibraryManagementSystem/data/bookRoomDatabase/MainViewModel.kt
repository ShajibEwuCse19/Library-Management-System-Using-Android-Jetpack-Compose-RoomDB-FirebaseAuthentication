package com.example.LibraryManagementSystem.data.bookRoomDatabase

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.LibraryManagementSystem.data.signup.SignupViewModel
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.example.LibraryManagementSystem.screens.EmailPicker
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream

class MainViewModel(
    private val repository: TodoRepository,
    private val checkoutRepository: CheckoutRepository,
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
        currency: String,
        date: String,
        noOfPage: String,
        imageUri: String,
        contentResolver: ContentResolver
    ) =
        viewModelScope.launch(ioDispatcher) {
            if (title.trim().isNotEmpty() && author.trim().isNotEmpty() && publisher.trim()
                    .isNotEmpty()
                && isbn.trim().isNotEmpty() && price.trim().isNotEmpty() && noOfPage.trim()
                    .isNotEmpty()
            ) {
                val imageData = compressImageAndConvertToBase64(imageUri, contentResolver)
                Log.d("TAGgg", "addTodo: $imageData")
                repository.insert(
                    TodoItem(
                        title = title, author = author, publisher = publisher, isbn = isbn,
                        price = price, currency = currency, date = date, noOfPage = noOfPage,
                        base64Image = imageData
                    )
                )
            }
        }

    private suspend fun compressImageAndConvertToBase64(
        imageUri: String,
        contentResolver: ContentResolver
    ): String? {
        return withContext(Dispatchers.IO) {
            var inputStream: InputStream? = null
            try {
                inputStream = contentResolver.openInputStream(Uri.parse(imageUri))
                inputStream?.use { stream ->
                    val originalBitmap = BitmapFactory.decodeStream(stream)
                    val outputStream = ByteArrayOutputStream()
                    // Compress the bitmap with quality and size adjustments
                    originalBitmap.compress(
                        Bitmap.CompressFormat.JPEG,
                        70,
                        outputStream
                    ) // Adjust quality as needed
                    // Convert the ByteArrayOutputStream to a Base64 string
                    return@withContext Base64.encodeToString(
                        outputStream.toByteArray(),
                        Base64.DEFAULT
                    )
                }
            } finally {
                inputStream?.close()
            }
            return@withContext null
        }
    }

    /*private fun convertImageToBase64(imageUri: String, contentResolver: ContentResolver): String? {
        val inputStream: InputStream? = contentResolver.openInputStream(Uri.parse(imageUri))
        inputStream?.use { stream ->
            val bytes = ByteArrayOutputStream().use {
                stream.copyTo(it)
                it.toByteArray()
            }
            return Base64.encodeToString(bytes, Base64.DEFAULT)
        }
        return null
    }*/

    fun removeTodo(todoItem: TodoItem) = viewModelScope.launch(ioDispatcher) {
        repository.delete(todoItem)
    }

    // 4. Update Todo
    fun updateTodo(todoItem: TodoItem) = viewModelScope.launch(ioDispatcher) {
        if (todoItem.id != 0) { // Check if the todoItem has a valid id
            repository.update(todoItem)
        }
    }

    //-----------Checkout Functionalities----------
    val checkoutAllTodos = checkoutRepository.allCheckout
    val checkoutAllFilteredByEmail =
        checkoutRepository.allCheckoutFilteredByEmail(email = EmailPicker.email)

    fun addCheckout(
        checkoutId: Int,
        email: String,
        checkoutDate: String,
        returnDate: String? = null,
        title: String,
        author: String,
        publisher: String,
        isbn: String,
        price: String,
        currency: String,
        date: String,
        noOfPage: String,
        base64Image: String
    ) = viewModelScope.launch(ioDispatcher) {
        if (email.trim().isNotEmpty()) checkoutRepository.insert(
            CheckoutItem(
                checkoutId = checkoutId,
                email = email,
                checkoutDate = checkoutDate,
                returnDate = returnDate,
                title = title,
                author = author,
                publisher = publisher,
                isbn = isbn,
                price = price,
                currency = currency,
                date = date,
                noOfPage = noOfPage,
                base64Image = base64Image
            )
        )
    }

    fun returnCheckout(checkoutItem: CheckoutItem) = viewModelScope.launch(ioDispatcher) {
        checkoutRepository.delete(checkoutItem)
    }

    //currency
    fun getCurrencyList(): List<String> {
        return listOf(
            "BDT", "USD", "EURO", "RUPEE"
        )
    }

    private var _currencyName = mutableStateOf(getCurrencyList().first())
    val currencyName: State<String> = _currencyName

    fun updateCurrencyName(currency: String) {
        if (!getCurrencyList().contains(currency)) {
            return
        }
        _currencyName.value = currency
    }


    //Logout from firebase
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

}



package com.example.LibraryManagementSystem.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.LibraryManagementSystem.data.bookRoomDatabase.TodoItem
import com.example.LibraryManagementSystem.ui.theme.constants.AccentColor
import com.example.LibraryManagementSystem.ui.theme.constants.TextColor
import kotlinx.coroutines.flow.Flow


val books = arrayListOf("")


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoInputBar(
    modifier: Modifier = Modifier,
    onAddButtonClick: (String, String, String, String, String, String) -> Unit,
) {
    var newTodoText by remember { mutableStateOf("") }
    var authorName by remember { mutableStateOf("") }
    var publisherName by remember { mutableStateOf("") }
    var isbnNo by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var pages by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .background(Color.LightGray),
            value = newTodoText,
            onValueChange = {
                newTodoText = it
            },
            label = { Text("Book Name") },
            textStyle = TextStyle(
                color = TextColor
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1
        )
        TextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .background(Color.LightGray),
            value = authorName,
            onValueChange = {
                authorName = it
            },
            label = { Text("Author Name") },
            textStyle = TextStyle(
                color = TextColor
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1
        )

        TextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .background(Color.LightGray),
            value = publisherName,
            onValueChange = {
                publisherName = it
            },
            label = { Text("Publisher Name") },
            textStyle = TextStyle(
                color = TextColor
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1
        )

        TextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .background(Color.LightGray),
            value = isbnNo,
            onValueChange = {
                isbnNo = it
            },
            label = { Text("ISBN") },
            textStyle = TextStyle(
                color = TextColor
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1
        )
        TextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .background(Color.LightGray),
            value = price,
            onValueChange = {
                price = it
            },
            label = { Text("Price") },
            textStyle = TextStyle(
                color = TextColor
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1
        )
        TextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .background(Color.LightGray),
            value = pages,
            onValueChange = {
                pages = it
            },
            label = { Text("Total Page") },
            textStyle = TextStyle(
                color = TextColor
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            maxLines = 1
        )

        //add new book button
        Button(
            onClick = {
                onAddButtonClick(newTodoText, authorName, publisherName, isbnNo, price, pages)
                if (newTodoText.trim().isNotEmpty() && authorName.trim()
                        .isNotEmpty() && publisherName.trim().isNotEmpty()
                    && isbnNo.trim().isNotEmpty() && price.trim().isNotEmpty() && pages.trim()
                        .isNotEmpty()
                ) {
                    Toast.makeText(context, "Successfully added the book", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, "Enter valid book or author name", Toast.LENGTH_SHORT)
                        .show()
                }
                newTodoText = ""
                authorName = ""
                publisherName = ""
                isbnNo = ""
                price = ""
                pages = ""
            },
            enabled = newTodoText.isNotBlank() && authorName.isNotBlank() && publisherName.isNotBlank() &&
                    isbnNo.isNotBlank() && price.isNotBlank() && pages.isNotBlank(),
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White
            )
        ) {
            Text("Add New Book")
        }
    }
}

@Composable
fun TodoItemsContainer(
    todoItemsFlow: Flow<List<TodoItem>>,
    onItemClick: (TodoItem) -> Unit,
    onItemDelete: (TodoItem) -> Unit,
) {
    val todoItems by todoItemsFlow.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier) {
        items(todoItems) { todoItem ->
            TodoItemRow(
                todoItem = todoItem,
                onItemClick = onItemClick,
                onItemDelete = onItemDelete
            )
        }
    }
}

@Composable
fun TodoItemRow(
    todoItem: TodoItem,
    onItemClick: (TodoItem) -> Unit,
    onItemDelete: (TodoItem) -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .background(AccentColor, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Aligns children in the Row with space between them
    ) {
        // Displaying title and author in left side
        Column(
            modifier = Modifier.weight(1f) // Takes up remaining space in the Row
        ) {
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { onItemClick.invoke(todoItem) },
                text = "Title: ${todoItem.title}",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
            )
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { onItemClick.invoke(todoItem) },
                text = "Author: ${todoItem.author}",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
            )
        }
        // delete book button on the right side
        IconButton(
            onClick = {
                onItemDelete.invoke(todoItem)
                Toast.makeText(context, "Successfully deleted the book", Toast.LENGTH_SHORT).show()
                books.remove(todoItem.title)
            },
            modifier = Modifier.size(70.dp),
        ) {
            Icon(
                modifier = Modifier.padding(10.dp),
                imageVector = Icons.Outlined.Delete,
                contentDescription = "DeleteIcon"
            )
        }
    }
}
@Composable
fun PlusButton(onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                .padding(16.dp)
                .clickable(onClick = onClick)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}









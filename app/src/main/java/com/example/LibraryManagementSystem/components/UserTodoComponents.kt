package com.example.LibraryManagementSystem.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ShoppingCart
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
import com.example.LibraryManagementSystem.ui.theme.constants.GrayColor
import com.example.LibraryManagementSystem.ui.theme.constants.TextColor
import kotlinx.coroutines.flow.Flow

@Composable
fun CartButton(onClick: () -> Unit) {
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
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun BookListContainer(
    todoItemsFlow: Flow<List<TodoItem>>,
) {
    val todoItems by todoItemsFlow.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier) {
        items(todoItems) { todoItem ->
            BookListTodoItemRow(
                todoItem = todoItem
            )
        }
    }
}

@Composable
fun BookListTodoItemRow(
    todoItem: TodoItem,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .border(width = 2.dp, color = GrayColor, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .background(AccentColor, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = "Book Name: ${todoItem.title}",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
            )
        }
    }
}

@Composable
fun AuthorListContainer(
    todoItemsFlow: Flow<List<TodoItem>>,
) {
    val todoItems by todoItemsFlow.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier) {
        items(todoItems) { todoItem ->
            AuthorTodoItemRow(
                todoItem = todoItem
            )
        }
    }
}

@Composable
fun AuthorTodoItemRow(
    todoItem: TodoItem,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .border(width = 2.dp, color = GrayColor, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .background(AccentColor, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = "Author Name: ${todoItem.author}",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
            )
        }
    }
}

//checkoutBook
@Composable
fun CheckoutItemsContainer(
    todoItemsFlow: Flow<List<TodoItem>>,
    onItemClick: (TodoItem) -> Unit,
    onItemDelete: (TodoItem) -> Unit,
) {
    val todoItems by todoItemsFlow.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier) {
        items(todoItems) { todoItem ->
            CheckoutItemRow(
                todoItem = todoItem,
                onItemClick = onItemClick,
                onItemDelete = onItemDelete
            )
        }
    }
}

@Composable
fun CheckoutItemRow(
    todoItem: TodoItem,
    onItemClick: (TodoItem) -> Unit,
    onItemDelete: (TodoItem) -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .border(width = 2.dp, color = GrayColor, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .background(AccentColor, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
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
        IconButton(
            onClick = {
                onItemDelete.invoke(todoItem)
                Toast.makeText(context, "Book checkout successful", Toast.LENGTH_SHORT).show()
                books.remove(todoItem.title)///delete implemented
            },
            modifier = Modifier.size(70.dp),
        ) {
            Icon(
                modifier = Modifier.padding(10.dp),
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "CartIcon"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReturnBookInputBar(
    modifier: Modifier = Modifier,
    onAddButtonClick: (String, String) -> Unit
) {
    var newTodoText by remember {
        mutableStateOf("")
    }
    var authorName by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Awesome text field for entering new todo item
        TextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .background(Color.LightGray),
            value = newTodoText,
            onValueChange = {
                newTodoText = it
            },
            label = { Text("Enter Book Name") },
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
            label = { Text("Enter Author Name") },
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
        // Awesome button to add new todo item
        Button(
            onClick = {
                onAddButtonClick(newTodoText, authorName)
                if (newTodoText.trim().isNotEmpty() && authorName.trim().isNotEmpty())
                    Toast.makeText(context, "Successfully returned the book", Toast.LENGTH_SHORT)
                        .show()
                else Toast.makeText(context, "Enter valid book or author name", Toast.LENGTH_SHORT)
                    .show()
                books.add(newTodoText)
                newTodoText = ""
                authorName = ""
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White // Customize button text color
            )
        ) {
            Text("Return Book")
        }
    }
}

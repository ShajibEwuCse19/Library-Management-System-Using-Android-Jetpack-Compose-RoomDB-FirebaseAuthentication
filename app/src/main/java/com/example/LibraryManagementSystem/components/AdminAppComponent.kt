package com.example.LibraryManagementSystem.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.LibraryManagementSystem.R
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.data.bookRoomDatabase.TodoItem
import com.example.LibraryManagementSystem.ui.theme.constants.WhiteColor
import com.example.LibraryManagementSystem.ui.theme.constants.componentShapes
import kotlinx.coroutines.flow.Flow

@Composable
fun AdminTextFieldComponent(
    labelValue: String,
    iconValue: ImageVector,
    onTextSelected: (String) -> Unit,
) {
    val textValue = remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = componentShapes.small)
            .padding(start = 20.dp, end = 20.dp),
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it) //saving the input into UI state
        },
        label = {
            Text(
                text = labelValue
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.colorPrimary),
            focusedLabelColor = colorResource(id = R.color.colorPrimary),
            cursorColor = colorResource(id = R.color.colorPrimary),
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Icon(iconValue, contentDescription = "")
        },
    )
}

@Composable
fun AddButton(
    texValue: String,
    onButtonClicked: () -> Unit,
) {
    Button(
        onClick = {
            onButtonClicked.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(text = texValue)
    }
}

@Composable
fun AdminBookListContainer(
    todoItemsFlow: Flow<List<TodoItem>>,
    onButtonClicked: (TodoItem) -> Unit
) {
    val todoItems by todoItemsFlow.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier) {
        items(todoItems) { todoItem ->
            AdminBookListTodoItemRow(
                todoItem = todoItem,
                onButtonClicked = {
                    onButtonClicked(todoItem)
                }
            )
        }
    }
}

@Composable
fun AdminBookListTodoItemRow(
    todoItem: TodoItem,
    onButtonClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(onClick = { onButtonClicked.invoke() }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(.8F)
                    .padding(10.dp),
                text = "Book Name: ${todoItem.title}",
                fontWeight = FontWeight.Bold,
                color = WhiteColor,
            )
        }
        IconButton(onClick = { onButtonClicked.invoke() }) {
            Icon(
                modifier = Modifier.padding(10.dp),
                imageVector = Icons.Rounded.Info,
                contentDescription = "DetailsButton",
                tint = WhiteColor
            )
        }
    }
}
@Composable
fun BookInfoContent(todoItem: TodoItem) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Title: ${todoItem.title}",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Author: ${todoItem.author}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Publisher: ${todoItem.publisher}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "ISBN: ${todoItem.isbn}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Price: ${todoItem.price}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Number of Pages: ${todoItem.noOfPage}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
fun AdminUpdateBookListContainer(
    todoItemsFlow: Flow<List<TodoItem>>,
    onButtonClicked: (TodoItem) -> Unit
) {
    val todoItems by todoItemsFlow.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier) {
        items(todoItems) { todoItem ->
            AdminUpdateBookListTodoItemRow(
                todoItem = todoItem,
                onButtonClicked = {
                    onButtonClicked(todoItem)
                }
            )
        }
    }
}

@Composable
fun AdminUpdateBookListTodoItemRow(
    todoItem: TodoItem,
    onButtonClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(onClick = { onButtonClicked.invoke() }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(.8F)
                    .padding(10.dp),
                text = "Book Name: ${todoItem.title}",
                fontWeight = FontWeight.Bold,
                color = WhiteColor,
            )
        }
        IconButton(onClick = { onButtonClicked.invoke() }) {
            Icon(
                modifier = Modifier.padding(10.dp),
                imageVector = Icons.Rounded.Edit,
                contentDescription = "DetailsButton",
                tint = WhiteColor
            )
        }
    }
}

@Composable
fun BookUpdateInfoContent(
    mainViewModel: MainViewModel,
    todoItem: TodoItem,
    onUpdateButtonClicked: (TodoItem) -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var title by remember { mutableStateOf(todoItem.title) }
    var author by remember { mutableStateOf(todoItem.author) }
    var publisher by remember { mutableStateOf(todoItem.publisher) }
    var isbn by remember { mutableStateOf(todoItem.isbn) }
    var price by remember { mutableStateOf(todoItem.price) }
    var noOfPage by remember { mutableStateOf(todoItem.noOfPage) }

    Column(
        modifier = Modifier.padding(16.dp).verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1
        )

        TextField(
            value = author,
            onValueChange = { author = it },
            label = { Text("Author") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1
        )

        TextField(
            value = publisher,
            onValueChange = { publisher = it },
            label = { Text("Publisher") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1
        )

        TextField(
            value = isbn,
            onValueChange = { isbn = it },
            label = { Text("ISBN") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1
        )

        TextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1
        )

        TextField(
            value = noOfPage,
            onValueChange = { noOfPage = it },
            label = { Text("Number of Pages") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            maxLines = 1
        )

        Button(
            onClick = {
                val updatedTodoItem = todoItem.copy(
                    title = title,
                    author = author,
                    publisher = publisher,
                    isbn = isbn,
                    price = price,
                    noOfPage = noOfPage
                )
                onUpdateButtonClicked(updatedTodoItem)
                mainViewModel.updateTodo(updatedTodoItem)
                Toast.makeText(context, "Book Updated Successfully", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 50.dp)
        ) {
            Text("Update")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithBackButton(
    textValue: String,
    onButtonClicked: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = { Text(text = textValue) },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked.invoke() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        },
        modifier = Modifier
            .padding(bottom = 8.dp)
            .background(MaterialTheme.colorScheme.primary)
    )
}

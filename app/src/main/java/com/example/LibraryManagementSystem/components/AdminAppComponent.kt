package com.example.LibraryManagementSystem.components

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.data.bookRoomDatabase.TodoItem
import com.example.LibraryManagementSystem.ui.theme.constants.GrayColor
import com.example.LibraryManagementSystem.ui.theme.constants.GreenColor
import com.example.LibraryManagementSystem.ui.theme.constants.Secondary
import com.example.LibraryManagementSystem.ui.theme.constants.WhiteColor
import kotlinx.coroutines.flow.Flow
import kotlin.math.roundToInt


@Composable
fun AdminBookListContainer(
    todoItemsFlow: Flow<List<TodoItem>>,
    onButtonClicked: (TodoItem) -> Unit,
    onDeleteButtonClicked: (TodoItem) -> Unit,
    onEditButtonClicked: (TodoItem) -> Unit,
    onDetailsButtonClicked: (TodoItem) -> Unit
) {
    val todoItems by todoItemsFlow.collectAsState(initial = emptyList())

    if (todoItems.isEmpty()) {
        HeadingTextComponent(value = "No books available at this moment...")
    } else {
        LazyColumn(modifier = Modifier) {
            items(todoItems) { todoItem ->
                AdminBookListTodoItemRowWithSwipeToAction(
                    todoItem = todoItem,
                    onButtonClicked = {
                        onButtonClicked(todoItem)
                    },
                    onDeleteButtonClicked = {
                        onDeleteButtonClicked(todoItem)
                    },
                    onDetailsButtonClicked = {
                        onDetailsButtonClicked(todoItem)
                    },
                    onEditButtonClicked = {
                        onEditButtonClicked(todoItem)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun AdminBookListTodoItemRowWithSwipeToAction(
    todoItem: TodoItem,
    onButtonClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
    onEditButtonClicked: () -> Unit,
    onDetailsButtonClicked: () -> Unit
) {
    val context = LocalContext.current
    val swipeableState = rememberSwipeableState(initialValue = 0)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .swipeable(
                state = swipeableState,
                anchors = mapOf(
                    0f to 0,//original position
                    -dpToPx(context = context, dpValue = 100f) to 1, //swipe to left
                    //change - to + to swipe right
                ),
                thresholds = { _, _ ->
                    FractionalThreshold(0.6f)
                },
                orientation = Orientation.Horizontal
            )
            .padding(bottom = 5.dp, start = 16.dp, end = 16.dp)
            .background(GreenColor, RoundedCornerShape(16.dp))
    ) {
        //bottom box
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 30.dp),
                horizontalAlignment = Alignment.End
            ) {
                IconButton(onClick = { onDeleteButtonClicked.invoke() }) {
                    androidx.wear.compose.material.Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                IconButton(onClick = { onEditButtonClicked.invoke() }) {
                    androidx.wear.compose.material.Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                IconButton(onClick = { onDetailsButtonClicked.invoke() }) {
                    androidx.wear.compose.material.Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }

            //top box
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(swipeableState.offset.value.roundToInt(), 0)
                    }
                    .fillMaxSize()
                    .background(Secondary, RoundedCornerShape(16.dp))
                    .border(2.dp, GrayColor, RoundedCornerShape(16.dp))
                    .clickable { onButtonClicked.invoke() }
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.4f)
                            .aspectRatio(1f)
                    ) {
                        val imageBitmap =
                            todoItem.base64Image?.let { decodeBase64ToImageBitmap(it) }
                        imageBitmap?.let {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                bitmap = it,
                                contentDescription = ""
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp, top = 20.dp),
                            text = todoItem.title,
                            color = Color.Black,
                            textAlign = TextAlign.Start,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.padding(start = 16.dp, top = 20.dp),
                            text = "Author: ${todoItem.author}",
                            color = Color.Black,
                            textAlign = TextAlign.Start,
                            fontSize = 10.sp,
                        )
                        Text(
                            modifier = Modifier.padding(start = 16.dp, top = 20.dp),
                            text = todoItem.date + " (" + todoItem.publisher + ")",
                            color = Color.Black,
                            textAlign = TextAlign.Start,
                            fontSize = 10.sp,
                        )
                    }

                }
            }
        }

    }
}

private fun dpToPx(context: Context, dpValue: Float): Float {
    return dpValue * context.resources.displayMetrics.density
}

fun decodeBase64ToImageBitmap(base64String: String): ImageBitmap? {
    val decodedBytes: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    return bitmap?.asImageBitmap()
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
        Row {
            val imageBitmap = todoItem.base64Image?.let { decodeBase64ToImageBitmap(it) }
            imageBitmap?.let {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(.3f)
                        .padding(top = 5.dp, end = 16.dp),
                    bitmap = it,
                    contentDescription = "Book Image"
                )
            }
            Column {
                Text(
                    text = todoItem.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Author: ${todoItem.author}",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Publisher: ${todoItem.publisher}",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "ISBN: ${todoItem.isbn}",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Price: (${todoItem.currency}) ${todoItem.price}",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Published Date: ${todoItem.date}",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Number of Pages: ${todoItem.noOfPage}",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }

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
    var currency by remember { mutableStateOf(todoItem.currency) }
    var noOfPage by remember { mutableStateOf(todoItem.noOfPage) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState),
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
            value = currency,
            onValueChange = { currency = it },
            label = { Text("Currency") },
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
                    currency = currency,
                    noOfPage = noOfPage
                )
                onUpdateButtonClicked(updatedTodoItem)
                mainViewModel.updateTodo(updatedTodoItem)
                Toast.makeText(context, "Book Updated Successfully", Toast.LENGTH_SHORT).show()
            },
            enabled = title.trim().isNotEmpty() && author.trim().isNotEmpty() && publisher.trim()
                .isNotEmpty() &&
                    price.trim().isNotEmpty() && noOfPage.trim().isNotEmpty() && currency.trim()
                .isNotEmpty(),
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 400.dp)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithBackAndRefresh(
    textValue: String,
    onBackButtonClicked: () -> Unit,
    onRefreshButtonClicked: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = { Text(text = textValue) },

        navigationIcon = {
            IconButton(onClick = { onBackButtonClicked.invoke() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = { onRefreshButtonClicked.invoke() }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = Color.White
                )
            }
        },
        // Apply modifier to the top app bar
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithLogout(
    textValue: String,
    onLogoutButtonClicked: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = { Text(text = textValue) },
        actions = {
            IconButton(onClick = { onLogoutButtonClicked.invoke() }) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Logout",
                    tint = Color.White
                )
            }
        },
        // Apply modifier to the top app bar
        modifier = Modifier.padding(bottom = 8.dp)
    )
}
@Composable
fun MyFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
        contentColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(16.dp),
        onClick = {
            onClick.invoke()
    }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add"
        )
    }
}

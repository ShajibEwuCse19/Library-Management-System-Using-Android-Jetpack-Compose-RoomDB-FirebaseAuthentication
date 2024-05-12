package com.example.LibraryManagementSystem.components

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.RemoveShoppingCart
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.LibraryManagementSystem.data.bookRoomDatabase.CheckoutItem
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.data.bookRoomDatabase.TodoItem
import com.example.LibraryManagementSystem.screens.EmailPicker
import com.example.LibraryManagementSystem.ui.theme.constants.AccentColor
import com.example.LibraryManagementSystem.ui.theme.constants.TextColor
import com.example.LibraryManagementSystem.ui.theme.constants.WhiteColor
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
    mainViewModel: MainViewModel,
    checkoutItemFlow: Flow<List<CheckoutItem>>,
    onItemDelete: (CheckoutItem) -> Unit,
) {
    val checkoutItem by checkoutItemFlow.collectAsState(initial = emptyList())

    if (checkoutItem.isEmpty()) HeadingTextComponent(value = "You don't have any books to return")

    LazyColumn(modifier = Modifier) {
        items(checkoutItem) { item ->
            if (item.email == EmailPicker.email) {
                CheckoutItemRow(
                    mainViewModel,
                    item = item,
                    onItemDelete = { todo ->
                        onItemDelete(todo)
                    }
                )
            }
        }
    }

}

@Composable
fun CheckoutItemRow(
    mainViewModel: MainViewModel,
    item: CheckoutItem,
    onItemDelete: (CheckoutItem) -> Unit //delete from checkout and add to the admin book list (availale for checkout)
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
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
                text = "Book Name: ${item.title}",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
            )
            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = "Checkout Date: ${item.checkoutDate}",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
            )
            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = "Return Date: ${item.returnDate}",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
            )
        }
        IconButton(
            onClick = {
                onItemDelete.invoke(item)
                mainViewModel.updateTodo(
                    TodoItem(
                        item.checkoutId,
                        item.title,
                        item.author,
                        item.publisher,
                        item.isbn,
                        item.price,
                        item.currency,
                        item.date,
                        item.noOfPage,
                        false,
                        item.base64Image
                    )
                )
            },
            modifier = Modifier.size(70.dp),
        ) {
            Icon(
                modifier = Modifier.padding(10.dp),
                imageVector = Icons.Outlined.RemoveShoppingCart,
                contentDescription = "Return Book"
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopAppBarWithBackButton(
    textValue: String,
    onButtonClicked: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue,
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

@Composable
fun LargeTextButton(
    textValue: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
            .height(100.dp)
            .shadow(8.dp, shape = RoundedCornerShape(50.dp)),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White
        )
    ) {
        Text(
            text = textValue,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.sp
        )
    }
}

@Composable
fun CheckoutBookListContainer(
    todoItemsFlow: Flow<List<TodoItem>>,
    onButtonClicked: (TodoItem) -> Unit
) {
    val todoItems by todoItemsFlow.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier) {
        items(todoItems) { todoItem ->
            CheckoutBookListItemRow(
                todoItem = todoItem,
                onButtonClicked = {
                    onButtonClicked(todoItem)
                }
            )
        }
    }
}

@Composable
fun CheckoutBookListItemRow(
    todoItem: TodoItem,
    onButtonClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clickable { onButtonClicked.invoke() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp)),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(.8F)
                        .padding(10.dp),
                    text = "Book Name: ${todoItem.title}",
                    fontWeight = FontWeight.Bold,
                    color = WhiteColor,
                )
                IconButton(onClick = {
                    onButtonClicked.invoke()
                }) {
                    Icon(
                        modifier = Modifier.padding(10.dp),
                        imageVector = Icons.Rounded.ShoppingCart,
                        contentDescription = "checkout_Book",
                        tint = WhiteColor
                    )
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = if (todoItem.isCheckout) "Out of Stock" else "In Stock",
                fontWeight = FontWeight.Bold,
                color = WhiteColor,
            )
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutBookInfoInputWithDate(
    mainViewModel: MainViewModel,
    todoItem: TodoItem,
    onCheckoutButtonClicked: (TodoItem) -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val placeholder = "MM/DD/YYYY"

    //var id by remember { mutableIntStateOf(todoItem.id) }
    var email by remember { mutableStateOf(EmailPicker.email) }
    /*var title by remember { mutableStateOf(todoItem.title) }
    var author by remember { mutableStateOf(todoItem.author) }
    var publisher by remember { mutableStateOf(todoItem.publisher) }
    var isbn by remember { mutableStateOf(todoItem.isbn) }
    var currency by remember { mutableStateOf(todoItem.currency) }
    var price by remember { mutableStateOf(todoItem.price) }*/
    var checkoutDate by remember { mutableStateOf("") }
    var returnDate by remember { mutableStateOf("") }
    //var noOfPage by remember { mutableStateOf(todoItem.noOfPage) }
    //var base64Image by remember { mutableStateOf(todoItem.base64Image) }

    // State for selected dates
    var checkoutCalendarState = rememberSheetState()
    var returnCalendarState = rememberSheetState()
    val currentDate = Calendar.getInstance().time
    val fiveDaysLater = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 5) }.time

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeadingTextComponent(value = "Only for register users")
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = EmailPicker.email,
            onValueChange = { email = it },
            label = { Text("Email") },
            textStyle = TextStyle(
                color = TextColor
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            maxLines = 1
        )

        //Checkout Date
        CalendarDialog(
            state = checkoutCalendarState,
            selection = CalendarSelection.Date { selectedDate ->
                Log.d("selectedDate", "$selectedDate")
                checkoutDate = selectedDate.toString()
            }
        )
        Row {
            IconButton(
                modifier = Modifier
                    .fillMaxWidth(.3F)
                    .padding(top = 5.dp, bottom = 16.dp, start = 10.dp, end = 10.dp)
                    .background(Color.LightGray, RoundedCornerShape(16.dp)),
                onClick = { checkoutCalendarState.show() }
            ) { Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "calendar") }
            TextField(
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                value = checkoutDate,
                onValueChange = { checkoutDate = it },
                label = { Text("Checkout Date") },
                readOnly = true, // Disable editing
                modifier = Modifier.fillMaxWidth()
            )
        }
        //Return date
        CalendarDialog(
            state = returnCalendarState,
            selection = CalendarSelection.Date { selectedDate ->
                Log.d("selectedDate", "$selectedDate")
                returnDate = selectedDate.toString()
            }
        )
        Row {
            IconButton(
                modifier = Modifier
                    .fillMaxWidth(.3F)
                    .padding(top = 5.dp, bottom = 16.dp, start = 10.dp, end = 10.dp)
                    .background(Color.LightGray, RoundedCornerShape(16.dp)),
                onClick = { returnCalendarState.show() }
            ) { Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "calendar") }
            TextField(
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                value = returnDate,
                onValueChange = { returnDate = it },
                label = { Text("Return Date") },
                readOnly = true, // Disable editing
                modifier = Modifier.fillMaxWidth()
            )
        }


        Button(
            onClick = {
                todoItem.isCheckout = true //In stock or out of stock
                val updatedTodoItem = todoItem.copy(
                    id = todoItem.id,
                    title = todoItem.title,
                    author = todoItem.author,
                    publisher = todoItem.publisher,
                    isbn = todoItem.isbn,
                    price = todoItem.price,
                    noOfPage = todoItem.noOfPage,
                    base64Image = todoItem.base64Image
                )
                onCheckoutButtonClicked(updatedTodoItem)
                mainViewModel.updateTodo(updatedTodoItem)
                todoItem.base64Image?.let { uri ->
                    mainViewModel.addCheckout(
                        checkoutId = todoItem.id,
                        email = EmailPicker.email,
                        checkoutDate = dateFormat.format(currentDate), // Convert to string
                        returnDate = dateFormat.format(fiveDaysLater), // Convert to string
                        title = todoItem.title,
                        author = todoItem.author,
                        publisher = todoItem.publisher,
                        isbn = todoItem.isbn,
                        price = todoItem.price,
                        currency = todoItem.currency,
                        date = todoItem.date,
                        noOfPage = todoItem.noOfPage,
                        base64Image = uri
                    )
                }

                checkoutDate = ""
                returnDate = ""
                Toast.makeText(context, "Book checkout successful", Toast.LENGTH_SHORT).show()
            },
            enabled = email.isNotBlank() && checkoutDate.isNotBlank() && returnDate.isNotBlank() && !todoItem.isCheckout,
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 400.dp)
        ) {
            Text("Checkout")
        }
    }
}






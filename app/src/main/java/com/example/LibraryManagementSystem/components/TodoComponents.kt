package com.example.LibraryManagementSystem.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.LibraryManagementSystem.R
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.data.bookRoomDatabase.TodoItem
import com.example.LibraryManagementSystem.ui.theme.constants.AccentColor
import com.example.LibraryManagementSystem.ui.theme.constants.GrayColor
import com.example.LibraryManagementSystem.ui.theme.constants.GreenColor
import com.example.LibraryManagementSystem.ui.theme.constants.TextColor
import com.example.LibraryManagementSystem.ui.theme.constants.WhiteColor
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import kotlin.math.roundToInt


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun TodoInputBar(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    onAddButtonClick: (String, String, String, String, String, String, String, String, String) -> Unit,
) {
    var newTodoText by remember { mutableStateOf("") }
    var authorName by remember { mutableStateOf("") }
    var publisherName by remember { mutableStateOf("") }
    var isbnNo by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var pages by remember { mutableStateOf("") }
    var calendarState = rememberSheetState()
    var imageUri by remember { mutableStateOf<Uri?>(Uri.EMPTY) } // Store selected image URI
    val context = LocalContext.current

    // Image picker launcher from gallery
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            imageUri = uri
        }
    )
    // Image picker launcher using camera
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            imageUri = uri
        }
    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                Toast.makeText(context, "permission granted", Toast.LENGTH_SHORT).show()
                cameraLauncher.launch(uri)
            } else {
                Toast.makeText(context, "permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        TextField(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
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
                .background(Color.LightGray, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
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
                .background(Color.LightGray, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
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
                .background(Color.LightGray, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
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
                .background(Color.LightGray, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            maxLines = 1
        )

        Row {
            DropdownMenuComponent(viewModel)
            TextField(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                maxLines = 1
            )
        }
        //book publish date
        CalendarDialog(
            state = calendarState,
            config = CalendarConfig(
                monthSelection = true,
                yearSelection = true
            ),
            selection = CalendarSelection.Date { selectedDate ->
                Log.d("selectedDate", "$selectedDate")
                date = selectedDate.toString()
            }
        )

        Row {
            IconButton(
                modifier = Modifier
                    .fillMaxWidth(.3F)
                    .padding(top = 10.dp, bottom = 16.dp, start = 10.dp, end = 10.dp)
                    .background(Color.LightGray, RoundedCornerShape(16.dp)),
                onClick = { calendarState.show() }
            ) {
                Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "calendar")
            }
            TextField(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                value = date,
                onValueChange = {
                    date = it
                },
                label = { Text("Published Date") },
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
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Launching camera for capturing image
            IconButton(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 16.dp, start = 10.dp, end = 10.dp)
                    .background(Color.LightGray, RoundedCornerShape(16.dp)),
                onClick = {
                    val permissionCheckResult =
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(uri)
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Take Picture"
                )
            }

            // Image picker icon button
            IconButton(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 16.dp, start = 10.dp, end = 10.dp)
                    .background(Color.LightGray, RoundedCornerShape(16.dp)),
                onClick = { launcher.launch("image/*") }, // Launch image picker
            ) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = "Pick Image"
                )
            }

            // Show small preview of the selected image
            if (imageUri != Uri.EMPTY) {
                Image(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(64.dp)
                        .background(Color.LightGray, RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Fit, // Adjust content scale as needed
                    painter = rememberImagePainter(imageUri),
                    contentDescription = "Uploaded Image Preview"
                )
            } else NoImageSelectedBox()
        }

        //add new book button
        Button(
            onClick = {
                onAddButtonClick(
                    newTodoText,
                    authorName,
                    publisherName,
                    isbnNo,
                    price,
                    viewModel.currencyName.value,
                    date,
                    pages,
                    imageUri.toString(),
                )
                if (newTodoText.trim().isNotEmpty() && authorName.trim()
                        .isNotEmpty() && publisherName.trim().isNotEmpty()
                    && isbnNo.trim().isNotEmpty() && price.trim()
                        .isNotEmpty() && date.isNotEmpty() && pages.trim()
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
                date = ""
                pages = ""
                imageUri = Uri.EMPTY
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

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH:mm::ss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )

    return image
}

@Composable
fun NoImageSelectedBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .size(64.dp)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(5.dp),
            text = "No image selected",
            style = TextStyle(
                color = TextColor,
                fontWeight = FontWeight.Bold
            )
        )
    }
}


@Composable
fun HighlightedText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier
            .padding(5.dp)
            .background(Color.LightGray, RoundedCornerShape(16.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp),
        color = TextColor,
        style = TextStyle(
            fontWeight = FontWeight.Bold
        )
    )
}


@Composable
fun DropdownMenuComponent(viewModel: MainViewModel) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        modifier = Modifier
            .fillMaxWidth(.3F)
            .padding(top = 10.dp, bottom = 16.dp, start = 10.dp, end = 10.dp)
            .background(Color.LightGray, RoundedCornerShape(16.dp)),
        onClick = { expanded = !expanded }
    ) {
        Row {
            Text(text = viewModel.currencyName.value)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown",
            )
        }
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        viewModel.getCurrencyList().map { currency ->
            DropdownMenuItem(
                text = { Text(currency) },
                onClick = {
                    viewModel.updateCurrencyName(currency)
                    expanded = false
                }
            )
        }
    }

}

@Composable
fun TodoItemsContainer(
    todoItemsFlow: Flow<List<TodoItem>>,
    onItemDelete: (TodoItem) -> Unit,
) {
    val todoItems by todoItemsFlow.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier) {
        items(todoItems) { todoItem ->
            TodoItemRow(
                todoItem = todoItem,
                onItemDelete = onItemDelete
            )
        }
    }
}

@Composable
fun TodoItemRow(
    todoItem: TodoItem,
    onItemDelete: (TodoItem) -> Unit
) {
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
                    .padding(10.dp),
                text = "Title: ${todoItem.title}",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
            )
            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = "Author: ${todoItem.author}",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
            )
        }
        // delete book button on the right side
        IconButton(
            onClick = {
                onItemDelete.invoke(todoItem)
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
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = dialogText,
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
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

@Composable
fun StudentBookListContainer(
    todoItemsFlow: Flow<List<TodoItem>>,
    onButtonClicked: (TodoItem) -> Unit,
    onCheckoutButtonClicked: (TodoItem) -> Unit,
    onDetailsButtonClicked: (TodoItem) -> Unit
) {
    val todoItems by todoItemsFlow.collectAsState(initial = emptyList())

    if (todoItems.isEmpty()) {
        HeadingTextComponent(value = "No books available at this moment...")
    } else {
        LazyColumn(modifier = Modifier) {
            items(todoItems) { todoItem ->
                StudentBookListTodoItemRowWithSwipeToAction(
                    todoItem = todoItem,
                    onButtonClicked = {
                        onButtonClicked(todoItem)
                    },
                    onCheckoutButtonClicked = {
                        onCheckoutButtonClicked(todoItem)
                    },
                    onDetailsButtonClicked = {
                        onDetailsButtonClicked(todoItem)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun StudentBookListTodoItemRowWithSwipeToAction(
    todoItem: TodoItem,
    onButtonClicked: () -> Unit,
    onCheckoutButtonClicked: () -> Unit,
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
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = { onCheckoutButtonClicked.invoke() }) {
                    androidx.wear.compose.material.Icon(
                        imageVector = Icons.Rounded.AddShoppingCart,
                        contentDescription = "Checkout",
                        tint = Color.Blue,
                    )
                }
                IconButton(onClick = { onDetailsButtonClicked.invoke() }) {
                    androidx.wear.compose.material.Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = "",
                        tint = Color.Blue,
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
                    .background(AccentColor, RoundedCornerShape(16.dp))
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
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceAround
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
                        Text(
                            modifier = Modifier.padding(start = 16.dp, top = 20.dp),
                            text = if (todoItem.isCheckout) "Out of Stock" else "In Stock",
                            color = Color.Black,
                            textAlign =  TextAlign.Start,
                            fontSize = 10.sp
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







package com.example.LibraryManagementSystem.navigation

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalLifecycleOwner

private val LocalBackPressedDispatcher =
    staticCompositionLocalOf<OnBackPressedDispatcherOwner?> { null }

//Implementation of back button handler
private class ComposableBackNavigationHandler(enabled: Boolean) : OnBackPressedCallback(enabled) {
    lateinit var onBackPressed: () -> Unit

    override fun handleOnBackPressed() {
        onBackPressed()
    }
}

@Composable
internal fun ComposableHandler(
    enabled: Boolean = true,
    onBackPressed: () -> Unit
) {
    val dispatcher = LocalBackPressedDispatcher.current?.onBackPressedDispatcher
    val handler = remember { ComposableBackNavigationHandler(enabled) }

    CompositionLocalProvider(LocalBackPressedDispatcher provides LocalLifecycleOwner.current as ComponentActivity) {
        DisposableEffect(key1 = dispatcher) {
            dispatcher?.addCallback(handler)
            onDispose { handler.remove() }
        }
    }

    LaunchedEffect(key1 = enabled) {
        handler.isEnabled = enabled
        handler.onBackPressed = onBackPressed
    }
}


@Composable
internal fun SystemBackButtonHandler(onBackPressed: () -> Unit) {
    CompositionLocalProvider(LocalBackPressedDispatcher provides LocalLifecycleOwner.current as ComponentActivity) {
        ComposableHandler {
            onBackPressed()
        }
    }
}

package com.example.LibraryManagementSystem.ui.theme.constants

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val componentShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(0.dp)
)


val SmallDp: Dp = 4.dp
val MediumDp: Dp = 8.dp
val LargeDp: Dp = 16.dp

val TodoItemHeight: Dp = 48.dp
val TodoItemIconSize: Dp = 24.dp
val TodoItemActionButtonRippleRadius: Dp = 32.dp

val TodoInputBarHeight: Dp = 64.dp
val TodoInputBarFabSize: Dp = 40.dp

val OverlappingHeight = TodoInputBarHeight
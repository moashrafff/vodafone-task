package com.example.core.ui

import androidx.compose.ui.graphics.Color


sealed class ThemeColors(
    val bacground: Color,
    val surafce: Color,
    val primary: Color,
    val text: Color
)  {
    object Night: ThemeColors(
        bacground = colorA6A6A6,
        surafce = Color.White,
        primary = Color.White,
        text = colorA6A6A6
    )
    object Day: ThemeColors(
        bacground = Color.White,
        surafce = colorA6A6A6,
        primary = colorA6A6A6,
        text = Color.Black
    )
}


val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val color0076FF = Color(0xFF0076FF)
val colorA6A6A6 = Color(0xFFA6A6A6)
val colorE8E8E8 = Color(0xFFE8E8E8)
val colorFFB100 = Color(0xFFFFB100)
val color7F7F7F = Color(0xFF7F7F7F)
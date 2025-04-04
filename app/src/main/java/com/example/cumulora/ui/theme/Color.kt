package com.example.cumulora.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Purple = Color(0xff48319D)
val DarkPurple = Color(0xff1F1D47)
val Pink = Color(0xffC427FB)
val PinkLight = Color(0xffE0D9FF)
val Red = Color(0xFFE04E45)

val DarkLinColorOne = Color(0xff2E335A)
val DarkLinColorTwo = Color(0xff1C1B33)

val MediumLinColorOne = Color(0xff5936B4)
val MediumLinColorTwo = Color(0xff362A84)

val DarkCyan = Color(0xFF2D62AF)
val LightLinColorOne = Color(0xff427BD1)
val LightCyan = Color(0xFF7090C4)
val Cyan = Color(0xFF86A7E1)
val LighterCyan = Color(0xFFAFC1E0)
val LightRed = Color(0xFFF35555)

val DarkLinear = Brush.linearGradient(
    colors = listOf(DarkLinColorOne, DarkLinColorTwo),
    start = Offset(0f, 0f),
    end = Offset(1000f, 1000f)
)
val MediumLinear = Brush.linearGradient(
    colors = listOf(MediumLinColorOne, MediumLinColorTwo),
    start = Offset(0f, 0f),
    end = Offset(1000f, 1000f)
)
val LightLinear = Brush.linearGradient(
    colors = listOf(LightLinColorOne, LightRed),
    start = Offset(0f, 0f),
    end = Offset(1000f, 1000f)
)

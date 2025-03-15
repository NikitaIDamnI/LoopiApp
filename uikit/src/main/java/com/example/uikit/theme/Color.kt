package com.example.uikit.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

val Purple80 = androidx.compose.ui.graphics.Color(0xFFD0BCFF)
val PurpleGrey80 = androidx.compose.ui.graphics.Color(0xFFCCC2DC)
val Pink80 = androidx.compose.ui.graphics.Color(0xFFEFB8C8)

val Purple40 = androidx.compose.ui.graphics.Color(0xFF6650a4)
val PurpleGrey40 = androidx.compose.ui.graphics.Color(0xFF625b71)
val Pink40 = androidx.compose.ui.graphics.Color(0xFF7D5260)
val MainBackgroundColor = Color(0xFF312d29)
val ColorMainGreen = Color(0xFF17494D)
val ColorBottomLogging = Color(0xFFEBDDC9)
val TextColorLogging = Color(0xFF74966F)
val InactiveColor = Color(0xFFAEB6AE)
val LightBackgroundColo = Color(color = 0xFFafafaf)



val BrushShadow = Brush.linearGradient(
    colorStops = arrayOf(
        0.1f to Color.Transparent,
        0.4f to Color.Transparent,
//        0.3f to Color.Transparent,
//        0.3f to Color.Transparent,
        0.5f to Color.Black.copy(0.5f),
        0.6f to Color.Black,
        0.1f to Color.Black

    ),
    start = Offset(0f, 0f), // Верхняя точка (сверху)
    end = Offset(0f, 1000f), // Нижняя точка (вниз)
    tileMode = TileMode.Clamp
)

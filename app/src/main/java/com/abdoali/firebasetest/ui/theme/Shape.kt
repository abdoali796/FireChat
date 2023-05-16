package com.abdoali.firebasetest.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(2.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(18.dp)
)
val ShapesMassageStart= RoundedCornerShape(
    topStart = 20.dp ,
    bottomStart = 0.dp ,
    topEnd = 20.dp ,
    bottomEnd = 20.dp)

val ShapesMassageEnd= RoundedCornerShape(
    topStart = 20.dp ,
    bottomStart = 20.dp ,
    topEnd = 20.dp ,
    bottomEnd = 0.dp)
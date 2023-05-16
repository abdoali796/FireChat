package com.abdoali.firebasetest.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
//val Typography = Typography(
//    body1 = TextStyle(
//        fontFamily = FontFamily.Monospace,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    ),
////    / Other default text styles to override
//    button = TextStyle(
//        fontFamily = FontFamily.Cursive,
//        fontWeight = FontWeight.W500,
//        fontSize = 14.sp
//    ),
//    caption = TextStyle(
//        fontFamily = FontFamily.SansSerif,
//        fontWeight = FontWeight.Normal,
//        fontSize = 12.sp
//    )
//
//)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default ,
        fontWeight = FontWeight.Normal ,
        fontSize = 16.sp ,
        lineHeight = 24.sp ,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
package com.exyte.composesample.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.exyte.composesample.R


private val rubikFontFamily = FontFamily(
    Font(R.font.nunito_light, FontWeight.Light),
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_black, FontWeight.Black),
)

private val abrilFontFamily = FontFamily(
    Font(R.font.abril_fatface, FontWeight.Bold)
)

val Typography = Typography(
    defaultFontFamily = rubikFontFamily,
    h4 = TextStyle(
        fontFamily = abrilFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        letterSpacing = 0.25.sp
    ),
    h5 = TextStyle(
        fontFamily = abrilFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
)
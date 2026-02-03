package com.migueldk17.breeze.ui.components

import android.R
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.theme.NavyBlue
import com.migueldk17.breeze.ui.theme.regularTextModoClaro

@Composable
fun BreezeRegularText(
    modifier: Modifier = Modifier,
    text: String,
    size: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = regularTextModoClaro,
    style: TextStyle = LocalTextStyle.current
){
    Text(
        modifier = modifier,
        text = text,
        fontSize = size,
        fontWeight = fontWeight,
        color = color,
        style = style

    )
}
package com.migueldk17.breeze.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.theme.DeepSkyBlue
import com.migueldk17.breeze.ui.theme.SkyBlue

@Composable
fun BreezeOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,onClick: () -> Unit,
    enabled: Boolean = true,
    colors: Color = if (!isSystemInDarkTheme()) SkyBlue else DeepSkyBlue
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.White,
            containerColor = colors,
            disabledContentColor = Color.LightGray,
            disabledContainerColor = Color.LightGray
        )){
        Text(text,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}
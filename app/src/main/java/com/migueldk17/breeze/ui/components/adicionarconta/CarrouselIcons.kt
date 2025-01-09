package com.migueldk17.breeze.ui.components.adicionarconta

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.ui.theme.PastelLightBlue

@Composable
fun CarrouselIcons(){
    Card(
        modifier = Modifier.size(width = 382.dp, height = 68.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
    //Row e HorizontalPager aqui
    }
}
@Preview(showBackground = true)
@Composable
private fun Preview(){
    BreezeTheme {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(PastelLightBlue),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
         CarrouselIcons()
        }
    }
}
package com.migueldk17.breeze.ui.layouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.migueldk17.breeze.ui.animation.OndasAnimadas

@Composable
fun WaveScreen(navController: NavController){
    OndasAnimadas(
        baseColor = Color.Yellow,
        onWaveEnd = { }
    )
}
package com.migueldk17.breeze.ui.features.adicionarconta.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AdicionarContaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = NavGraph.Passo1.route,
        modifier = modifier
            .padding()
            .fillMaxHeight(0.9f),
        enterTransition = { fadeIn(tween(700)) },
        exitTransition = { fadeOut(tween(700)) },
        popEnterTransition = { fadeIn(tween(700))},
        popExitTransition = { fadeOut(tween(700))}
    ) {
        adicionarContaGraph(navController)
    }
}
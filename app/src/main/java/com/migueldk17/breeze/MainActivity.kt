package com.migueldk17.breeze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.migueldk17.breeze.ui.components.BreezeBottomBar
import com.migueldk17.breeze.ui.components.BreezeTopAppBar
import com.migueldk17.breeze.ui.layouts.Configuracoes
import com.migueldk17.breeze.ui.layouts.Historico
import com.migueldk17.breeze.ui.layouts.PaginaInicial
import com.migueldk17.breeze.ui.layouts.EditarValorConta
import com.migueldk17.breeze.ui.theme.BreezeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BreezeTheme {
                val navController = rememberNavController()
                val scroll = rememberScrollState()
                Scaffold(modifier = Modifier
                    .fillMaxSize(),
                    topBar = {
                        BreezeTopAppBar()
                    },
                    bottomBar = {
                        BreezeBottomBar(navController)
                    }){ innerPadding ->
                    val navGraph = navController.createGraph(startDestination = Screen.PaginaInicial.route){
                        composable(Screen.PaginaInicial.route) { PaginaInicial(navController) }
                        composable(Screen.Historico.route) { Historico() }
                        composable(Screen.Configuracoes.route) { Configuracoes() }
                        composable(Screen.WaveScreen.route) { EditarValorConta() }
                    }
                    NavHost(
                        navController = navController,
                        graph = navGraph,
                        modifier = Modifier.padding(innerPadding)
                            .verticalScroll(scroll),
                        enterTransition = { fadeIn(animationSpec = tween(700)) },
                        exitTransition = { fadeOut(animationSpec = tween(700)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(700)) },
                        popExitTransition = { fadeOut(animationSpec = tween(700)) }



                    )
                }
            }
        }
    }

}


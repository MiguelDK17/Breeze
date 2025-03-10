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
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.migueldk17.breeze.ui.components.BreezeBottomBar
import com.migueldk17.breeze.ui.components.BreezeTopAppBar
import com.migueldk17.breeze.features.configuracoes.ui.layouts.Configuracoes
import com.migueldk17.breeze.features.historico.ui.layouts.Historico
import com.migueldk17.breeze.features.paginainicial.ui.layouts.PaginaInicial
import com.migueldk17.breeze.ui.theme.BreezeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            //Tema do app
            BreezeTheme {
                //Cria o navController
                val navController = rememberNavController()
                //Pega a rota atual do navController
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                //Caso a rota depender das barras de navegação elas irão aparecer, do contrário não
                val showToolbarAndToolbar = when(currentRoute){
                    Screen.PaginaInicial.route,
                        Screen.Historico.route,
                        Screen.Configuracoes.route -> true

                    else -> false
                }
                Scaffold(modifier = Modifier
                    .fillMaxSize(),
                    //Topbar do app usando o when anterior
                    topBar = {
                        if (showToolbarAndToolbar){
                            BreezeTopAppBar()
                        }
                    },
                    //Bottombar do app usando o when anterior
                    bottomBar = {
                        if (showToolbarAndToolbar){
                            BreezeBottomBar(navController)
                        }
                    }){ innerPadding ->

                    //Cria o navGraph com a rota inicial como a PaginaInicial
                    val navGraph = navController.createGraph(startDestination = Screen.PaginaInicial.route) {
                        //Passa o viewModel como argumento para PaginaInicial para que seja feita o envio da cor dos cards
                        composable(Screen.PaginaInicial.route) {
                            PaginaInicial(
                                navController
                            )
                        }
                        composable(Screen.Historico.route) { Historico() }
                        composable(Screen.Configuracoes.route) { Configuracoes() }
                    }
                    //NavHost contendo o navController, as rotas, o scroll vertical e as animações, os layouts compose irão compartilhar dessas propriedades
                    //Se estiverem na rota do navGraph
                    NavHost(
                        navController = navController,
                        graph = navGraph,
                        modifier = Modifier
                            .padding(innerPadding),
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


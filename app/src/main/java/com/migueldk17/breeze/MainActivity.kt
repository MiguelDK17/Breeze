package com.migueldk17.breeze


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.migueldk17.breeze.ui.components.BreezeBottomBar
import com.migueldk17.breeze.ui.components.BreezeTopAppBar
import com.migueldk17.breeze.ui.features.configuracoes.ui.layouts.Configuracoes
import com.migueldk17.breeze.ui.features.historico.ui.layouts.Historico
import com.migueldk17.breeze.ui.features.paginainicial.navigation.routes.Screen
import com.migueldk17.breeze.ui.features.paginainicial.ui.layouts.PaginaInicial
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
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route

                val context = LocalContext.current

                //Caso a rota depender das barras de navegação elas irão aparecer, do contrário não
                val showToolbarAndToolbar = when (currentRoute) {
                    Screen.PaginaInicial.route,
                    Screen.Historico.route,
                    Screen.Configuracoes.route -> true

                    else -> false
                }

                BackHandler {
                    if (currentRoute == Screen.PaginaInicial.route) {
                        (context as? MainActivity)?.finish()
                    } else {
                        navController.popBackStack()
                    }
                }
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    //Topbar do app usando o when anterior
                    topBar = {
                        if (showToolbarAndToolbar) {
                            BreezeTopAppBar()
                        }
                    },
                    //Bottombar do app usando o when anterior
                    bottomBar = {
                        if (showToolbarAndToolbar) {
                            BreezeBottomBar(navController)
                        }
                    }) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.PaginaInicial.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.PaginaInicial.route) {
                            PaginaInicial()
                        }
                        composable(Screen.Historico.route) {
                            Historico()
                        }
                        composable(Screen.Configuracoes.route) {
                            Configuracoes()
                        }
                    }
                }
            }
        }
    }
}


package com.migueldk17.breeze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.migueldk17.breeze.ui.layouts.Configuracoes
import com.migueldk17.breeze.ui.layouts.Historico
import com.migueldk17.breeze.ui.layouts.PaginaInicial
import com.migueldk17.breeze.ui.layouts.adicionarconta.AdicionarContaOpcional
import com.migueldk17.breeze.ui.theme.BreezeTheme

class MainActivity3: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BreezeTheme {
                //Cria o navController
                val navController = rememberNavController()
                //Cria o scroll que será usado futuramente
                val scroll = rememberScrollState()
                //Pega a rota atual do navController
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                Scaffold {paddingValues ->
                    //Cria o navGraph com a rota inicial como a PaginaInicial
                    val navGraph = navController.createGraph(startDestination = NavGraph2.Passo1.route) {
                        //Passa o viewModel como argumento para PaginaInicial para que seja feita o envio da cor dos cards
                        composable(NavGraph2.Passo1.route) {
                            AdicionarContaOpcional(
                                navController
                            )
                        }
                    }
                    //NavHost contendo o navController, as rotas, o scroll vertical e as animações, os layouts compose irão compertilhar dessas propriedades
                    //Se estiverem na rota do navGraph
                    NavHost(
                        navController = navController,
                        graph = navGraph,
                        modifier = Modifier
                            .padding(paddingValues)
                            .verticalScroll(scroll),
                        enterTransition = { fadeIn(animationSpec = tween(700)) },
                        exitTransition = { fadeOut(animationSpec = tween(700)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(700)) },
                        popExitTransition = { fadeOut(animationSpec = tween(700)) }
                }
            }
        }
    }
}
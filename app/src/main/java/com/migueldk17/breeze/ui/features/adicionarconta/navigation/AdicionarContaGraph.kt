package com.migueldk17.breeze.ui.features.adicionarconta.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts.Final
import com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts.Passo1
import com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts.Passo2
import com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts.Passo3
import com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts.Passo4
import com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts.Passo5



fun NavGraphBuilder.adicionarContaGraph(
    navController: NavController
) {
    navigation(
        route = NavGraphs.AdicionarConta, //Rota pai do NavGraph
        startDestination = NavGraph.Passo1.route // Primeira rota do navGraph
    ) {
        composable(NavGraph.Passo1.route) { backStackEntry ->
            //backStack do NavGraph pai
            val parentEntry = remember(backStackEntry) { //Remember entra aqui para evitar de buscar o entry toda recomposicao
                navController.getBackStackEntry(NavGraphs.AdicionarConta)
            }
            //Injeta um valor global(parentEntry) no escopo da UI para salvar o ViewModel entre as telas
            CompositionLocalProvider(LocalViewModelStoreOwner provides parentEntry) {
                Passo1(
                    navToPasso2 = {
                        navController.navigate(NavGraph.Passo2.route) //Lambda que navega pro Passo2
                    }
                )
            }
        }

        composable(NavGraph.Passo2.route) { backStackEntry ->
            //backStack do NavGraph pai
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavGraphs.AdicionarConta)
            }
            //Injeta um valor global(parentEntry) no escopo da UI para salvar o ViewModel entre as telas
            CompositionLocalProvider(LocalViewModelStoreOwner provides parentEntry) {
                Passo2(
                    navToPasso3 = {
                        navController.navigate(NavGraph.Passo3.route) //Lambda que navega pro Passo3
                    },
                    currentState = NavGraph.Passo2.route //Rota atual do Passo onde o usuário está
                )
            }
        }

        composable(NavGraph.Passo3.route) { backStackEntry ->
            //backStack do NavGraph pai
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavGraphs.AdicionarConta)
            }
            //Injeta um valor global(parentEntry) no escopo da UI para salvar o ViewModel entre as telas
            CompositionLocalProvider(LocalViewModelStoreOwner provides parentEntry) {
                Passo3(
                    navToPasso4 = {
                        navController.navigate(NavGraph.Passo4.route)
                    },
                    currentState = NavGraph.Passo3.route
                )
            }
        }

        composable(NavGraph.Passo4.route) { backStackEntry ->
            //backStack do NavGraph pai
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavGraphs.AdicionarConta)
            }
            //Injeta um valor global(parentEntry) no escopo da UI para salvar o ViewModel entre as telas
            CompositionLocalProvider(LocalViewModelStoreOwner provides parentEntry) {
                Passo4(
                    navToPasso5 = {
                        navController.navigate(NavGraph.Passo5.route)
                    }
                )
            }
        }

        composable(NavGraph.Passo5.route) { backStackEntry ->
            //backStack do NavGraph pai
            val parentEntry = remember(backStackEntry) {
               navController.getBackStackEntry(NavGraphs.AdicionarConta)
           }
            //Injeta um valor global(parentEntry) no escopo da UI para salvar o ViewModel entre as telas
            CompositionLocalProvider(LocalViewModelStoreOwner provides parentEntry) {
                Passo5(
                    navToFinal = {
                        navController.navigate(NavGraph.Final.route)
                    },
                    currentState = NavGraph.Passo5.route
                )
            }
        }

        composable(NavGraph.Final.route) { backStackEntry ->
            //backStack do NavGraph pai
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavGraphs.AdicionarConta)
            }
            //Injeta um valor global(parentEntry) no escopo da UI para salvar o ViewModel entre as telas
            CompositionLocalProvider(LocalViewModelStoreOwner provides parentEntry) {
                Final()
            }
        }
    }
}

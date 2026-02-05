package com.migueldk17.breeze.ui.features.adicionarconta.navigation

import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel

fun NavGraphBuilder.adicionarContaGraph(
    navController: NavController
) {
    navigation(
        route = NavGraphs.AdicionarConta,
        startDestination = NavGraph.Passo1.route
    ) {
        composable(NavGraph.Passo1.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavGraphs.AdicionarConta)
            }

            val viewModel: AdicionarContaViewModel =
                hiltViewModel(parentEntry)

            Passo1(
                navToPasso2 = {
                    navController.navigate(NavGraph.Passo2.route)
                }
            )
        }

        composable(NavGraph.Passo2.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavGraphs.AdicionarConta)
            }
            val viewModel: AdicionarContaViewModel =
                hiltViewModel(parentEntry)

            Passo2(
                navToPasso3 = {
                    navController.navigate(NavGraph.Passo3.route)
                },
                currentState = NavGraph.Passo3.route
            )
        }

        composable(NavGraph.Passo3.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavGraphs.AdicionarConta)
            }
            val viewModel: AdicionarContaViewModel =
                hiltViewModel(parentEntry)

            Passo3(
                navToPasso4 = {
                    navController.navigate(NavGraph.Passo4.route)
                },
                currentState = NavGraph.Passo4.route
            )
        }

        composable(NavGraph.Passo4.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavGraphs.AdicionarConta)
            }
            val viewModel: AdicionarContaViewModel =
                hiltViewModel(parentEntry)

            Passo4(
                navToPasso5 = {
                    navController.navigate(NavGraph.Passo5.route)
                }
            )
        }

        composable(NavGraph.Passo5.route) { backStackEntry ->
           val parentEntry = remember(backStackEntry) {
               navController.getBackStackEntry(NavGraphs.AdicionarConta)
           }
            val viewModel: AdicionarContaViewModel =
                hiltViewModel(parentEntry)

            Passo5(
                navToFinal = {
                    navController.navigate(NavGraph.Final.route)
                },
                currentState = NavGraph.Passo5.route
            )
        }

        composable(NavGraph.Final.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavGraphs.AdicionarConta)
            }
            val viewModel: AdicionarContaViewModel =
                hiltViewModel(parentEntry)

            Final()
        }
    }
}
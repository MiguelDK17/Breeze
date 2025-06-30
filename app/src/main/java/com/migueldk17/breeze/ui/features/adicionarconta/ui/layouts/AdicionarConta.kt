package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.components.TitleText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.CardPrincipal
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel
import com.migueldk17.breeze.ui.theme.SkyBlue

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AdicionarConta(
    modifier : Modifier = Modifier,
    viewModel: AdicionarContaViewModel = hiltViewModel()
){
    //Cria o navController
    val navController = rememberNavController()
     //Pega a rota atual do navController
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    //Column principal do ciclo de vida AdicionarContaOpcionaç
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        //Text de título
        TitleText(
            "Adicionar Conta"
        )
        Spacer(modifier = Modifier.size(20.dp))
        //Contagem de passos
        Text(
            retornaPasso(currentRoute),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(15.dp))
        //LinearProgressIndicator que evolui conforme os passos
        LinearWavyProgressIndicator(
            progress = {
                progressIndicatorEvolution(currentRoute)
            },
            modifier = Modifier.fillMaxWidth(),
            color = SkyBlue,
        )
        Spacer(modifier = Modifier.size(10.dp))
        //Card Principal que abriga os passos que serão gerenciados pelo NavigationCompose

        CardPrincipal{
            InstanciaRotasAdicionarConta(navController, viewModel)
        }

    }

}

private fun progressIndicatorEvolution(currentRoute: String?): Float{
    val progress = when(currentRoute){
        NavGraph2.Passo1.route -> {
            0.2f
        }
        NavGraph2.Passo2.route -> {
            0.4f
        }
        NavGraph2.Passo3.route -> {
            0.6f
        }
        NavGraph2.Passo4.route -> {
            0.8f
        }
        NavGraph2.Passo5.route -> {
            0.9f
        }
        NavGraph2.Final.route -> {
            1f
        }
        else -> {
            0f
        }
    }
    return progress
}

private fun retornaPasso(currentRoute: String?): String{
    val text = when(currentRoute){
        NavGraph2.Passo1.route -> {
            "Passo 1 de 5"
        }
        NavGraph2.Passo2.route -> {
            "Passo 2 de 5"
        }
        NavGraph2.Passo3.route -> {
            "Passo 3 de 5"
        }
        NavGraph2.Passo4.route -> {
            "Passo 4 de 5"
        }
        NavGraph2.Passo5.route -> {
            "Passo 5 de 5"
        }
        NavGraph2.Final.route -> {
            "Final"
        }
        else -> {
            "Passo Inválido"
        }
    }
    return text
}
@Composable
private fun InstanciaRotasAdicionarConta(
    navController: NavHostController,
    viewModel: AdicionarContaViewModel){
    //Cria o navGraph com a rota inicial como a PaginaInicial
    val navGraph = navController.createGraph(startDestination = NavGraph2.Passo1.route) {
        //Passa o viewModel como argumento para PaginaInicial para que seja feita o envio da cor dos cards
        composable(NavGraph2.Passo1.route) {
            Passo1(
                navToPasso2 = {
                navController.navigate(NavGraph2.Passo2.route)
            }, viewModel
            )
        }
        composable(NavGraph2.Passo2.route) {
            Passo2(
                navToPasso3 = {
                    navController.navigate(NavGraph2.Passo3.route)},
                currentState = navController.currentBackStackEntryAsState().value?.destination?.route,
                viewModel
            )
        }
        composable(NavGraph2.Passo3.route){
            Passo3(
                navToPasso4 = {
                navController.navigate(NavGraph2.Passo4.route)
            },
                currentState = navController.currentBackStackEntryAsState().value?.destination?.route,
                viewModel
            )
        }
        composable(NavGraph2.Passo4.route){
            Passo4(navToPasso5 = {
                navController.navigate(NavGraph2.Passo5.route)
            }, viewModel)
        }
        composable(NavGraph2.Passo5.route){
            Passo5(navToFinal = {
                navController.navigate(NavGraph2.Final.route)
            },
                currentState = navController.currentBackStackEntryAsState().value?.destination?.route,
                viewModel)
        }
        composable(NavGraph2.Final.route){
            Final( viewModel)
        }
    }
    //NavHost contendo o navController, as rotas, o scroll vertical e as animações, os layouts compose irão compertilhar dessas propriedades
    //Se estiverem na rota do navGraph
    NavHost(
        navController = navController,
        graph = navGraph,
        modifier = Modifier
            .padding()
            .fillMaxHeight(0.9f),
        enterTransition = { fadeIn(animationSpec = tween(700)) },
        exitTransition = { fadeOut(animationSpec = tween(700)) },
        popEnterTransition = { fadeIn(animationSpec = tween(700)) },
        popExitTransition = { fadeOut(animationSpec = tween(700)) }
    )
}
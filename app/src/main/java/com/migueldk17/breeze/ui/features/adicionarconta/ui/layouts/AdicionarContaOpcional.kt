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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
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
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.CardPrincipal
import com.migueldk17.breeze.viewmodels.BreezeViewModel

@Composable
fun AdicionarContaOpcional(
    modifier : Modifier = Modifier,
    viewModel: BreezeViewModel = hiltViewModel()
){
    //Cria o navController
    val navController = rememberNavController()
    //Cria o scroll que será usado futuramente
    val scroll = rememberScrollState()
    //Pega a rota atual do navController
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    //Column principal do ciclo de vida AdicionarContaOpcionaç
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        //Text de título
        Text(
            "Adicionar Conta Opcional",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp,
            color = Black)
        Spacer(modifier = Modifier.size(20.dp))
        //Contagem de passos
        Text(
            retornaPasso(currentRoute),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(15.dp))
        //LinearProgressIndicator que evolui conforme os passos
        LinearProgressIndicator(
            progress = {
                progressIndicatorEvolution(currentRoute)
            },
            modifier = Modifier.fillMaxWidth(),
            color = Green,
        )
        Spacer(modifier = Modifier.size(10.dp))
        //Card Principal que abriga os passos que serão gerenciados pelo NavigationCompose

        CardPrincipal{
            InstanciaRotasAdicionarConta(navController, scroll, viewModel)
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
private fun InstanciaRotasAdicionarConta(navController: NavHostController, scroll: ScrollState, viewModel: BreezeViewModel){
    //Cria o navGraph com a rota inicial como a PaginaInicial
    val navGraph = navController.createGraph(startDestination = NavGraph2.Passo1.route) {
        //Passa o viewModel como argumento para PaginaInicial para que seja feita o envio da cor dos cards
        composable(NavGraph2.Passo1.route) {
            Passo1(navController, viewModel)
        }
        composable(NavGraph2.Passo2.route) {
            Passo2(navController, viewModel)
        }
        composable(NavGraph2.Passo3.route){
            Passo3(navController, viewModel)
        }
        composable(NavGraph2.Passo4.route){
            Passo4(navController, viewModel)
        }
        composable(NavGraph2.Passo5.route){
            Passo5(navController, viewModel)
        }
        composable(NavGraph2.Final.route){
            Final(navController, viewModel)
        }
    }
    //NavHost contendo o navController, as rotas, o scroll vertical e as animações, os layouts compose irão compertilhar dessas propriedades
    //Se estiverem na rota do navGraph
    NavHost(
        navController = navController,
        graph = navGraph,
        modifier = Modifier
            .padding()
            .verticalScroll(scroll)
            .fillMaxHeight(0.9f),
        enterTransition = { fadeIn(animationSpec = tween(700)) },
        exitTransition = { fadeOut(animationSpec = tween(700)) },
        popEnterTransition = { fadeIn(animationSpec = tween(700)) },
        popExitTransition = { fadeOut(animationSpec = tween(700)) }
    )
}
package com.migueldk17.breeze.ui.layouts.adicionarconta

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.components.adicionarconta.CardPrincipal

@Composable
fun AdicionarContaOpcional(
    modifier : Modifier = Modifier
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
        Text("Passo 1 de 5",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(15.dp))
        //LinearProgressIndicator que evolui conforme os passos
        LinearProgressIndicator(
            progress = {
                0.2f
            },
            modifier = Modifier.fillMaxWidth(),
            color = Green,
        )
        Spacer(modifier = Modifier.size(10.dp))
        //Card Principal que abriga os passos que serão gerenciados pelo NavigationCompose

        CardPrincipal{
            InstanciaRotasAdicionarConta(navController, scroll)
        }


    }

}
@Composable
private fun InstanciaRotasAdicionarConta(navController: NavHostController, scroll: ScrollState){
    //Cria o navGraph com a rota inicial como a PaginaInicial
    val navGraph = navController.createGraph(startDestination = NavGraph2.Passo1.route) {
        //Passa o viewModel como argumento para PaginaInicial para que seja feita o envio da cor dos cards
        composable(NavGraph2.Passo1.route) {
            Passo1(navController)
        }
        composable(NavGraph2.Passo2.route) {
            Passo2(navController)
        }
        composable(NavGraph2.Passo3.route){
            Passo3(navController)
        }
        composable(NavGraph2.Passo4.route){
            Passo4(navController)
        }
        composable(NavGraph2.Passo5.route){
            Passo5(navController)
        }
        composable(NavGraph2.Final.route){
            Final(navController)
        }
    }
    //NavHost contendo o navController, as rotas, o scroll vertical e as animações, os layouts compose irão compertilhar dessas propriedades
    //Se estiverem na rota do navGraph
    NavHost(
        navController = navController,
        graph = navGraph,
        modifier = Modifier
            .padding()
            .verticalScroll(scroll),
        enterTransition = { fadeIn(animationSpec = tween(700)) },
        exitTransition = { fadeOut(animationSpec = tween(700)) },
        popEnterTransition = { fadeIn(animationSpec = tween(700)) },
        popExitTransition = { fadeOut(animationSpec = tween(700)) }
    )
}
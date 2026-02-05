package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.migueldk17.breeze.ui.features.adicionarconta.navigation.AdicionarContaNavHost
import com.migueldk17.breeze.ui.features.adicionarconta.navigation.NavGraph
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.CardPrincipal
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel
import com.migueldk17.breeze.ui.theme.SkyBlue

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AdicionarConta(
    navController: NavHostController,
    modifier : Modifier = Modifier
){
    //Pega a rota atual do navController
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    //Column principal do ciclo de vida AdicionarContaOpcional
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {

        //Contagem de passos
        Text(
            retornaPasso(currentRoute),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)

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
                AdicionarContaNavHost(navController = navController)
            }
        }
}

private fun progressIndicatorEvolution(currentRoute: String?): Float{
    val progress = when(currentRoute){
        NavGraph.Passo1.route -> {
            0.2f
        }
        NavGraph.Passo2.route -> {
            0.4f
        }
        NavGraph.Passo3.route -> {
            0.6f
        }
        NavGraph.Passo4.route -> {
            0.8f
        }
        NavGraph.Passo5.route -> {
            0.9f
        }
        NavGraph.Final.route -> {
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
        NavGraph.Passo1.route -> {
            "Passo 1 de 5"
        }
        NavGraph.Passo2.route -> {
            "Passo 2 de 5"
        }
        NavGraph.Passo3.route -> {
            "Passo 3 de 5"
        }
        NavGraph.Passo4.route -> {
            "Passo 4 de 5"
        }
        NavGraph.Passo5.route -> {
            "Passo 5 de 5"
        }
        NavGraph.Final.route -> {
            "Final"
        }
        else -> {
            "Passo Inválido"
        }
    }
    return text
}
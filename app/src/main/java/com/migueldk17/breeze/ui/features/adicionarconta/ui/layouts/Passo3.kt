package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.migueldk17.breeze.BreezeIconLists
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.PersonalizationCard
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.adicionaCorPadrao
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.carrouselIcons
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.insereIconeNoViewModel
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel

@Composable
fun Passo3(navController: NavController, viewModel: AdicionarContaViewModel = hiltViewModel()){

    val nomeConta = viewModel.nomeConta.collectAsState().value
    val icone = viewModel.iconeCardConta.collectAsState().value

    //Passo atual
    val currentState = navController.currentBackStackEntryAsState().value?.destination?.route

    //Column do Passo3
    Column {
        Column(
            modifier = Modifier
                .padding(25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DescriptionText("Assim está ficando o card da sua nova conta:")
            Spacer(modifier = Modifier.size(25.dp))
            //Card que evolui conforme o usuario vai adicionando informações
            PersonalizationCard(nomeConta = nomeConta, icone = icone)
            Spacer(modifier = Modifier.size(26.dp))

            DescriptionText("Escolha uma cor para o ícone que combine com o estilo da sua conta!")

            Spacer(modifier = Modifier.size(8.dp))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Carrossel de icones
            val iconCarrousel = carrouselIcons(BreezeIconLists.getVibrantColorIcons())
            Spacer(modifier = Modifier.size(71.dp))
            OutlinedButton(
                onClick = {
                    adicionaCorPadrao(currentState, viewModel)
                    navController.navigate(NavGraph2.Passo4.route)
                }
            ){
                Text("Ou usar a cor padrão")
            }
            Spacer(modifier = Modifier.size(30.dp))
            //Botão para avançar de tela
            Button(onClick = {
                insereIconeNoViewModel(currentState, viewModel, iconCarrousel)
                navController.navigate(NavGraph2.Passo4.route)
            }, enabled = true
            ) {
                Text("Avançar")
            }
        }
    }

}


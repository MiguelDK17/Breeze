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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.migueldk17.breeze.BreezeIconLists
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.PersonalizationCard
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.adicionaCorPadrao
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.carrouselIcons
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.insereIconeNoViewModel
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel
import com.migueldk17.breeze.ui.utils.formataValorConta
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun Passo5(
    navToFinal: () -> Unit,
    currentState: String?,
    viewModel: AdicionarContaViewModel = hiltViewModel()) {

    val nomeConta = viewModel.nomeConta.collectAsStateWithLifecycle().value
    val icone = viewModel.iconeCardConta.collectAsStateWithLifecycle().value
    val corIcone = viewModel.corIcone.collectAsStateWithLifecycle().value
    val valorConta = viewModel.valorConta.collectAsStateWithLifecycle().value
    //Pega o valor da conta do viewModel e formata para valores monetários
    val valorMascarado = formataValorConta(valorConta)


    Column {
        //Column do Passo5
        Column(
            modifier = Modifier
                .padding(25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            DescriptionText("Assim está ficando o card da sua nova conta:")

            Spacer(modifier = Modifier.size(25.dp))
            //Card que evolui conforme o usuario vai adicionando informações
            PersonalizationCard(nomeConta = nomeConta, icone = icone, corIcone = corIcone, valorMascarado = valorMascarado)
            Spacer(modifier = Modifier.size(26.dp))

            DescriptionText("Último passo! Escolha a cor do card para deixar tudo com a sua cara!",)
            Spacer(modifier = Modifier.size(35.dp))

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Carrossel de icones
            val iconCarrousel = carrouselIcons(BreezeIconLists.getSoftColorIcons())
            Spacer(modifier = Modifier.size(74.dp))
            OutlinedButton(
                onClick = {
                    adicionaCorPadrao(currentState, viewModel)
                    navToFinal()
                }
            ){
                Text("Ou usar a cor padrão")
            }
            Spacer(modifier = Modifier.size(30.dp))
            //Botão para avançar de tela
            Button(onClick = {
                insereIconeNoViewModel(currentState, viewModel, iconCarrousel)
                navToFinal()
            }, enabled = true
            ) {
                Text("Avançar")
            }
        }
    }
}
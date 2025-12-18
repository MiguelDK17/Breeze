package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.PersonalizationCard
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.carrouselIcons
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.insereIconeNoViewModel
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel
import com.migueldk17.breeze.ui.theme.blackPoppinsDarkMode
import com.migueldk17.breeze.ui.theme.blackPoppinsLightMode

@Composable
fun Passo2(
    navToPasso3: () -> Unit,
    currentState: String?,
    viewModel: AdicionarContaViewModel = hiltViewModel()){
    val nomeConta = viewModel.nomeConta.collectAsState().value


    //Column do Passo2
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
            PersonalizationCard(nomeConta = nomeConta)

            Spacer(modifier = Modifier.size(26.dp))

            DescriptionText("Agora escolha um nome para representar essa conta.")
            Spacer(Modifier.size(18.dp))
            Text("Qual combina melhor ?",
                style = MaterialTheme.typography.bodySmall,
                color = if (!isSystemInDarkTheme()) blackPoppinsLightMode else blackPoppinsDarkMode
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            //Carrossel de icones
            val iconCarrousel = carrouselIcons(BreezeIconLists.getLinearIcons())
            Spacer(modifier = Modifier.size(71.dp))
            //Botão para avançar de tela
            Button(onClick = {
                insereIconeNoViewModel(currentState, viewModel, iconCarrousel)
                navToPasso3()
            }, enabled = true
            ) {
                Text("Avançar")
            }
        }
    }


}
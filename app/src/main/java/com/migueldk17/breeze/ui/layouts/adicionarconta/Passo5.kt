package com.migueldk17.breeze.ui.layouts.adicionarconta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.BreezeIconLists
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.components.adicionarconta.carrouselIcons
import com.migueldk17.breeze.ui.components.adicionarconta.insereIconeNoViewModel
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.theme.blackPoppins
import com.migueldk17.breeze.viewmodels.BreezeViewModel
import java.util.Locale

@Composable
fun Passo5(navController: NavController, viewModel: BreezeViewModel = hiltViewModel()) {

    val nomeConta = viewModel.nomeConta.collectAsState().value
    val icone = viewModel.iconeCardConta.collectAsState().value
    val corIcone = viewModel.corIcone.collectAsState().value
    val valorConta = viewModel.valorConta.collectAsState().value
    //Pega o valor da conta do viewModel e formata para valores monetários
    val valorMascarado = String.format(Locale.getDefault(),"R$: %.2f", valorConta)

    val currentState = navController.currentBackStackEntryAsState().value?.destination?.route


    Column {
        //Column do Passo5
        Column(
            modifier = Modifier
                .padding(25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Assim está ficando o card da sua nova conta:",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.size(25.dp))
            //Card que evolui conforme o usuario vai adicionando informações
            Card(
                modifier = Modifier
                    .size(width = 342.dp, height = 80.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardColors(
                    containerColor = PastelLightBlue,
                    contentColor = Color.Black,
                    disabledContentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )


            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                ) {
                    BreezeIcon(
                        icone,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp),
                        color = corIcone
                    )
                    Spacer(modifier = Modifier.size(15.dp))
                    Column {
                        Text(
                            nomeConta,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Normal,
                                color = blackPoppins,
                            ),
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            valorMascarado,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Normal,
                                color = blackPoppins
                            ),
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(26.dp))

            Text(
                "Último passo! Escolha a cor do card para deixar tudo com a sua cara!",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.size(35.dp))

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Carrossel de icones
            val iconCarrousel =carrouselIcons(BreezeIconLists.getColorIcons())
            Spacer(modifier = Modifier.size(74.dp))
            //Botão para avançar de tela
            Button(
                onClick = {
                    insereIconeNoViewModel(currentState, viewModel, iconCarrousel)
                    navController.navigate(NavGraph2.Final.route)
                }, enabled = true
            ) {
                Text("Avançar")
            }
        }
    }
}
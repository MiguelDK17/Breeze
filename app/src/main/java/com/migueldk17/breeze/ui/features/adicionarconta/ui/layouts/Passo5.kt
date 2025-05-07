package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

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
import androidx.compose.material3.OutlinedButton
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
import com.migueldk17.breeze.BreezeIconLists
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.adicionaCorPadrao
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.carrouselIcons
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.insereIconeNoViewModel
import com.migueldk17.breeze.ui.utils.formataValorConta
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.theme.blackPoppinsLightMode
import com.migueldk17.breeze.viewmodels.BreezeViewModel

@Composable
fun Passo5(navController: NavController, viewModel: BreezeViewModel = hiltViewModel()) {

    val nomeConta = viewModel.nomeConta.collectAsState().value
    val icone = viewModel.iconeCardConta.collectAsState().value
    val corIcone = viewModel.corIcone.collectAsState().value
    val valorConta = viewModel.valorConta.collectAsState().value
    //Pega o valor da conta do viewModel e formata para valores monetários
    val valorMascarado = formataValorConta(valorConta)

    val currentState = navController.currentBackStackEntryAsState().value?.destination?.route


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
                                color = blackPoppinsLightMode,
                            ),
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            valorMascarado,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Normal,
                                color = blackPoppinsLightMode
                            ),
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
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
                    navController.navigate(NavGraph2.Final.route)
                }
            ){
                Text("Ou usar a cor padrão")
            }
            Spacer(modifier = Modifier.size(30.dp))
            //Botão para avançar de tela
            Button(onClick = {
                insereIconeNoViewModel(currentState, viewModel, iconCarrousel)
                navController.navigate(NavGraph2.Final.route)
            }, enabled = true
            ) {
                Text("Avançar")
            }
        }
    }
}
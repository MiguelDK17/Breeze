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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.components.adicionarconta.CarrouselIcons
import com.migueldk17.breeze.ui.icons.BreezeIcon
import com.migueldk17.breeze.ui.icons.BreezeIcons
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.theme.blackPoppins

@Composable
fun Passo5() {
    //Lista de icones de tipo BreezeIcons
    val iconList = listOf(
        BreezeIcons.Colors.IconOrange,
        BreezeIcons.Colors.IconYellow,
        BreezeIcons.Colors.IconGreen,
        BreezeIcons.Colors.IconGreenCyan,
        BreezeIcons.Colors.IconTurquoise)
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
                .size(width = 342.dp, height = 80.dp)
            ,
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
                    BreezeIcons.Linear.BookLinear,
                    contentDescription = "Ícone de Livro",
                    modifier = Modifier
                        .size(50.dp)
                )
                Spacer(modifier = Modifier.size(15.dp))
                Column {
                    Text("Nome da conta",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = blackPoppins,
                        ),
                        modifier = Modifier.padding(5.dp))
                    Text("Valor da Conta",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = blackPoppins
                        ),
                        modifier = Modifier.padding(5.dp))
                }
            }
        }
        Spacer(modifier = Modifier.size(26.dp))

        Text(
            "Último passo! Escolha a cor do card para deixar tudo com a sua cara!",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.size(35.dp))
        //Carrossel de icones
        CarrouselIcons(
            iconList
        )
        Spacer(modifier = Modifier.size(74.dp))
        //Botão para avançar de tela
        Button(
            onClick = {

            }, enabled = false
        ) {
            Text("Avançar")
        }
    }
}
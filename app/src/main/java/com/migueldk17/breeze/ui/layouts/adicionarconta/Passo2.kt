package com.migueldk17.breeze.ui.layouts.adicionarconta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.migueldk17.breeze.ui.icons.BreezeIcons
import com.migueldk17.breeze.ui.components.adicionarconta.CarrouselIcons
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.theme.blackPoppins

@Composable
fun Passo2(){
    val iconList = listOf(
        BreezeIcons.Linear.BookLinear,
        BreezeIcons.Linear.GroupLinear,
        BreezeIcons.Linear.GlobeLinear,
        BreezeIcons.Linear.CarLinear,
        BreezeIcons.Linear.CloudLinear,
        BreezeIcons.Linear.DropLinear,
        BreezeIcons.Linear.AirplaneLinear,
        BreezeIcons.Linear.DiscoverLinear,
        BreezeIcons.Linear.KeyLinear)
    Column(
        modifier = Modifier
            .padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Assim está ficando o card da sua nova conta:",
            style = MaterialTheme.typography.bodySmall,
            color = blackPoppins)
        Spacer(modifier = Modifier.size(25.dp))
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
                modifier = Modifier.padding(10.dp)
            ) {
                Spacer(Modifier.size(50.dp))
                Column {
                    Text("Nome da conta",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = blackPoppins
                        ))
                }
            }
        }
        Spacer(modifier = Modifier.size(26.dp))

        Text("Agora escolha um nome para representar essa conta.",
            style = MaterialTheme.typography.bodySmall,
            color = blackPoppins)
        Spacer(Modifier.size(18.dp))
        Text("Qual combina melhor ?",
            style = MaterialTheme.typography.bodySmall,
            color = blackPoppins)


        Spacer(modifier = Modifier.size(8.dp))
    }

    CarrouselIcons(iconList)

    Spacer(modifier = Modifier.size(71.dp))
    Button(onClick = {

    }, enabled = false
    ) {
        Text("Avançar")
    }
}
package com.migueldk17.breeze.ui.layouts.adicionarconta

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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.BreezeIcons
import com.migueldk17.breeze.ui.components.adicionarconta.CarrouselIcons
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.theme.blackPoppins

@Composable
fun Passo2(){
    val iconList = listOf(
        BreezeIcons.BookLinear,
        BreezeIcons.GroupLinear,
        BreezeIcons.GlobeLinear,
        BreezeIcons.CarLinear,
        BreezeIcons.CloudLinear,
        BreezeIcons.DropLinear,
        BreezeIcons.AirplaneLinear,
        BreezeIcons.DiscoverLinear,
        BreezeIcons.KeyLinear)


    Text("Assim está ficando o card da sua nova conta:",
        style = MaterialTheme.typography.bodySmall)
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
        style = MaterialTheme.typography.bodySmall)
    Spacer(Modifier.size(18.dp))
    Text("Qual combina melhor ?",
        style = MaterialTheme.typography.bodySmall)


    Spacer(modifier = Modifier.size(31.dp))
    CarrouselIcons(iconList)
    Spacer(modifier = Modifier.size(74.dp))
    Button(onClick = {

    }, enabled = false
    ) {
        Text("Avançar")
    }
}
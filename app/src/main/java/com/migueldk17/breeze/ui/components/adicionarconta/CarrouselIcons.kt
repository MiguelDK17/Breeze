package com.migueldk17.breeze.ui.components.adicionarconta

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.BreezeIcons
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.theme.greyTextMediumPoppins

@Composable
fun CarrouselIcons(){
    Column(
        modifier = Modifier
            .size(width = 382.dp, height = 129.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .size(width = 382.dp, height = 68.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    BreezeIcons.BookLinear,
                    "Icone de Livro"
                )
                Icon(
                    BreezeIcons.GroupLinear,
                    "Ícone de Ônibus"
                )
                Icon(
                    BreezeIcons.GlobeLinear,
                    "Ícone de Globo"
                )
                Icon(
                    BreezeIcons.CarLinear,
                    "Ícone de Carro"
                )
                Icon(
                    BreezeIcons.CloudLinear,
                    "Ícone de Núvem"
                )
            }


        }
        Spacer(Modifier.height(15.dp))
        Text("Arraste para o lado para selecionar",
            style = MaterialTheme.typography.bodyMedium,
            color = greyTextMediumPoppins)
    }

}
@Preview(showBackground = true)
@Composable
private fun Preview(){
    BreezeTheme {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(PastelLightBlue),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
         CarrouselIcons()
        }
    }
}
package com.migueldk17.breeze.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIconType


//Card de PaginaInicial
@Composable
fun BreezeCard(
    imageVector: FaIconType,
    categoria: String,
    valorCategoria: Int,
    baseColor: Color,
    cardColor: Color,
    onClick: () -> Unit
){
    OutlinedCard (
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = baseColor)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),

        ){
            //Linha de cima(com ícone, texto e etc)
            Row(modifier = Modifier
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FaIcon(imageVector, size = 48.dp, tint = cardColor)
                Spacer(Modifier.size(20.dp))
                Text(categoria)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = {
                        onClick()
                    }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.Add, "Add")
                            Spacer(modifier = Modifier.size(10.dp))
                            Text("Editar Valor")
                        }
                    }
                }

            }
            //Linha de baixo com o valor monetário da categoria
            Row(
                modifier = Modifier.padding(10.dp)

            ) {
                Spacer(modifier = Modifier.width(68.dp))
                Text("R$: $valorCategoria")
            }
        }


    }
    Spacer(modifier = Modifier.size(10.dp))
}

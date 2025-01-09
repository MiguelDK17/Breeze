package com.migueldk17.breeze.ui.components.adicionarconta

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.FaIcons
import com.migueldk17.breeze.ui.components.BreezeCard
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.theme.SkyBlue
import com.migueldk17.breeze.ui.theme.blackPoppins

@Composable
fun CardPrincipal(){
    var text by remember{
        mutableStateOf("")
    }
    Card(modifier = Modifier
        .size(width = 383.dp, height = 522.dp)
        .padding(5.dp),
        border = BorderStroke(1.dp, SkyBlue),
        shape = RoundedCornerShape(10.dp),
        colors = CardColors(
            contentColor = Color.Transparent,
            containerColor = PastelLightBlue,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text("Parece que o card de sua nova conta está vazio:",
                style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.size(25.dp))
            Card(
                modifier = Modifier
                    .size(width = 342.dp, height = 80.dp)
                ,
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardColors(
                    containerColor = PastelLightBlue,
                    contentColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )


            ) {

            }
            Spacer(modifier = Modifier.size(26.dp))

            Text("Vamos começar adicionando um nome !",
                style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.size(26.dp))

            TextField(text, onValueChange = { value ->
                text = value
            },
                 modifier = Modifier.size(width = 210.dp, height = 56.dp),
                placeholder = {
                    Text("Adicionar nome")
                })
            Spacer(modifier = Modifier.size(74.dp))
            Button(onClick = {

            }, enabled = false
            ) {
                Text("Avançar")
            }

        }
    }
}

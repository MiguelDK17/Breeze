package com.migueldk17.breeze.ui.components.adicionarconta

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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

@Composable
fun CardPrincipal(){
    var text by remember{
        mutableStateOf("")
    }
    Card(modifier = Modifier
        .size(width = 383.dp, height = 522.dp)
        .padding(5.dp)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text("Parece que o card de sua nova conta está vazio:")
            Card(
                modifier = Modifier.size(width = 342.dp, height = 80.dp),

            ) {

            }
            Spacer(modifier = Modifier.size(10.dp))

            Text("Vamos começar adicionando um nome !")
            Spacer(modifier = Modifier.size(25.dp))

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

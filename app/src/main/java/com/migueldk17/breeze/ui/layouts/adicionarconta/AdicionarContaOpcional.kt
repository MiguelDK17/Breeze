package com.migueldk17.breeze.ui.layouts.adicionarconta

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.migueldk17.breeze.ui.components.adicionarconta.CardPrincipal
import com.migueldk17.breeze.ui.theme.BreezeTheme

@Composable
fun AdicionarContaOpcional(
    navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(15.dp)
    ) {
        Text(
            "Adicionar Conta Opcional",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp,
            color = Black)
        Spacer(modifier = Modifier.size(20.dp))
        Text("Passo 1 de 5",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(15.dp))
        LinearProgressIndicator(
            progress = {
                0.2f
            },
            modifier = Modifier.fillMaxWidth(),
            color = Green,
        )
        Spacer(modifier = Modifier.size(10.dp))
        CardPrincipal(
            firstText = "Parece que o card de sua nova conta está vazio:",
            secondText = "Vamos começar adicionando um nome !"
        )
    }
}
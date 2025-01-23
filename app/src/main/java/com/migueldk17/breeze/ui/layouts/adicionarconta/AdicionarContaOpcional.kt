package com.migueldk17.breeze.ui.layouts.adicionarconta

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.migueldk17.breeze.ui.components.adicionarconta.CardPrincipal

@Composable
fun AdicionarContaOpcional(
    navController: NavController,
    modifier : Modifier = Modifier
){
    //Column principal do ciclo de vida AdicionarContaOpcionaç
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        //Text de título
        Text(
            "Adicionar Conta Opcional",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp,
            color = Black)
        Spacer(modifier = Modifier.size(20.dp))
        //Contagem de passos
        Text("Passo 1 de 5",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(15.dp))
        //LinearProgressIndicator que evolui conforme os passos
        LinearProgressIndicator(
            progress = {
                0.2f
            },
            modifier = Modifier.fillMaxWidth(),
            color = Green,
        )
        Spacer(modifier = Modifier.size(10.dp))
        //Card Principal que abriga os passos que serão gerenciados pelo NavigationCompose
        CardPrincipal{
            Passo2()
        }


    }
}
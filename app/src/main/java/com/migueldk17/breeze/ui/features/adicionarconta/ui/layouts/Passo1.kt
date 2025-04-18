package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.viewmodels.BreezeViewModel

@Composable
fun Passo1(navController: NavController, viewModel: BreezeViewModel = hiltViewModel()){
    var text by remember{
        mutableStateOf("")
    }


    Column(
        modifier = Modifier
            .padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Parece que o card de sua nova conta está vazio:",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.size(25.dp))
        //Card que evolui conforme o usuario vai adicionando informações
        Card(
            modifier = Modifier
                .size(width = 342.dp, height = 80.dp),
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

        Text(
            "Vamos começar adicionando um nome !",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.size(26.dp))
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            //TextField responsável por adicionar um nome a conta
            TextField(
                text,
                onValueChange = { value ->
                    text = value
                },
                modifier = Modifier.size(width = 210.dp, height = 56.dp),
                placeholder = {
                    Text("Adicionar nome")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                isError = !textoCorreto(text)
            )
            if (!textoCorreto(text)) {
                Text(
                    "O nome da conta deve ter menos de 15 caracteres",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp
                )
            }
        }
        Spacer(modifier = Modifier.size(74.dp))
        //Botão para avançar de tela
        Button(
            onClick = {
                    viewModel.setNomeConta(text)
                    navController.navigate(NavGraph2.Passo2.route)

            }, enabled = text.isNotEmpty() && textoCorreto(text)
        ) {
            Text("Avançar")
        }
    }
}
private fun textoCorreto(text: String): Boolean {
    return text.length <= 14
}
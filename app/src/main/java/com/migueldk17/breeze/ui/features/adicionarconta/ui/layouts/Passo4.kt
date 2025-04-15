package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.migueldk17.breeze.MoneyVisualTransformation
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.theme.blackPoppins
import com.migueldk17.breeze.viewmodels.BreezeViewModel

@Composable
fun Passo4(navController: NavController, viewModel: BreezeViewModel = hiltViewModel()) {
    var valorConta by remember{
        mutableStateOf("")
    }
    val nomeConta = viewModel.nomeConta.collectAsState().value
    val icone = viewModel.iconeCardConta.collectAsState().value
    val corIcone = viewModel.corIcone.collectAsState().value
    Log.d(TAG, "Passo4: $corIcone")
    //Column do Passo4
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
                    icone,
                    contentDescription = "Ícone de Livro",
                    modifier = Modifier
                        .size(50.dp),
                    color = corIcone
                )
                Spacer(modifier = Modifier.size(15.dp))
                Column {
                    Text(nomeConta,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = blackPoppins,
                        ),
                        modifier = Modifier.padding(5.dp))
                }
            }
        }
        Spacer(modifier = Modifier.size(26.dp))

        Text(
            "Quanto você planeja gastar com esta conta ?",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.size(5.dp))

        Text("Defina o valor aqui!",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.size(29.dp))
        //TextField responsável por adicionar um valor a conta
        TextField(valorConta, onValueChange = { value ->
            valorConta = value
        },
            modifier = Modifier.size(width = 210.dp, height = 56.dp),
            placeholder = {
                Text("Adicionar Valor")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            visualTransformation = MoneyVisualTransformation())
        Spacer(modifier = Modifier.size(74.dp))
        //Botão para avançar de tela
        Button(
            onClick = {
                viewModel.guardaValorConta(valorConta.toDouble())
                navController.navigate(NavGraph2.Passo5.route)
            }, enabled = valorConta.isNotEmpty()
        ) {
            Text("Avançar")
        }
    }
}
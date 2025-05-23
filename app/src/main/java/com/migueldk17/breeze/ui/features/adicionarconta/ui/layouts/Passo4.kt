package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.migueldk17.breeze.MoneyVisualTransformation
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.PersonalizationCard
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel

@Composable
fun Passo4(navController: NavController, viewModel: AdicionarContaViewModel = hiltViewModel()) {
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

        DescriptionText("Assim está ficando o card da sua nova conta:")
        Spacer(modifier = Modifier.size(25.dp))
        //Card que evolui conforme o usuario vai adicionando informações
        PersonalizationCard(nomeConta = nomeConta, icone = icone, corIcone = corIcone)
        Spacer(modifier = Modifier.size(26.dp))

        DescriptionText("Quanto você planeja gastar com esta conta ?")
        Spacer(modifier = Modifier.size(5.dp))

        DescriptionText("Defina o valor aqui!")
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
package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.migueldk17.breeze.MoneyVisualTransformation
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.ParcelamentoColumn
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.PersonalizationCard
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel
import com.migueldk17.breeze.ui.theme.BreezeTheme

@Composable
fun Passo4(navController: NavController, viewModel: AdicionarContaViewModel = hiltViewModel()) {
    var valorConta by remember{
        mutableStateOf("")
    }
    val nomeConta = viewModel.nomeConta.collectAsState().value
    val icone = viewModel.iconeCardConta.collectAsState().value
    val corIcone = viewModel.corIcone.collectAsState().value
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


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun Passo4Preview() {
    var valorConta by remember{
        mutableStateOf("")
    }
    var isChecked by remember {
        mutableStateOf(false)
    }
//    val nomeConta = viewModel.nomeConta.collectAsState().value
//    val icone = viewModel.iconeCardConta.collectAsState().value
//    val corIcone = viewModel.corIcone.collectAsState().value
    //Column do Passo4

    BoxWithConstraints{
        val horizontalPadding = if (maxWidth < 380.dp) 16.dp else 25.dp
        val isSmallScreen = maxWidth < 380.dp

        Column(
            modifier = Modifier
                .padding(horizontalPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            DescriptionText("Assim está ficando o card da sua nova conta:")
            Spacer(modifier = Modifier.size(25.dp))
//        //Card que evolui conforme o usuario vai adicionando informações
//        PersonalizationCard(nomeConta = nomeConta, icone = icone, corIcone = corIcone)
            Spacer(modifier = Modifier.size(26.dp))

            DescriptionText("Quanto você planeja gastar com esta conta ?")
            Spacer(modifier = Modifier.size(5.dp))

            DescriptionText("Defina o valor aqui!")
            Spacer(modifier = Modifier.size(29.dp))
            //TextField responsável por adicionar um valor a conta
            TextField(valorConta, onValueChange = { value ->
                valorConta = value
            },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text("Adicionar Valor")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                visualTransformation = MoneyVisualTransformation())
            Row(
                modifier = Modifier.padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DescriptionText("Essa conta é parcelada ?")
                Checkbox(
                    enabled = true,
                    onCheckedChange = {
                        isChecked = it
                    },
                    checked = isChecked
                )
            }
            if (isChecked) {
                ParcelamentoColumn(isSmallScreen)
            }

            //Botão para avançar de tela
            Button(
                modifier = Modifier
                    .padding(vertical = 74.dp),
                onClick = {
//                viewModel.guardaValorConta(valorConta.toDouble())
//                navController.navigate(NavGraph2.Passo5.route)
                }, enabled = valorConta.isNotEmpty()
            ) {
                Text("Avançar")
            }
        }
    }

}

@Composable
@Preview
private fun Preview(){
    BreezeTheme {
        Passo4Preview()
    }
}
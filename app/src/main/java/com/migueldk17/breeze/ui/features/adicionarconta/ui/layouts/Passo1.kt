package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.components.BreezeButton
import com.migueldk17.breeze.ui.components.BreezeDropdownMenu
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.PersonalizationCard
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel
import com.migueldk17.breeze.ui.theme.BreezeTheme

@Composable
fun Passo1(navController: NavController = rememberNavController(), viewModel: AdicionarContaViewModel = hiltViewModel()){
    var text by remember{
        mutableStateOf("")
    }


    Column(
        modifier = Modifier
            .padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DescriptionText("Parece que o card de sua nova conta está vazio:")
        Spacer(modifier = Modifier.size(25.dp))
        //Card que evolui conforme o usuario vai adicionando informações
        PersonalizationCard()
        Spacer(modifier = Modifier.size(26.dp))

        DescriptionText("Vamos começar adicionando um nome !")
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
                minLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                isError = !textoCorreto(text)
            )
            if (!textoCorreto(text)) {
                Text(
                    "O nome da conta deve ter entre dois caracteres e 20 caracteres",
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
    return  text.length <= 20
}

@Composable
fun Passo1Preview(){
    var text by remember{
        mutableStateOf("")
    }
    val categories = listOf(
        "Alimentação",
        "Transporte",
        "Educação",
        "Moradia",
        "Lazer",
        "Saúde",
        "Trabalho/Negócios",
        "Pets",
        "Pessoais",
        "Outros")
    var selectedCategory by remember { mutableStateOf("Selecione uma categoria") }
    var onCategorySelected by remember { mutableStateOf("") }



    Column(
        modifier = Modifier
            .padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DescriptionText("Parece que o card de sua nova conta está vazio:")
        Spacer(modifier = Modifier.size(25.dp))
        //Card que evolui conforme o usuario vai adicionando informações
        PersonalizationCard()
        Spacer(modifier = Modifier.size(26.dp))

        DescriptionText("Vamos começar adicionando um nome !")
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 26.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            //TextField responsável por adicionar um nome a conta
            TextField(
                text,
                onValueChange = { value ->
                    text = value
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Adicionar nome")
                },
                minLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                isError = !textoCorreto(text)
            )
            if (!textoCorreto(text)) {
                Text(
                    "O nome da conta deve ter entre dois caracteres e 20 caracteres",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp
                )
            }
        }

        BreezeDropdownMenu(
            modifier = Modifier.padding(vertical = 10.dp),
            categoryName = "Insira uma categoria(Opcional)",
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )
        Log.d(TAG, "Passo1Preview: $selectedCategory")
        //Botão para avançar de tela
        BreezeButton(
            modifier = Modifier.padding(vertical = 30.dp),
            text = "Avançar", onClick = {
        //    viewModel.setNomeConta(text)
        //    navController.navigate(NavGraph2.Passo2.route)

        },
            enabled = text.isNotEmpty() && textoCorreto(text))
    }
}

@Composable
@Preview
private fun Preview(){
    BreezeTheme {
        Passo1Preview()
    }
}
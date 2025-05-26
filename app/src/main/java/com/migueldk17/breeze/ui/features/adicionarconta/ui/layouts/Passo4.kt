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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.migueldk17.breeze.MoneyVisualTransformation
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.components.BreezeOutlinedTextField
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.ParcelamentoColumn
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.PersonalizationCard
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel
import java.time.LocalDate

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun Passo4(navController: NavController, viewModel: AdicionarContaViewModel = hiltViewModel()) {
    //Valor bruto da conta em reais
    var valorConta by remember{
        mutableStateOf("")
    }

    var textJuros by remember {
        mutableStateOf("")
    }
    var isChecked by remember {
        mutableStateOf(false)
    }
    var isCheckedParcelamento by remember {
        mutableStateOf(false)
    }
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    var selectedCategory by remember { mutableStateOf("1x") }
    val categories = listOf("1x", "3x", "6x", "12x", "Outro...")
    var textParcelas by remember { mutableStateOf("") }

    val nomeConta = viewModel.nomeConta.collectAsState().value
    val icone = viewModel.iconeCardConta.collectAsState().value
    val corIcone = viewModel.corIcone.collectAsState().value
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
            PersonalizationCard(nomeConta = nomeConta, icone = icone, corIcone = corIcone)
            Spacer(modifier = Modifier.size(26.dp))

            DescriptionText("Quanto você planeja gastar com esta conta ?")
            Spacer(modifier = Modifier.size(5.dp))

            DescriptionText("Defina o valor aqui!")
            Spacer(modifier = Modifier.size(29.dp))
            //TextField responsável por adicionar um valor a conta
            BreezeOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = valorConta,
                onValueChange = { valorConta = it},
                textLabel = "Adicionar Valor",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                visualTransformation = MoneyVisualTransformation()

            )
            Row(
                modifier = Modifier.padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DescriptionText("Essa conta é parcelada ?")
                Checkbox(
                    enabled = true,
                    onCheckedChange = {
                        isChecked = it
                        viewModel.guardaIsContaParcelada(it)
                    },
                    checked = isChecked
                )
            }
            if (isChecked) {
                ParcelamentoColumn(
                    isSmallScreen,
                    selectedDate = selectedDate,
                    onEditDate = { selectedDate = it},
                    textJuros = textJuros,
                    onEditTextJuros = { textJuros = it},
                    isCheckedParcelamento,
                    { isCheckedParcelamento = it},
                    categoriesParcelamento = categories,
                    selectedCategory = selectedCategory,
                    onChangeCategoriesParcelamento = { selectedCategory = it},
                    textParcelas = textParcelas,
                    onChangeTextParcelas = { textParcelas = it}
                    )
            }

            //Botão para avançar de tela
            Button(
                modifier = Modifier
                    .padding(vertical = 74.dp),
                onClick = {
                    viewModel.guardaValorConta(valorConta.toDouble())
                    viewModel.guardaDataConta(selectedDate)
                    if (textParcelas.isEmpty()) viewModel.guardaQtdParcelas(selectedCategory) else viewModel.guardaQtdParcelas(textParcelas)
                    if (textJuros.isNotEmpty()) viewModel.guardaPorcentagemJuros(textJuros)
                    navController.navigate(NavGraph2.Passo5.route)
                },
                enabled = buttonAvancaEnabled(
                    valorConta = valorConta,
                    textJuros,
                    isChecked,
                    isCheckedParcelamento
                )
            ) {
                Text("Avançar")
            }
        }
    }
}
@Composable
private fun buttonAvancaEnabled(
    valorConta: String,
    porcentagemText: String,
    isCheckedPasso4: Boolean,
    isCheckedParcelamento: Boolean): Boolean {
    val condicao = when {
        valorConta.isNotEmpty() && !isCheckedPasso4 -> {
            true
        }
        valorConta.isNotEmpty() && isCheckedPasso4 && !isCheckedParcelamento -> {
            true
        }
        valorConta.isNotEmpty() && isCheckedPasso4 && isCheckedParcelamento && porcentagemText.isNotEmpty() -> {
            true
        }
        else -> {
            false
        }

    }
    return condicao
}
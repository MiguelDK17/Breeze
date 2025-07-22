package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

import android.annotation.SuppressLint
import android.util.Log
import android.content.ContentValues.TAG
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
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
import com.migueldk17.breeze.ui.components.BreezeButton
import com.migueldk17.breeze.ui.components.BreezeOutlinedTextField
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.ParcelamentoColumn
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.PersonalizationCard
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel
import java.time.LocalDate

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun Passo4(
    navToPasso5: () -> Unit,
    viewModel: AdicionarContaViewModel = hiltViewModel()) {
    //Valor bruto da conta em reais
    var valorConta by remember{
        mutableStateOf("")
    }
    //Variável que armazena a quantidade de juros da conta
    var textJuros by remember {
        mutableStateOf("")
    }
    //Variável que controla o estado do Checkbox em Passo4
    var isChecked by remember {
        mutableStateOf(false)
    }
    //Variável que controla o estado do Checkbox em ParcelamentoColumn
    var isCheckedParcelamento by remember {
        mutableStateOf(false)
    }
    //Armazena a data da conta fornecida pelo usuário
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    //Valor inicial do Dropdown de parcelamento
    var selectedCategory by remember { mutableStateOf("1x") }
    //Lista de quantidade de parcelas pre-definidas para escolha
    val categories = listOf("1x", "3x", "6x", "12x", "Outro...")
    //Caso a quantidade desejada não estiver em categories esta variável serve para armazenar o valor do BreezeOutlinedTextField em ParcelamentoColumn
    var textParcelas by remember { mutableStateOf("") }

    val nomeConta = viewModel.nomeConta.collectAsState().value
    val icone = viewModel.iconeCardConta.collectAsState().value
    val corIcone = viewModel.corIcone.collectAsState().value

    //Column do Passo4
    BoxWithConstraints{
        val horizontalPadding = if (maxWidth < 380.dp) 16.dp else 25.dp //Padding responsivo de acordo com a largura da tela
        val isSmallScreen = maxWidth < 380.dp //Boolean que controla se a tela é pequena ou não

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
                onValueChange = { text ->
                    valorConta = text.filter { it.isDigit() }

                },
                textLabel = "Adicionar Valor",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done),
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
                    isSmallScreen, //Boolean que controla se a tela é pequena ou não
                    selectedDate = selectedDate, //Data da conta
                    onEditDate = { selectedDate = it}, //Função que atualiza a data da conta
                    textJuros = textJuros, //Valor do juros
                    onEditTextJuros = { text ->
                        textJuros = text.filter { it.isDigit() }
                    }, //Função que atualiza o valor do juros
                    isCheckedParcelamento, //Boolean que controla se o checkbox de parcelamento está marcado ou não
                    { isCheckedParcelamento = it}, //Função que atualiza o valor do checkbox de parcelamento
                    categoriesParcelamento = categories, //Lista de opções de parcelamento
                    selectedCategory = selectedCategory, //Opção selecionada no Dropdown
                    onChangeCategoriesParcelamento = { selectedCategory = it}, //Função que atualiza a opção selecionada no Dropdown
                    textParcelas = textParcelas, //Valor do BreezeOutlinedTextField de parcelamento
                    onChangeTextParcelas = { textParcelas = it} //Função que atualiza o valor do BreezeOutlinedTextField de parcelamento
                    )
            }

            //Botão para avançar de tela
            BreezeButton(
                modifier = Modifier
                    .padding(vertical = 74.dp),
                text = "Avançar",
                onClick = {
                    viewModel.guardaValorConta(valorConta.toDouble())
                    if (textJuros != "") viewModel.guardaPorcentagemJuros(textJuros) //Guarda a porcentagem de juros
                    viewModel.guardaDataConta(selectedDate) //Guarda a data da conta
                    if (textParcelas.isEmpty()) viewModel.guardaQtdParcelas(selectedCategory) else viewModel.guardaQtdParcelas(textParcelas) //Guarda a quantidade de parcelas
                    navToPasso5()
                },
                enabled = buttonAvancaEnabled(
                    valorConta = valorConta,
                    textJuros,
                    textParcelas,
                    selectedCategory,
                    isChecked,
                    isCheckedParcelamento
                )
            )
        }
    }
}
//Função usada para controlar o estado do BreezeButton
@Composable
private fun buttonAvancaEnabled(
    valorConta: String,
    porcentagemText: String,
    textParcelas: String,
    selectedCategory: String,
    isCheckedPasso4: Boolean,
    isCheckedParcelamento: Boolean): Boolean {
    Log.d(TAG, "buttonAvancaEnabled: O valor de textParcelas é de: $textParcelas ")

    val condicao = when {
        //Caso o valor da conta não esteja vazio e o checkbox esteja marcado o valor é true
        valorConta.isNotEmpty() && !isCheckedPasso4 -> {
            true
        }
        //Caso o valor da conta não esteja vazio e o checkbox esteja marcado e o de parcelamento não o valor é true
        valorConta.isNotEmpty() && isCheckedPasso4 && !isCheckedParcelamento && selectedCategory != "Outro..." -> {
            true
        }
        //Caso o valor da conta não esteja vazio, o checkbox esteja ativo, o checkbox de parcelamento esteja ativo e o texto de porcentagem não esteja ativo o valor é true
        valorConta.isNotEmpty() && isCheckedPasso4 && isCheckedParcelamento && porcentagemText.isNotEmpty() -> {
            true
        }
        valorConta.isNotEmpty() && selectedCategory == "Outro..." && textParcelas.isNotEmpty() -> {
            true
        }
        //Qualquer outro cenário além dos mencionados acima o valor é false
        else -> {
            false
        }

    }
    return condicao
}
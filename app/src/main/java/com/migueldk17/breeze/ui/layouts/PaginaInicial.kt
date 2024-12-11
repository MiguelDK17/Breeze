package com.migueldk17.breeze.ui.layouts

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.guru.fontawesomecomposelib.FaIcons
import com.migueldk17.breeze.MainActivity2
import com.migueldk17.breeze.MoneyVisualTransformation
import com.migueldk17.breeze.ui.components.BreezeCard
import com.migueldk17.breeze.ui.theme.cardAguaColor
import com.migueldk17.breeze.ui.theme.cardAluguelColor
import com.migueldk17.breeze.ui.theme.cardEnergiaColor
import com.migueldk17.breeze.ui.theme.cardInternetColor
import com.migueldk17.breeze.ui.theme.cardSupermercado
import com.migueldk17.breeze.ui.theme.iconAluguelColor
import com.migueldk17.breeze.ui.theme.iconContaAguaColor
import com.migueldk17.breeze.ui.theme.iconContaEnergiaColor
import com.migueldk17.breeze.ui.theme.iconInternetColor
import com.migueldk17.breeze.ui.theme.iconSupermercadoColor
import com.migueldk17.breeze.viewmodels.BreezeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun PaginaInicial(navController: NavController, viewModel: BreezeViewModel = hiltViewModel()){
    val saldo by viewModel.saldo.collectAsState()
    val context = LocalContext.current
    val saldoFormatado = saldo?.valor


    //Estados para controlar o ModalBottomSheet
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }


    //Estado para armazenar o saldo
    var saldoInput by remember { mutableStateOf("") }
    var isSaldoCorrectly by remember { mutableStateOf(false) }


    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.size(20.dp))
        //Card de saldo disponível
        ElevatedCard(modifier = Modifier
            .size(width = 254.dp, height = 49.dp))
        {
             Row(modifier = Modifier.fillMaxSize(),
                 verticalAlignment = Alignment.CenterVertically,
                 horizontalArrangement = Arrangement.Center) {
                Text(
                    formataSaldo(saldoFormatado),
                    style = MaterialTheme.typography.titleMedium
                )
                 //Botão para editar o saldo
                IconButton(
                    onClick = {
                        showBottomSheet = true
                    },
                    modifier = Modifier
                        .size(23.dp)
                        .padding(0.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        "",
                        modifier = Modifier.size(30.dp)
                    )
                }
            } }
        Spacer(modifier = Modifier.size(15.dp))
        Text("Contas Fixas:",
            fontSize = 14.sp)
        Spacer(modifier = Modifier.size(10.dp))

        BreezeCard(FaIcons.Globe,
            "Internet",
            150,
            cardInternetColor, //Cor do card
            iconInternetColor,  //Cor do ícone
            onClick = {
                onClick(navController, cardInternetColor, iconInternetColor, "Internet")
            })

        BreezeCard(FaIcons.Water,
            "Conta de Água",
            12,
            cardAguaColor, //Cor do card
            iconContaAguaColor,  //Cor do ícone
            onClick = {
                onClick(navController, cardAguaColor, iconContaAguaColor, "Conta de Água")
            })
        BreezeCard(
            FaIcons.Bolt,
            "Conta de Energia",
            100,
            cardEnergiaColor, //Cor do card
            iconContaEnergiaColor, //Cor do ícone
            onClick = {
                onClick(navController, cardEnergiaColor, iconContaEnergiaColor, "Conta de Energia")
            })
        BreezeCard(FaIcons.Home,
            "Aluguel",
            450,
            cardAluguelColor, //Cor do card
            iconAluguelColor,  //Cor do ícone
            onClick = {
                onClick(navController, cardAluguelColor, iconAluguelColor, "Aluguel")
            })
        BreezeCard(FaIcons.ShoppingCart,
            "Supermercado",
            350,
            cardSupermercado, //Cor do card
            iconSupermercadoColor, //Cor do ícone
            onClick = {
                onClick(navController, cardSupermercado, iconSupermercadoColor, "Supermercado")
            })
    }
    if (showBottomSheet){
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Editar Saldo", style = MaterialTheme.typography.titleMedium)
                //AQUI FICARÁ O OUTLINEDTEXTFIELD
                OutlinedTextField(
                    value = saldoInput,
                    onValueChange = { value ->
                        saldoInput = value.filter { it.isLetterOrDigit() }
                        Log.d(TAG, "PaginaInicial: $saldoInput")
                    },
                    label = { Text("Novo Saldo") },
                    //Aceita apenas números
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    //Apenas uma linha
                    singleLine = true,
                    //Verificação de erro
                    isError = saldoInput.isNotEmpty() && (saldoInput.toIntOrNull() ?: 0) !in 1000..9999999,
                    visualTransformation = MoneyVisualTransformation()
                )
                if (saldoInput.isNotEmpty() && (saldoInput.toIntOrNull() ?: 0) !in 1000..9999999) {
                    isSaldoCorrectly = false
                    Text(
                        text = "O saldo deve estar entre R$:10,00 e R$:99.999,00",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                else {
                    isSaldoCorrectly = true
                }
                Button(onClick = {
                    viewModel.atualizaSaldo(saldoInput.toDouble()) //Atualiza o saldo
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible){
                            showBottomSheet = false  //Fecha o BottomSheet
                        }
                    }
                },
                    enabled = isSaldoCorrectly && saldoInput != ""
                ) {
                    Text("Salvar")
                }


            }
        }
    }
}

private fun formataSaldo(saldo: Double?): String {
    if (saldo != null) {
        val formatacao = String.format("Seu Saldo: R$ %.2f", saldo)
        return formatacao
    } else return "Carregando..."
}
private fun onClick(navController: NavController, cardColor: Color, iconColor: Color, nomeConta: String){

    val intent = Intent(navController.context, MainActivity2::class.java)
    //Transforma a cor em Argb para passar para a intent
    val transformaCorIcon = iconColor.toArgb()

    //Transforma a cor em Argb para passar para a intent
    val transformaCorCard = cardColor.toArgb()

    //Manda as cores do card e do icone para um array
    val arrayList = intArrayOf(transformaCorCard, transformaCorIcon)


    intent.putExtra("color", arrayList)
    intent.putExtra("nome", nomeConta)
    navController.context.startActivity(intent)
}
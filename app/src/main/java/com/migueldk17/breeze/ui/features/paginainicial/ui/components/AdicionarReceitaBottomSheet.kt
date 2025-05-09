package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.MoneyVisualTransformation
import com.migueldk17.breeze.viewmodels.BreezeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdicionarReceitaBottomSheet(viewModel: BreezeViewModel){
    var showBottomSheet by remember { mutableStateOf(false) }


    //Estados para controlar o ModalBottomSheet
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    //Estado para armazenar o saldo
    var saldoInput by remember { mutableStateOf("") }
    var isSaldoCorrectly by remember { mutableStateOf(false) }

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
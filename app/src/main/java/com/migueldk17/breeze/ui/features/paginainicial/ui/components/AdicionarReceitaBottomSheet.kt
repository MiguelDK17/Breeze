package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.MoneyVisualTransformation
import com.migueldk17.breeze.converters.toDatabaseValue
import com.migueldk17.breeze.ui.features.paginainicial.viewmodels.PaginaInicialViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdicionarReceitaBottomSheet(
    atualizaBottomSheet: (Boolean) -> Unit,
    adicionaReceita: (Double, String, LocalDate, String) -> Unit
){
    //Estados para controlar o ModalBottomSheet
     val scope = rememberCoroutineScope()

    //Estado para armazenar o saldo
    var saldoInput by remember { mutableStateOf("") }

    var isSaldoCorrectly by remember { mutableStateOf(false) }

    var descricaoInput by remember { mutableStateOf("")}

    var showDatePicker by remember { mutableStateOf(false)}

    var selectedDate by remember { mutableStateOf(LocalDate.now())}

    var fecharSolicitado by remember { mutableStateOf(false)}

    val icon = BreezeIcons.Linear.Money.DollarCircle.enum.toDatabaseValue()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(Unit) {
        sheetState.show()
    }

    ModalBottomSheet(
        onDismissRequest = {
            atualizaBottomSheet(false)
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
            Text("Adicionar Receita", style = MaterialTheme.typography.titleMedium)

            // Valor
            OutlinedTextField(
                value = saldoInput,
                onValueChange = { value ->
                    saldoInput = value.filter { it.isLetterOrDigit() }
                },
                label = { Text("Valor") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                isError = saldoInput.isNotEmpty() && (saldoInput.toIntOrNull() ?: 0) !in 500..9999999,
                visualTransformation = MoneyVisualTransformation()
            )

            if (saldoInput.isNotEmpty() && (saldoInput.toIntOrNull() ?: 0) !in 500..9999999) {
                isSaldoCorrectly = false
                Text(
                    text = "O saldo deve ser superior a R$ 5,00",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            } else {
                isSaldoCorrectly = true
            }

            // Descrição opcional
            OutlinedTextField(
                value = descricaoInput,
                onValueChange = { descricaoInput = it },
                label = { Text("Descrição (opcional)") },
                singleLine = true
            )

            // Data
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Data:", style = MaterialTheme.typography.bodyMedium)
                Text(
                    selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            BreezeDatePicker(
                showDialog = showDatePicker,
                onDismiss = {showDatePicker = false},
                onDateSelected = { selectedDate = it}
            )

            // Botão salvar
            Button(
                onClick = {
                    adicionaReceita(
                        saldoInput.toDouble(),
                        descricaoInput,
                        selectedDate,
                        icon
                    )
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            atualizaBottomSheet(false)
                        }
                    }
                },
                enabled = isSaldoCorrectly && saldoInput.isNotEmpty()
            ) {
                Text("Salvar")
            }
        }
    }

}
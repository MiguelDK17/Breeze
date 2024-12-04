package com.migueldk17.breeze.ui.components

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.migueldk17.breeze.viewmodels.BreezeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreezeModalBottomSheet(viewModel: BreezeViewModel = hiltViewModel()){
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var novoSaldo by remember { mutableStateOf("") }


    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Editar Saldo", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = novoSaldo,
                onValueChange = { novoSaldo = it },
                label = { Text("Novo Saldo") },
                //Aceita apenas números
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                //Apenas uma linha
                singleLine = true
            )
            Button(onClick = {
                viewModel.atualizaSaldo(novoSaldo.toDouble()) //Atualiza o saldo
                scope.launch { sheetState.hide() } //Fecha o BottomSheet
            }) { }

        }
    }
}

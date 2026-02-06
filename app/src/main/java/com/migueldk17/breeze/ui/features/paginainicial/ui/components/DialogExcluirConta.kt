package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogExcluirConta(
    openDialogExcluirConta: (Boolean) -> Unit,
    apagarConta: () -> Unit,
    apagarParcelas: () -> Unit,
    modifier: Modifier = Modifier
){
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = {
            //Dispensa o BasicAlertDialog
            openDialogExcluirConta(false)
        }

    ) {
        Surface(
            modifier = Modifier.size(width = 312.dp, height = 200.dp),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {

                //Título do BasicAlertDialog
                Text("Excluir Conta",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 23.sp)

                //Texto do BasicAlertDialog
                Text("Você está prestes a excluir esta conta, esta ação será irreversível. Prosseguir ?",
                    style = MaterialTheme.typography.bodyMedium)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        openDialogExcluirConta(false) //Botão de cancelar
                    }) {
                        Text("Cancelar")
                    }
                    TextButton(onClick = {
                        apagarConta()
                        apagarParcelas()
                        openDialogExcluirConta(false) //Botão de confirmar
                    }) {
                        Text("Confirmar")
                    }
                }
            }
        }
    }
}
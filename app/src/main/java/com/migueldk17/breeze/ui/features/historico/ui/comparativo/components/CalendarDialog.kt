package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarDialog(
    yearMonth: YearMonth,
    onConfirm: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
){
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    selectedDate?.let { onConfirm }
                }
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        text = {
            BreezeCalendar(
                yearMonth = yearMonth,
                selectedDate = selectedDate,
                onDateSelect = { selectedDate = it }
            )
        }
    )
}
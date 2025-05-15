package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceitaDatePicker(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    if (showDialog) {
        val datePickerState = rememberDatePickerState()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                            onDateSelected(selectedDate)
                        }
                        onDismiss()
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) { Text("Cancelar")}
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.ui.theme.DarkBlue
import com.migueldk17.breeze.ui.theme.NavyBlue
import com.migueldk17.breeze.ui.theme.NavyPetrol
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
        containerColor = if (!isSystemInDarkTheme()) Color(0xFFFDFDFD) else DarkBlue,
        confirmButton = {
            Button(
                onClick = {
                    selectedDate?.let { date ->
                        onConfirm(date)
                    }
                },
                 enabled = selectedDate != null
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
            Column(
            ) {
                BreezeCalendar(
                    yearMonth = yearMonth,
                    selectedDate = selectedDate,
                    onDateSelect = { selectedDate = it }
                )
                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider()
            }

        }
    )
}

@Composable
@Preview
private fun Preview(){
    BreezeTheme() {
        CalendarDialog(
            yearMonth = YearMonth.now(),
            onConfirm = {},
            onDismiss = {}
        )
    }

}
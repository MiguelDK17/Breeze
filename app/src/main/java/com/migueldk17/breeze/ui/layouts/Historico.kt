package com.migueldk17.breeze.ui.layouts

import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar
import java.util.Locale

@Composable
fun Histórico(){
    val context = LocalContext.current
    var selectedMonth by remember { mutableStateOf("") }

    //Abrir o DatePickerDialog e pegar apenas o mês
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, _: Int ->
            //Pegando apenas o mês (e.g Janeiro, Fevereiro)
            selectedMonth = Calendar.getInstance().apply {
                set(Calendar.MONTH, month)
            }.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) ?: ""
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )
    Column {
        Button(onClick = { datePickerDialog.show() }) {
            Text("Select Month")
        }

        if(selectedMonth.isNotEmpty()){
            Text("Selected Month: $selectedMonth")
        }
    }

}
@Composable
@Preview(showBackground = true)
private fun Preview(){
    Histórico()
}
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
import com.migueldk17.breeze.ui.components.Calendario
import java.util.Calendar
import java.util.Locale

@Composable
fun Histórico(){
    val context = LocalContext.current
    var selectedMonth by remember { mutableStateOf("") }

    Calendario {  }

}
@Composable
@Preview(showBackground = true)
private fun Preview(){
    Histórico()
}
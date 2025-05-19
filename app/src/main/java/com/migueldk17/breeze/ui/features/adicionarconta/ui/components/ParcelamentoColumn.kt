package com.migueldk17.breeze.ui.features.adicionarconta.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.components.BreezeDropdownMenu
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ParcelamentoColumn(){
    var selectedCategory by remember { mutableStateOf("1x") }
    val categories = listOf("1x", "3x", "6x", "12x", "Outro...")
    var textParcelas by remember { mutableStateOf("") }
    var textJuros by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false)}

    var selectedDate by remember { mutableStateOf(LocalDate.now())}

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .height(47.dp),
                contentAlignment = Alignment.Center
            ){
                DescriptionText("Número de parcelas:")
            }
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                BreezeDropdownMenu(
                    categoryName = "",
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it},
                    showDescriptionText = false
                )

                if (selectedCategory == "Outro...") {
                    TextField(
                        value = textParcelas,
                        onValueChange = {
                            textParcelas = it
                        },
                        placeholder = {
                            DescriptionText("Parcelas")
                        },
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(50.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DescriptionText("Qual a porcentagem de juros?")
            TextField(
                value = textJuros,
                onValueChange = {
                    textJuros = it
                },
                placeholder = {
                    DescriptionText("2% ao mês")
                },
                modifier = Modifier
                    .width(121.dp)
                    .height(56.dp),

            )
            Row(
                modifier = Modifier.padding(vertical = 30.dp)
            ) {
                BreezeIcon(BreezeIcons.Linear.Time.CalendarLinear, contentDescription = null)
                DescriptionText("Data da primeira parcela: ${selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}")
            }
        }

    }
}
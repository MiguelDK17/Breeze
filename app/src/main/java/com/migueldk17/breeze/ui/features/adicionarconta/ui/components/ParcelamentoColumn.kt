package com.migueldk17.breeze.ui.features.adicionarconta.ui.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.entity.Receita
import com.migueldk17.breeze.ui.components.BreezeDropdownMenu
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.ReceitaDatePicker
import com.migueldk17.breeze.ui.theme.Blue
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ParcelamentoColumn(isSmallScreen: Boolean){
    val fillMaxWidth = Modifier.fillMaxWidth()
    var selectedCategory by remember { mutableStateOf("1x") }
    val categories = listOf("1x", "3x", "6x", "12x", "Outro...")
    var textParcelas by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    var textJuros by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false)}

    var selectedDate by remember { mutableStateOf(LocalDate.now())}
    Log.d(TAG, "ParcelamentoColumn: $isSmallScreen")

    Column {
        Row(
            modifier = fillMaxWidth,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
                Box(
                    modifier = Modifier
                        .height(47.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DescriptionText("Número de parcelas:")
                }
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End
                ) {
                    BreezeDropdownMenu(
                        modifier = if(isSmallScreen) Modifier.width(120.dp) else Modifier.width(162.dp),
                        categoryName = "",
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { selectedCategory = it },
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
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            modifier = fillMaxWidth,
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DescriptionText("As parcelas têm juros ?")
            Checkbox(
                enabled = true,
                onCheckedChange = {
                    checked = it
                },
                checked = checked
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (checked) {
            ResponsiveJurosSection(isSmallScreen, textJuros) { textJuros = it }
        }
            ResponsiveDateParcelaSection(isSmallScreen, selectedDate)

        ReceitaDatePicker(
            showDialog = showDatePicker,
            onDismiss = { showDatePicker = false},
        ) { selectedDate = it }


        Row(
            modifier = fillMaxWidth,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val text = if (checked) "Parcelado em $selectedCategory com juros" else "Parcelado em $selectedCategory sem juros"
            Text(
                text,
                fontStyle = FontStyle.Italic,
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            BreezeIcon(
                BreezeIcons.Linear.Essetional.InfoCircle,
                contentDescription = "Botão de informções",
                modifier = Modifier.padding(horizontal = 5.dp)

            )
        }

    }
}
@Composable
private fun ResponsiveJurosSection(
    isSmallScreen: Boolean,
    textJuros: String,
    onValueChange: (String) -> Unit
) {
    if (isSmallScreen) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ResponsiveLabelField(textJuros = textJuros, onValueChange = onValueChange)
        }
    } else {
        Log.d(TAG, "ParcelamentoColumn: caiu no else $isSmallScreen")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ResponsiveLabelField(textJuros = textJuros, onValueChange = onValueChange)
        }

    }
}

@Composable
private fun ResponsiveDateParcelaSection(
    isSmallScreen: Boolean,
    selectedDate: LocalDate){
    val iconSize = if (isSmallScreen) 24.dp else 26.dp
    val formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    val layoutModifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 30.dp)

    if (isSmallScreen) {
        Row(
            modifier = layoutModifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column{
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                BreezeIcon(
                    BreezeIcons.Linear.Time.CalendarLinear,
                    contentDescription = null
                )
                DescriptionText(
                    "Data da primeira parcela:",
                    size = 12.9.sp,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
                Spacer(modifier = Modifier.height(15.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    DescriptionText(
                        formattedDate
                    )
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .size(iconSize)
                            .padding(start = 5.dp)
                    ) {
                        BreezeIcon(
                            BreezeIcons.Linear.Software.EditLinear,
                            contentDescription = "Editar data da primeira parcela"
                        )
                    }
                }
            }
        }
    }
    else {
        Row(
            modifier = layoutModifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DescriptionText(
                "Data da primeira parcela: ${
                    selectedDate.format(
                        DateTimeFormatter.ofPattern(
                            "dd/MM/yyyy"
                        )
                    )
                }"
            )
            IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(iconSize)
                ) {
                    BreezeIcon(
                        BreezeIcons.Linear.Software.EditLinear,
                        contentDescription = "Editar data da primeira parcela",
                        modifier = Modifier

                    )
                }
        }
    }
}

@Composable
private fun ResponsiveLabelField(textJuros: String, onValueChange: (String) -> Unit){

        DescriptionText("Qual a porcentagem de juros?")
        Spacer(modifier = Modifier.size(25.dp))
        TextField(
            value = textJuros,
            onValueChange = onValueChange,
            placeholder = {
                DescriptionText("2% ao mês")
            },
            modifier = Modifier
                .width(121.dp)
                .height(56.dp),

            )

}
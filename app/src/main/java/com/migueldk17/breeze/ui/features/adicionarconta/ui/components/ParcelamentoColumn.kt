package com.migueldk17.breeze.ui.features.adicionarconta.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.components.BreezeDropdownMenu
import com.migueldk17.breeze.ui.components.BreezeOutlinedTextField
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.BreezeDatePicker
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ParcelamentoColumn(
    isSmallScreen: Boolean, //Booleano para verificar se é um dispositivo pequeno
    selectedDate: LocalDate, //Data da primeira parcela
    onEditDate: (LocalDate) -> Unit, //Função para editar a data da primeira parcela
    textJuros: String, //Texto do campo de texto para o juros
    onEditTextJuros: (String) -> Unit, //Função para editar o texto do campo de texto para o juros
    isParcelamentoComJurosChecked: Boolean, //Booleano para verificar se o parcelamento tem juros
    onCheckedChange: (Boolean) -> Unit, //Função para editar o booleano para verificar se o parcelamento tem juros
    categoriesParcelamento: List<String>, //Lista de categorias de parcelamento
    selectedCategory: String, //Categoria selecionada
    onChangeCategoriesParcelamento: (String) -> Unit, //Função para editar a categoria selecionada
    textParcelas: String, //Texto do campo de texto para o número de parcelas
    onChangeTextParcelas: (String) -> Unit, //Função para editar o texto do campo de texto para o número de parcelas
    ){

    val fillMaxWidth = Modifier.fillMaxWidth()
    var showDatePicker by remember { mutableStateOf(false)}

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
                        categories = categoriesParcelamento,
                        selectedCategory = selectedCategory,
                        onCategorySelected = onChangeCategoriesParcelamento,
                        showDescriptionText = false
                    )
                    //Caso a categoria selecionada seja Outro irá aparecer um OutlinedTextField para inserir a quantidade de parcelas
                    if (selectedCategory == "Outro...") {
                        BreezeOutlinedTextField(
                            modifier = Modifier.padding(vertical = 20.dp),
                            text = textParcelas,
                            onValueChange = onChangeTextParcelas,
                            textLabel = "Parcelas",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
                onCheckedChange = onCheckedChange,
                checked = isParcelamentoComJurosChecked
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        if (isParcelamentoComJurosChecked) {
            ResponsiveJurosSection(isSmallScreen, textJuros, onValueChange = onEditTextJuros)
        }
            ResponsiveDateParcelaSection(isSmallScreen, selectedDate, showDatePicker = {
                showDatePicker = true
            })
        //Date Picker usado para alterar a data da conta
        BreezeDatePicker(
            showDialog = showDatePicker,
            onDismiss = { showDatePicker = false},
            onDateSelected = onEditDate
        )


        Row(
            modifier = fillMaxWidth,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val parceladoEm = if (selectedCategory == "Outro...") "${textParcelas}x" else selectedCategory
            val parceladoEmFormatado = if (parceladoEm == "x") "..." else parceladoEm
            val text = if (isParcelamentoComJurosChecked && textJuros.isNotEmpty()) "Parcelado em $parceladoEmFormatado com juros" else "Parcelado em $parceladoEmFormatado sem juros"
            Text(
                text,
                fontStyle = FontStyle.Italic,
                color = Color.Black,
                fontSize = 14.sp,
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
    selectedDate: LocalDate,
    showDatePicker: () -> Unit){
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
                        onClick = showDatePicker,
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

    OutlinedTextField(
        textJuros,
        onValueChange = onValueChange,
        modifier = Modifier
            .width(130.dp)
            .height(80.dp),
        label = {
            Text("Porcentagem",
                fontSize = 12.4.sp)
        },
        minLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
        isError = textJuros.length > 2,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF5F5F5),
            unfocusedContainerColor = Color(0xFFF5F5F5),
            unfocusedBorderColor = Color(0xFFF5F5F5)
        ),
        textStyle = TextStyle(
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    )

}
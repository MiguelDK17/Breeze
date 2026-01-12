package com.migueldk17.breeze.ui.features.confirmarpagamento.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.components.BreezeDropdownMenu
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.theme.grayforHint
import com.migueldk17.breeze.ui.theme.grayforTextColorInDropdown

@Composable
fun PaymentMethodField(

){
    var selectedCategory by remember { mutableStateOf("Nenhum") }
    val textColor = if (selectedCategory == "Nenhum") grayforHint else grayforTextColorInDropdown

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val categories = listOf("💵 Dinheiro", "💳 Cartão de Débito", "💳 Cartão de Crédito", "💸 Pix")

        Box(
            modifier = Modifier
                .height(62.dp),
            contentAlignment = Alignment.Center
        ) {
            BreezeRegularText(text = "Forma de pagamento:")
        }
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            BreezeDropdownMenu(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                categoryName = "",
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = {
                    selectedCategory = it
                }
            )

        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        BreezeRegularText(
            modifier = Modifier.padding(10.dp),
            text = "Juros:"
        )
        BreezeRegularText(
            modifier = Modifier.padding(10.dp),
            text = "0.0% a.m"
        )
    }

}
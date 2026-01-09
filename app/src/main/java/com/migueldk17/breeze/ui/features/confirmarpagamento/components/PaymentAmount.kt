package com.migueldk17.breeze.ui.features.confirmarpagamento.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.ui.components.BreezeDropdownMenu
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.theme.NavyPetrol

@Composable
fun PaymentAmount(){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val categories = listOf("💵 Dinheiro", "💳 Cartão de Débito", "💳 Cartão de Crédito", "💸 Pix")
        var selectedCategory by remember { mutableStateOf("Nenhum") }

        Box(
            modifier = Modifier
                .height(62.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
           BreezeRegularText(text = "Forma de pagamento:")
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
                onCategorySelected = {
                    selectedCategory = it
                }
            )
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BreezeRegularText(
            modifier = Modifier.padding(start = 30.dp),
            text = "Juros:"
        )
        BreezeRegularText(
            modifier = Modifier.padding(end = 30.dp),
            text = "0.0% a.m"
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val categories = listOf("1", "2", "3", "4")
        var selectedCategory by remember { mutableStateOf("1") }

        Box(
            modifier = Modifier
                .height(62.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            BreezeRegularText(text = "Parcela n°:")
        }
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            BreezeDropdownMenu(
                modifier = Modifier
                    .size(50.dp),
                categories = categories,
                categoryName = "",
                selectedCategory = selectedCategory,
                onCategorySelected = {
                    selectedCategory = it
                }
            )
        }
    }

}

@Composable
@Preview
private fun Preview(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BreezeRegularText(
            modifier = Modifier.padding(start = 30.dp),
            text = "Juros:")
        BreezeRegularText(
            modifier = Modifier.padding(end = 30.dp),
            text = "0.0% a.m")
    }

}

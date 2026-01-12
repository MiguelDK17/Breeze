package com.migueldk17.breeze.ui.features.confirmarpagamento.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.components.BreezeDropdownMenu
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.theme.grayforHint
import com.migueldk17.breeze.ui.theme.grayforTextColorInDropdown

@Composable
fun PaymentAmount(
    isContaParcelada: Boolean
){
    var selectedCategory by remember { mutableStateOf("Nenhum") }
    val textColor = if (selectedCategory == "Nenhum") grayforHint else grayforTextColorInDropdown

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val categories = listOf("💵 Dinheiro", "💳 Cartão de Débito", "💳 Cartão de Crédito", "💸 Pix")

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
    if (isContaParcelada) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val categories = listOf("1", "2", "3", "4")
            var selectedNumericalCategory by remember { mutableStateOf("1") }

            BreezeRegularText(
                modifier = Modifier.padding(end = 10.dp),
                text = "Parcela n°:"
            )
            Column(
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 23.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BreezeDropdownMenu(
                    modifier = Modifier
                        .size(60.dp),
                    categories = categories,
                    categoryName = "",
                    selectedCategory = selectedNumericalCategory,
                    onCategorySelected = {
                        selectedNumericalCategory = it
                    },
                    textSize = 14.sp,
                    textColor = textColor
                )
            }

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

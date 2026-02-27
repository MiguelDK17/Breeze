package com.migueldk17.breeze.ui.features.confirmarpagamento.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.components.BreezeDropdownMenu
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.features.confirmarpagamento.model.ConfirmPaymentModel
import com.migueldk17.breeze.ui.theme.grayforHint
import com.migueldk17.breeze.ui.theme.grayforTextColorInDropdown
import kotlinx.collections.immutable.toImmutableList

@Composable
fun PaymentMethodField(
    state: ConfirmPaymentModel,
    selectedCategory: String,
    onSelectCategory: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    val juros = state.juros

    Column() {
        // --------- Row das formas de pagamento -----------------//
        Row(
            modifier = modifier
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
                modifier = Modifier
                    .heightIn(min = 76.dp, max = 100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BreezeDropdownMenu(
                    categoryName = "",
                    categories = categories.toImmutableList(),
                    selectedCategory = selectedCategory,
                    onCategorySelect = {
                        onSelectCategory(it)
                    },
                    showDescriptionText = false
                )

            }
        }

        //-------------Row dos Juros -------------------//
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
                text = "$juros% a.m"
            )
        }
    }



}
package com.migueldk17.breeze.ui.features.confirmarpagamento.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.components.BreezeDropdownMenu
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.theme.grayforHint
import com.migueldk17.breeze.ui.theme.grayforTextColorInDropdown

@Composable
fun InstallmentField() {
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
                textColor = grayforTextColorInDropdown
            )
        }

    }
}
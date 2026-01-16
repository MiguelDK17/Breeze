package com.migueldk17.breeze.ui.features.confirmarpagamento.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.components.BreezeDropdownMenu
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.features.confirmarpagamento.state.ConfirmPaymentState
import com.migueldk17.breeze.ui.theme.grayforTextColorInDropdown
import com.migueldk17.breeze.ui.utils.ToastManager

@Composable
fun InstallmentField(
    state: ConfirmPaymentState
) {
    val context = LocalContext.current
    ToastManager.showToast(context, "As parcelas disponíveis pra essa compra são essas: ${state.qtdParcelas}")
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val installments = state.qtdParcelas
        val firstInstallment = installments.first()
        var selectedNumericalCategory by remember { mutableStateOf(firstInstallment) }

        BreezeRegularText(
            modifier = Modifier.padding(end = 10.dp),
            text = "Parcela n°:"
        )
        Column(
            modifier = Modifier
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BreezeDropdownMenu(
                modifier = Modifier
                    .widthIn(min = 53.dp, 60.dp)
                    .heightIn(min = 53.dp, 60.dp),
                categories = installments,
                categoryName = "",
                selectedCategory = selectedNumericalCategory,
                onCategorySelected = {
                    selectedNumericalCategory = it
                },
                textSize = 14.sp,
                textColor = grayforTextColorInDropdown,
                showDescriptionText = false
            )
        }

    }
}
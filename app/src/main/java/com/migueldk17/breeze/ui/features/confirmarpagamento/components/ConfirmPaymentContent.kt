package com.migueldk17.breeze.ui.features.confirmarpagamento.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.converters.toDatabaseValue
import com.migueldk17.breeze.ui.components.BreezeButton
import com.migueldk17.breeze.ui.features.confirmarpagamento.model.ConfirmPaymentModel
import com.migueldk17.breeze.ui.features.confirmarpagamento.viewmodels.ConfirmarPagamentoViewModel
import com.migueldk17.breeze.ui.theme.Blue
import com.migueldk17.breeze.ui.utils.ToastManager
import java.time.LocalDate

@Composable
fun ConfirmPaymentContent(
    state: ConfirmPaymentModel,
    onConfirm: () -> Unit,
    viewModel: ConfirmarPagamentoViewModel
){
    var selectedCategory by remember { mutableStateOf("Nenhum") }
    viewModel.setIdDaConta(state.id)
    val formaDePagamento = viewModel.formaDePagamento.collectAsStateWithLifecycle().value
    val isEnabled = selectedCategory != "Nenhum"
    val verticalScroll = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PaymentHeader()

        HorizontalDivider()

        PaymentAmount(state)

        HorizontalDivider()

        PaymentMethodField(
            state,
            selectedCategory = selectedCategory,
            onSelectCategory = {
                selectedCategory = it
            },
            viewModel = viewModel
        )
        if (state.isContaParcelada) {
            InstallmentField(state, setIdParcela = { viewModel.setIdDaParcela(it)})
        }

        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            BreezeButton(
                modifier = Modifier.size(width = 120.dp, height = 56.dp),
                text = "Confirmar",
                onClick = {
                    onConfirm()
                    viewModel.setFormaDePagamento(selectedCategory)
                },
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = Blue,
                enabled = isEnabled
            )
        }


    }

}
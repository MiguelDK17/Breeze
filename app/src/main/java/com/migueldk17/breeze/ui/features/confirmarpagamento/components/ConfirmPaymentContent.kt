package com.migueldk17.breeze.ui.features.confirmarpagamento.components

import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.migueldk17.breeze.ui.features.confirmarpagamento.model.ParcelaUI
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
    //-------------------Controles de estado---------------------//
    var selectedCategory by remember { mutableStateOf("Nenhum") }
    val isEnabled = selectedCategory != "Nenhum"
    //-----------------------------------------------------///

    //-------------------Variaveis do state---------------------//
    val isContaParcelada = state.isContaParcelada
    val firstInstallment = state.parcelas.firstOrNull()
    val numeroDaParcela = firstInstallment?.numero ?: 0
    //-----------------------------------------------------///

    //-------------------Setters do ViewModel---------------------//
    viewModel.setNomeDaConta(state.name)
    viewModel.setIdDaConta(state.id)
    viewModel.setValor(state.valor)
    //-----------------------------------------------------///

    var numeroParcela by remember { mutableIntStateOf(numeroDaParcela) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PaymentHeader()

        HorizontalDivider()

        PaymentAmount(
            state = state,
            numeroDaParcela = numeroParcela,
            haveInstallment = isContaParcelada
        )

        HorizontalDivider()

        PaymentMethodField(
            state,
            selectedCategory = selectedCategory,
            onSelectCategory = {
                selectedCategory = it
            },
        )
        if (isContaParcelada) {
            InstallmentField(
                state,
                setNomeDaConta = { viewModel.setNomeDaConta(it) },
                setIdParcela = { viewModel.setIdDaParcela(it)},
                numeroParcela = numeroParcela,
                setNumeroParcela = { numeroParcela = it},
                setIsLatestInstallment = { viewModel.setIsLatestInstallment(it)},
            )
        }

        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            BreezeButton(
                modifier = Modifier.size(width = 130.dp, height = 56.dp),
                text = "Confirmar",
                onClick = {
                    onConfirm()
                    viewModel.setNumeroDaParcela(numeroParcela)
                    viewModel.setFormaDePagamento(selectedCategory)
                    viewModel.efetuarPagamentos(isContaParcelada)
                },
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Blue,
                enabled = isEnabled
            )
        }
    }
}
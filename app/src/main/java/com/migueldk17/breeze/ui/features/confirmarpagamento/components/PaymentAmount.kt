package com.migueldk17.breeze.ui.features.confirmarpagamento.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.confirmarpagamento.model.ConfirmPaymentModel
import com.migueldk17.breeze.ui.features.confirmarpagamento.viewmodels.ConfirmarPagamentoViewModel
import com.migueldk17.breeze.ui.theme.Blue
import com.migueldk17.breeze.ui.theme.NavyBlue
import com.migueldk17.breeze.ui.utils.arredondarValor
import com.migueldk17.breeze.ui.utils.formataValorConta

@Composable
fun PaymentAmount(
    state: ConfirmPaymentModel,
    numeroDaParcela: Int,
    viewModel: ConfirmarPagamentoViewModel,
    haveInstallment: Boolean
){
    val name = state.name
    val icon = state.icon
    val valor = state.valor
    val valorArrendondado = arredondarValor(valor)
    val valorConta = formataValorConta(valorArrendondado)
    val description = if (!haveInstallment) "Pagamento referente a $name" else "Pagamento referente a ${numeroDaParcela}ª parcela de $name"
     BreezeIcon(
        modifier = Modifier
            .size(40.dp),
        breezeIcon = icon,
        contentDescription = "Icone de $icon"
    )
    BreezeRegularText(
        text = valorConta,
        size = 17.sp,
        fontWeight = FontWeight.SemiBold,
        color = Blue
    )
    DescriptionText(
        text = description,
        color = NavyBlue
    )


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

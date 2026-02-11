package com.migueldk17.breeze.ui.features.confirmarpagamento.components

import android.util.Log
import android.content.ContentValues.TAG
import android.content.Context
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
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.ui.components.BreezeDropdownMenu
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.features.confirmarpagamento.model.ConfirmPaymentModel
import com.migueldk17.breeze.ui.features.confirmarpagamento.model.ParcelaUI
import com.migueldk17.breeze.ui.features.confirmarpagamento.viewmodels.ConfirmarPagamentoViewModel
import com.migueldk17.breeze.ui.theme.grayforTextColorInDropdown
import com.migueldk17.breeze.ui.utils.ToastManager

@Composable
fun InstallmentField(
    state: ConfirmPaymentModel,
    setNomeDaConta: (String) -> Unit,
    setIdParcela: (Long) -> Unit,
    numeroParcela: Int,
    setNumeroParcela: (Int) -> Unit,
    setIsLatestInstallment: (Boolean) -> Unit
) {
    val listaDeParcelas = state.parcelas
    setNomeDaConta(state.name)
    setIsLatestInstallment(isLatestInstallment((listaDeParcelas)))
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val list = mutableListOf<String>()
        val listAntiga = remember(state) { state.parcelas.toMutableList() }
        val map = mutableMapOf<Int, ParcelaUI>()
        for (parcela in listAntiga) {
            list.add(parcela.numero.toString())
            map[parcela.numero] = parcela
        }
        // ------------------CONTINUAR DAQUI ----------------------------- //
        setIdParcela(returnIdDaParcela(map, numeroParcela, context))



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
                categories = list,
                categoryName = "",
                selectedCategory = numeroParcela.toString(),
                onCategorySelected = {
                    setNumeroParcela(it.toInt())
                    Log.d(TAG, "InstallmentField: numero parcela na função é $it")

                },
                textSize = 14.sp,
                textColor = grayforTextColorInDropdown,
                showDescriptionText = false
            )
        }

    }
}

private fun returnIdDaParcela(map: Map<Int, ParcelaUI>, idDaLista: Int, context: Context): Long {

    val parcela = map.get(idDaLista)
    val id = parcela?.idDaParcela ?: 99
    return id
}
private fun isLatestInstallment(parcela: List<ParcelaUI>) =  parcela.size <= 1
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
import com.migueldk17.breeze.ui.theme.grayforTextColorInDropdown
import com.migueldk17.breeze.ui.utils.ToastManager

@Composable
fun InstallmentField(
    state: ConfirmPaymentModel,
    setIdParcela: (Long) -> Unit,
    setNumeroParcela: (Int) -> Unit,
    setIsLatestInstallment: (Boolean) -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val list = mutableListOf<String>()
        val listAntiga = state.parcelas.toMutableList()
        val map = mutableMapOf<Int, ParcelaUI>()
        for (parcela in listAntiga) {
            list.add(parcela.numero.toString())
            map[parcela.numero] = parcela
        }
        Log.d(TAG, "InstallmentField: conteúdo do map $map")
        val firstInstallment = list.first()
        // ------------------CONTINUAR DAQUI ----------------------------- //
        var selectedNumericalCategory by remember { mutableStateOf(firstInstallment) }
        setIdParcela(returnIdDaParcela(map, selectedNumericalCategory.toInt(), context))
        setNumeroParcela(selectedNumericalCategory.toInt())
        setIsLatestInstallment(isLatestInstallment(listAntiga))


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

private fun returnIdDaParcela(map: Map<Int, ParcelaUI>, idDaLista: Int, context: Context): Long {

    val parcela = map.get(idDaLista)
    val id = parcela?.idDaParcela ?: 99
    ToastManager.showToast(context, "A parcela selecionada é o numero $idDaLista, o id dela é $id")
    return id
}

private fun isLatestInstallment(parcela: List<ParcelaUI>) = parcela.size <= 1
package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import android.util.Log
import android.content.ContentValues.TAG
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.enums.TipoComparacao
import com.migueldk17.breeze.enums.TipoMovimentacao
import com.migueldk17.breeze.ui.components.BreezeButtonGroup
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.components.TitleText
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import com.migueldk17.breeze.ui.features.historico.ui.components.GraficoDeBarras
import com.migueldk17.breeze.ui.theme.NavyBlue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate
import java.math.BigDecimal
import java.time.LocalDateTime
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.migueldk17.breeze.ui.features.historico.ui.TipoDeDados
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.model.ComparativoModel
import com.migueldk17.breeze.ui.utils.ToastManager
import com.migueldk17.breeze.ui.utils.toApiFormat
import java.time.YearMonth


@Composable
fun SaldoDoMesCard(
    listMovimentacaoDomain: ImmutableList<MovimentacaoDomain>,
    comparativoModel: ComparativoModel,
    setDia: (String) -> Unit,
    converteDiaEmMes: (Boolean) -> Unit,
    modifier: Modifier = Modifier,

    ){
    val options = persistentListOf("Dia", "Categoria", "Mês")
    val tipoDeDados = comparativoModel.tipoDeDados
    var selectedIndex by remember { mutableIntStateOf(tipoDeDados.ordinal) }
    var isCalendarOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val listLinhaDoTempoModel = remember(listMovimentacaoDomain) {
        listMovimentacaoDomain.map {
            LinhaDoTempoModel(
                id = it.id,
                name = it.descricao,
                icon = it.icon,
                valor = it.valor,
                colorCard = it.colorCard.toArgb(),
                colorIcon = it.colorIcon.toArgb(),
                dateTime = it.date.atStartOfDay(),
                tipoComparacao = TipoComparacao.COMPARACAO

            )
        }.toImmutableList()
    }

    //Elevated Card Pai
    BreezeElevatedCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleText(
                text = "Saldo do mês",
                color = NavyBlue,
                modifier = Modifier
            )
            //Card de Saldo Final
            SaldoFinal(
                comparativoModel = comparativoModel
            )

            //Button Group de opções
            BreezeButtonGroup(
                options = options,
                onChangeSelectedIndex = {
                    selectedIndex = it
                    when (selectedIndex) {
                        0 -> isCalendarOpen = true
                        1 -> ToastManager.showToast(context, "Função em desenvolvimento")
                        2 -> converteDiaEmMes(true)

                        else -> Log.d(TAG, "SaldoDoMesCard: teste")
                    }
                },
                initialSelectedIcon = selectedIndex
            )
            if (isCalendarOpen) {
                CalendarDialog(
                    yearMonth = YearMonth.now(),
                    onConfirm = { date ->
                        setDia(date.toApiFormat())
                        isCalendarOpen = false
                    },
                    onDismiss = {
                        isCalendarOpen = false
                    }
                )
            }
            GraficoDeBarras(
                modifier = Modifier
                    .height(290.dp),
                graficoDoDiaModel = listLinhaDoTempoModel
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Circle,
                    contentDescription = null,
                    tint = Color(0xFFACE1C1)
                )
                DescriptionText(
                    text = "Receitas",
                    color = Color(0xFF26595C)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    Icons.Default.Circle,
                    contentDescription = null,
                    tint = Color(0xFFF69297)
                )
                DescriptionText(
                    text = "Despesas",
                    color = Color(0xFFD3374D)
                )
            }
        }
    }
}

private fun onChangedSelectedIndex(selectIndex: Boolean) {

}

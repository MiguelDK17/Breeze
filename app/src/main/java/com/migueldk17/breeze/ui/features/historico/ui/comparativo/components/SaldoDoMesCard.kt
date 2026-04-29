package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components


import android.util.Log
import android.content.ContentValues.TAG
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.enums.TipoComparacao
import com.migueldk17.breeze.ui.components.BreezeButtonGroup
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.components.TitleText
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import com.migueldk17.breeze.ui.features.historico.ui.components.GraficoDeBarras
import com.migueldk17.breeze.ui.theme.NavyBlue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.kizitonwose.calendar.core.yearMonth
import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.domain.CategoryExpense
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.ComparativoData
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.model.ComparativoModel
import com.migueldk17.breeze.ui.utils.toApiFormat
import java.math.BigDecimal
import java.math.RoundingMode


@Composable
fun SaldoDoMesCard(
    data: ComparativoData,
    comparativoModel: ComparativoModel,
    mesBackup: String,
    setCategoria: () -> Unit,
    setDia: (String) -> Unit,
    converteDiaEmMes: () -> Unit,
    modifier: Modifier = Modifier,
    ){

    val options = persistentListOf("Dia", "Categoria", "Mês")
    var selectedIndex by remember { mutableIntStateOf(comparativoModel.tipoDeDados.ordinal) }
    var isCalendarOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val dataConvertida = "${mesBackup.dropLast(1)}-01"


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

            val receitas = (data as? ComparativoData.Movimentacoes)?.totalReceitas ?: "R$ 0,00"
            val despesas = (data as? ComparativoData.Movimentacoes)?.totalDespesas ?: "R$ 0,00"
            val saldoFinal = (data as? ComparativoData.Movimentacoes)?.saldoFinal ?: "R$ 0,00"


            //Card de Saldo Final
            SaldoFinal(
                receitas = receitas,
                despesas = despesas,
                saldoFinal = saldoFinal
            )

            //Button Group de opções
            BreezeButtonGroup(
                options = options,
                onChangeSelectedIndex = {
                    selectedIndex = it
                    when (selectedIndex) {
                        0 -> isCalendarOpen = true
                        1 -> setCategoria()
                        2 -> converteDiaEmMes()
                    }
                },
                initialSelectedIcon = selectedIndex
            )
            if (isCalendarOpen) {
                CalendarDialog(
                    yearMonth = dataConvertida.toLocalDate().yearMonth,
                    onConfirm = { date ->
                        setDia(date.toApiFormat())
                        isCalendarOpen = false
                    },
                    onDismiss = {
                        isCalendarOpen = false
                    }
                )
            }
            when (data) {
                is ComparativoData.Movimentacoes -> {
                    Log.d(TAG, "SaldoDoMesCard: caiu em Movimentacoes")
                    val listLinhaDoTempoModel = remember(data.list) {
                        data.list.map {
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
                is ComparativoData.Categoria -> {
                    Spacer(modifier = Modifier.height(25.dp))
                    Log.d(TAG, "SaldoDoMesCard: Caiu em Categoria")
                    BreezeElevatedCard() {
                        GraficoHorizontalCategoria(
                            listMovimentacaoDomain = data.list.toImmutableList()
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun GraficoHorizontalCategoria(listMovimentacaoDomain: ImmutableList<CategoryExpense>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        val valorTotal =
            remember(listMovimentacaoDomain) { listMovimentacaoDomain.sumOf { it.totalAmount } }
        listMovimentacaoDomain.forEach { wrapper ->
            val nomeCategoria = wrapper.category
            val valorConta = wrapper.totalAmount
            val porcentagem = wrapper.percentage.times(100).toInt()
            val colorCard = wrapper.colorCard
            val colorIcon = wrapper.iconColor
            val icon = returnIcon(nomeCategoria)
            val progress = valorConta.divide(valorTotal, 4, RoundingMode.HALF_UP).toFloat()
            val progressBrush = wrapper.progressBrush

            ObjectGraficoCategoria(
                colorCard = colorCard,
                icon = icon,
                iconColor = colorIcon,
                nomeCategoria = nomeCategoria,
                valorConta = valorConta,
                porcentagem = "$porcentagem%",
                progress = progress,
                progressBrush = progressBrush
            )
        }
    }
}


@Composable
private fun returnIcon(category: String): BreezeIconsType {
    return when(category) {
        "Alimentação" -> BreezeIcons.Linear.FoodKitchen.Donut
        "Transporte" -> BreezeIcons.Linear.Mobility.CarLinear
        "Moradia" -> BreezeIcons.Linear.Building.House
        "Lazer" -> BreezeIcons.Linear.All.Game
        "Saúde" -> BreezeIcons.Linear.All.Heart
        "Trabalho/Negócios" -> BreezeIcons.Linear.All.Bag2
        "Entretenimento" -> BreezeIcons.Linear.All.VideoCircleLinear
        "Educação" -> BreezeIcons.Linear.All.SquareAcademicCap2
        "Pets" -> BreezeIcons.Linear.All.Paw
        "Pessoais" -> BreezeIcons.Linear.All.Profile
        "Outros" -> BreezeIcons.Linear.All.GlobeLinear
        else -> BreezeIcons.Unspecified.IconUnspecified
    }
}

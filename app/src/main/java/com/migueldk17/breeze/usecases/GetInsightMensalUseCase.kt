package com.migueldk17.breeze.usecases

import android.util.Log
import android.content.ContentValues.TAG
import com.migueldk17.breeze.data.local.repository.MovimentacaoRepository
import com.migueldk17.breeze.domain.model.BreezeInsight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.DateTimeException
import java.time.YearMonth
import javax.inject.Inject
import kotlin.math.abs

class GetInsightMensalUseCase @Inject constructor(
    private val repository: MovimentacaoRepository
) {
    operator fun invoke(mesReferencia: String): Flow<BreezeInsight> {
        if (mesReferencia.isBlank() || !mesReferencia.contains("-")){
            Log.d(TAG, "GetCategoryTotalByMonthUseCase: Aguardando um mês válido")
            return flowOf(BreezeInsight("Calculando...", "Aguarde um momento."))
        }
        val dataReferencia = try {
            YearMonth.parse(mesReferencia)
        } catch (e: DateTimeException) {
            Log.d(TAG, "GetCategoryTotalByMonthUseCase: Erro no parse de data: $mesReferencia", e)
            YearMonth.now()
        }
        val mesAtualStr = dataReferencia.toString()
        val mesAnteriorStr = dataReferencia.minusMonths(1).toString()

        Log.d(TAG, "GetCategoryTotalByMonthUseCase: O mes atual é $mesAtualStr")
        Log.d(TAG, "GetCategoryTotalByMonthUseCase: Teste abaixo de mes atual")
        Log.d(TAG, "GetCategoryTotalByMonthUseCase: O mes anterior é $mesAnteriorStr")


        val totaisMesAtual = repository.getCategoryTotalByMonth(mesAtualStr)
        val totaisMesAnterior = repository.getCategoryTotalByMonth(mesAnteriorStr)

        return combine(totaisMesAtual, totaisMesAnterior) { atual, anterior ->
            val totalAtual = atual.sumOf { it.totalAmount }
            Log.d(TAG, "GetCategoryTotalByMonthUseCase: totais do mês $totalAtual")

            val totalAnterior = anterior.sumOf { it.totalAmount }
            Log.d(TAG, "GetCategoryTotalByMonthUseCase: totais do mes anterior$totalAnterior")


            if (totalAnterior > BigDecimal.ZERO) {
                val diferenca = totalAtual - totalAnterior
                val porcentagem = diferenca
                    .divide(totalAnterior, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal(100))
                    .toInt()

                Log.d(TAG, "invoke: A porcentagem é $porcentagem")

                when {
                    porcentagem > 0 -> BreezeInsight(
                        titulo = "Você gastou $porcentagem% a mais que mês passado",
                        subTitulo = "Dê uma freada nos gastos dessa semana."
                    )
                    porcentagem < 0 -> BreezeInsight(
                        titulo = "Boa! Seus gastos caíram ${abs(porcentagem)}%",
                        subTitulo = "A Nina está orgulhosa da sua economia."
                    )
                    else -> BreezeInsight(
                        titulo = "Seus gastos estão iguais ao mês passado",
                        subTitulo = "Manter a consistência também é um ótimo sinal."
                    )
                }
            } else {
                BreezeInsight(
                    titulo = "Começando a organizar as finanças?",
                    subTitulo = "Registre tudo para ver suas métricas no próximo mês."
                )
            }
        }
    }
}
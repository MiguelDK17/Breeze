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
import java.time.YearMonth
import javax.inject.Inject
import kotlin.math.abs

/**
 * UseCase responsável por gerar os "Smart Insights" (dicas financeiras da Nina)
 * comparando o total de despesas do mês selecionado pelo usuário com o mês imediatamente anterior.
 */
class GetInsightMensalUseCase @Inject constructor(
    private val repository: MovimentacaoRepository
) {
    /**
     * @param mesReferencia A string do mês que está sendo visualizado na UI (Formato esperado: "YYYY-MM").
     */
    operator fun invoke(mesReferencia: String): Flow<BreezeInsight> {

        // 1. Validação Segura de Data:
        // Impede que strings vazias ou mal formatadas ("") vindas do ViewModel causem um crash.
        val dataSelecionada = runCatching {
            YearMonth.parse(mesReferencia)
        }.getOrElse {
            // Fallback gracioso: Se der erro de parse, a UI recebe um aviso invés do app fechar.
            return flowOf(
                BreezeInsight("Erro ao ler o mês.", "Tente selecionar novamente")
            )
        }

        // 2. Definição do Período Relativo:
        // A lógica do mês passado acompanha a navegação do usuário (Ex: Se ele olha Março, o passado é Fevereiro).
        val mesAtualStr = dataSelecionada.toString()
        val mesAnteriorStr = dataSelecionada.minusMonths(1).toString()

        // 3. Busca de Dados Reativa:
        // Buscamos as movimentações do banco. O Room já nos entrega fluxos (Flow) que se atualizam sozinhos.
        val totaisMesAtual = repository.getCategoryTotalByMonth(mesAtualStr)
        val totaisMesAnterior = repository.getCategoryTotalByMonth(mesAnteriorStr)

        // 4. Cruzamento de Fluxos (A Mágica do Combine):
        // Sempre que houver uma mudança no mês atual ou no anterior (ex: usuário adiciona nova conta),
        // este bloco é re-executado automaticamente para recalcular o insight.
        return combine(totaisMesAtual, totaisMesAnterior) { atual, anterior ->

            // Soma todos os valores gastos nas categorias (O repositório já filtra apenas Despesas)
            val totalAtual = atual.sumOf { it.totalAmount }
            val totalAnterior = anterior.sumOf { it.totalAmount }

            // 5. Cálculo de Variação Mensal:
            // Só podemos comparar se o usuário teve gastos no mês passado para evitar divisão por zero.
            if (totalAnterior > BigDecimal.ZERO) {
                val diferenca = totalAtual - totalAnterior

                // Divisão blindada usando BigDecimal com 4 casas decimais para evitar perda de precisão
                // em centavos antes de multiplicar por 100 para achar a porcentagem exata.
                val porcentagem = diferenca
                    .divide(totalAnterior, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal(100))
                    .toInt()

                // 6. Geração da Frase de Insight:
                // Baseado no resultado matemático, a Nina escolhe o tom da mensagem.
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
                // Caso de Uso: Usuário novo (não tem dados no mês passado) ou não gastou nada.
                BreezeInsight(
                    titulo = "Começando a organizar as finanças?",
                    subTitulo = "Registre tudo para ver suas métricas no próximo mês."
                )
            }
        }
    }
}
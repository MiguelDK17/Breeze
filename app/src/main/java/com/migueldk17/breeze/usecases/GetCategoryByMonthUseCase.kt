package com.migueldk17.breeze.usecases

import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.data.local.repository.MovimentacaoRepository
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.dto.CategoryTotalDto
import com.migueldk17.breeze.enums.TipoMovimentacao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class GetCategoryTotalByMonthUseCase @Inject constructor(
    private val repository: MovimentacaoRepository
) {
    operator fun invoke(mesAno: String): Flow<List<MovimentacaoDomain>> {
        return repository.getCategoryTotalByMonth(mesAno).map { list ->
            val totalGeral = list.sumOf { it.totalAmount }

            list.map {
                MovimentacaoDomain(
                    id = UUID.randomUUID().toString().toLong(),
                    valor = it.totalAmount,
                    descricao = "",
                    categoria = it.category,
                    date = LocalDate.now(),
                    icon = BreezeIcons.Unspecified.IconUnspecified.enum.name,
                    tipo = TipoMovimentacao.SAIDA,
                    totalAmount = totalGeral,
                    totalPercentage = (it.totalAmount.divide(totalGeral)).toFloat()
                )
            }
        }
    }
}
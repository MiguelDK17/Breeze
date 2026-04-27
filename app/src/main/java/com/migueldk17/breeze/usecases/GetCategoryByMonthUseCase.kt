package com.migueldk17.breeze.usecases

import android.util.Log
import android.content.ContentValues.TAG
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.data.local.repository.MovimentacaoRepository
import com.migueldk17.breeze.domain.CategoryExpense
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.dto.CategoryTotalDto
import com.migueldk17.breeze.enums.TipoMovimentacao
import com.migueldk17.breeze.mapper.dto.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class GetCategoryTotalByMonthUseCase @Inject constructor(
    private val repository: MovimentacaoRepository
) {
    operator fun invoke(mesAno: String): Flow<List<CategoryExpense>> {
        return repository.getCategoryTotalByMonth(mesAno).map { list ->
            Log.d(TAG, "GetCategoryTotalByMonthUseCase: a lista $list")
            list.toDomain()
        }
    }
}
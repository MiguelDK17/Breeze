package com.migueldk17.breeze.usecases

import com.migueldk17.breeze.data.local.repository.MovimentacaoRepository
import com.migueldk17.breeze.domain.MovimentacaoDomain
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMovimentacoesDoDiaUseCase @Inject constructor(
    private val movimentacaoRepository: MovimentacaoRepository
)  {
    operator fun invoke(dia: String): Flow<List<MovimentacaoDomain>> {
        return movimentacaoRepository.getMovimentacoesDoDia(dia)

    }
}
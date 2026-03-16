package com.migueldk17.breeze.usecases

import com.migueldk17.breeze.data.local.repository.MovimentacaoRepository
import com.migueldk17.breeze.domain.MovimentacaoDomain
import kotlinx.coroutines.flow.Flow

class GetMovimentacoesDoMesUseCase constructor(
    private val movimentacaoRepository: MovimentacaoRepository
) {
    operator fun invoke(mes: String): Flow<List<MovimentacaoDomain>> {
        return movimentacaoRepository.getMovimentacoesDoMes(mes)

    }
}
package com.migueldk17.breeze.usecases


import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.converters.toStatus
import com.migueldk17.breeze.domain.Conta
import com.migueldk17.breeze.provider.DateProvider
import com.migueldk17.breeze.repository.ContaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetContasComStatusCalculadoUseCase @Inject constructor(
    private val repository: ContaRepository,
    private val dateProvider: DateProvider
) {
    suspend operator fun invoke(): Flow<List<Conta>> {
        val today = dateProvider.today()

        return repository.getContas().map { contas ->
            contas.map { conta ->
                Conta(
                    id = conta.id,
                    valor = conta.valor,
                    status = conta.status.toStatus(),
                    dataVencimento = conta.dataVencimento.toLocalDate(),
                )
            }

        }
    }
}
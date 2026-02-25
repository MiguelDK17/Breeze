package com.migueldk17.breeze.domain

import com.migueldk17.breeze.data.local.entity.ParcelaEntity
import com.migueldk17.breeze.data.local.repository.ParcelaRepository
import com.migueldk17.breeze.uistate.UiState
import javax.inject.Inject

class GetParcelaPorIdUseCase @Inject constructor(
    private val parcelaRepository: ParcelaRepository
) {
    suspend operator fun invoke(idParcela: Long): UiState<ParcelaEntity> {

        val parcela = parcelaRepository.getParcelaPorId(idParcela)

        return if (parcela == null) {
            UiState.Empty
        } else {
            UiState.Success(parcela)
        }
    }
}
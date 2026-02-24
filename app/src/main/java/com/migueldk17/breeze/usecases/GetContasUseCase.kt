package com.migueldk17.breeze.usecases


import android.util.Log
import android.content.ContentValues.TAG
import com.migueldk17.breeze.data.local.entity.calcularStatus
import com.migueldk17.breeze.data.local.relation.calcularStatusParcelado
import com.migueldk17.breeze.provider.DateProvider
import com.migueldk17.breeze.data.local.repository.ContaRepository
import com.migueldk17.breeze.domain.ContaComParcelas
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetContasUseCase @Inject constructor(
    private val repository: ContaRepository,
    private val dateProvider: DateProvider
) {
     operator fun invoke(): Flow<List<ContaComParcelas>> {
        return  repository.getContasComParcelas()
            .map { lista ->
                val today = dateProvider.today() // Provider já vem com LocalDate.now()
                Log.d(TAG, "GetContasUseCase: a data de hoje é ${today.dayOfMonth}/0${today.month}/${today.year}")
                lista.map { wrapper ->
                    val conta = wrapper.conta
                    val parcelas = wrapper.parcelas

                    val status = if (!conta.isContaParcelada) {
                    conta.calcularStatus(today)
                    } else {
                        wrapper.calcularStatusParcelado(parcelas, today)
                    }
                    ContaComParcelas(
                        conta = conta,
                        parcelas = parcelas,
                        status = status
                    )
                }


            }

    }
}
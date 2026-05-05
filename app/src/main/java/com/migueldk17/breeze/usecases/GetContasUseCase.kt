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
                lista.map { wrapper ->
                    val conta = wrapper.contaEntity
                    val parcelas = wrapper.parcelas

                    val dataPagamento = conta.dataPagamento
                    val dataVencimento = conta.dataVencimento

                    val status = if (!conta.isContaParcelada) {
                        val status = conta.calcularStatus(today, dataPagamento, dataVencimento)
                        Log.d(TAG, "GetContasUseCase: status desta conta fixa está retornando como $status")
                        status

                    } else {
                        val status = wrapper.calcularStatusParcelado(parcelas, today)
                        Log.d(TAG, "GetContasUseCase: status desta conta parcelada está retornando como $status")
                        status
                    }

                    Log.d(TAG, "invoke: status está retornando como $status")
                    ContaComParcelas(
                        contaEntity = conta,
                        parcelas = parcelas,
                        status = status
                    )
                }


            }

     }
}
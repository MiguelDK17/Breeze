package com.migueldk17.breeze.usecases


import android.util.Log
import android.content.ContentValues.TAG
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.calcularStatus
import com.migueldk17.breeze.provider.DateProvider
import com.migueldk17.breeze.repository.ContaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class GetContasUseCase @Inject constructor(
    private val repository: ContaRepository,
    private val dateProvider: DateProvider
) {
     operator fun invoke(): Flow<List<Conta>> {
        return  repository.getContas()
            .map { lista ->
                val today = dateProvider.today() // Provider já vem com LocalDate.now()
                Log.d(TAG, "GetContasUseCase: a data de hoje é ${today.dayOfMonth}/0${today.month}/${today.year}")
                lista.map {
                    conta ->
                    Log.d(TAG, "GetContasUseCase: StatusConta está vindo assim: ${conta.status}")
                    conta.copy(
                        status = conta.calcularStatus(today).ordinal
                    )
                }
            }
    }
}
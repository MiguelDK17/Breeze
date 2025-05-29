package com.migueldk17.breeze.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.ui.utils.traduzData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContaRepository @Inject constructor(
    private val contaDao: ContaDao
){
    //Pega as contas por mes
    fun getContasPorMes(mes: String): Flow<List<Conta>> {
        return contaDao.getContas().map { contas ->
            contas.filter { conta ->
                val dataFormatada = conta.dateTime.toLocalDateTime()
                val mesTraduzido = traduzData(dataFormatada.month.name).take(3)
                mesTraduzido == mes
            }
        }
    }

    suspend fun adicionarConta(conta: Conta){

        val id = contaDao.insertConta(conta)

        Log.d(TAG, "adicionaConta: id da conta ao criar: $id")
    }
    suspend fun apagaConta(conta: Conta) {
        return contaDao.apagarConta(conta)
    }
}
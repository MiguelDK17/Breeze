package com.migueldk17.breeze.data.local.repository

import android.util.Log
import android.content.ContentValues.TAG
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.data.local.dao.ContaDao
import com.migueldk17.breeze.data.local.entity.Conta
import com.migueldk17.breeze.data.local.relation.ContaComParcelas
import com.migueldk17.breeze.ui.utils.traduzData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContaRepository @Inject constructor(
    private val contaDao: ContaDao
){
    fun getContas(): Flow<List<Conta>> =  contaDao.getContas()

    fun getContasMes(mesAno: String): Flow<List<Conta>>{
        Log.d(TAG, "getContasMes: $mesAno")
        return contaDao.getContasMes(mesAno)
    }

    fun getContasComParcelas(): Flow<List<ContaComParcelas>> = contaDao.getContasComParcelas()

    suspend fun getContaById(id: Long): Conta? = contaDao.getContaById(id)

    //Pega as contas por mes
    fun getContasPorMes(mes: String): Flow<List<Conta>> {
        return contaDao.getContasHistorico().map { contas ->
            contas.filter { conta ->
                val dataFormatada = conta.dateTime.toLocalDateTime()
                val mesTraduzido = traduzData(dataFormatada.month?.name ?: "").take(3)
                mesTraduzido == mes
            }
        }
    }

    suspend fun efetuarPagamentoConta(data: String, contaId: Long, formaDePagamento: String): Int {
       val resultado =  contaDao.efetuarPagamentoConta(data, contaId, formaDePagamento)
        val conta = if (resultado == 1){
            Log.d(TAG, "efetuarPagamentoConta: Operação feita com sucesso!")
            resultado
        } else {
            Log.d(TAG, "efetuarPagamentoConta: Não foi possível atualizar a conta. Verifique o id e o formato da data e tente novamente")
            resultado
        }
        return conta
    }

    suspend fun adicionarConta(conta: Conta): Long{

        val id = contaDao.insertConta(conta)

        return id
    }

    suspend fun atualizaConta(conta: Conta){
        contaDao.atualizarConta(conta)
    }

    suspend fun apagaConta(conta: Conta) {
        return contaDao.apagarConta(conta)
    }
}
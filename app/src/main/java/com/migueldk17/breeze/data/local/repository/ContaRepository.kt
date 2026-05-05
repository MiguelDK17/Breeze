package com.migueldk17.breeze.data.local.repository

import android.util.Log
import android.content.ContentValues.TAG
import com.migueldk17.breeze.data.local.dao.ContaDao
import com.migueldk17.breeze.data.local.entity.ContaEntity
import com.migueldk17.breeze.data.local.relation.ContaComParcelas
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContaRepository @Inject constructor(
    private val contaDao: ContaDao
){
    fun getContasMes(mesAno: String): Flow<List<ContaEntity>>{
        Log.d(TAG, "getContasMes: $mesAno")
        return contaDao.getContasMes(mesAno)
    }

    fun getContasComParcelas(): Flow<List<ContaComParcelas>> = contaDao.getContasComParcelas()

    suspend fun getContaById(id: Long): ContaEntity? = contaDao.getContaById(id)

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

    suspend fun adicionarConta(contaEntity: ContaEntity): Long{

        val id = contaDao.insertConta(contaEntity)

        return id
    }

    suspend fun atualizaConta(contaEntity: ContaEntity){
        contaDao.atualizarConta(contaEntity)
    }

    suspend fun apagaConta(contaEntity: ContaEntity) {
        return contaDao.apagarConta(contaEntity)
    }
}
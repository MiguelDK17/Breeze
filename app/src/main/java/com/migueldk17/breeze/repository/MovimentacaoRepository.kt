package com.migueldk17.breeze.repository

import com.migueldk17.breeze.dao.MovimentacaoDao
import com.migueldk17.breeze.entity.MovimentacaoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovimentacaoRepository @Inject constructor(
    private val movimentacaoDao: MovimentacaoDao
) {
    //Adiciona a conta para o Room
    suspend fun adicionarMovimentacao(movimentacaoEntity: MovimentacaoEntity) {
        movimentacaoDao.inserirReceita(movimentacaoEntity)
    }
    //Pega a receita já armazanada no Room
      fun getSaldoTotal(): Flow<Double?> {
        return movimentacaoDao.getSaldoTotal()
    }
    fun getMovimentacoesDoMes(mesAno: String): Flow<List<MovimentacaoEntity>> {
        return movimentacaoDao.getReceitasDoMes(mesAno)
    }
    fun getAllMovimentacoes(): Flow<List<MovimentacaoEntity>> {
        return movimentacaoDao.getAllMovimentacoes()
    }

    suspend fun apagaMovimentacao(movimentacaoEntity: MovimentacaoEntity) {
        movimentacaoDao.apagaMovimentacao(movimentacaoEntity)
    }
}
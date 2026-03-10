package com.migueldk17.breeze.data.local.repository

import com.migueldk17.breeze.data.local.dao.MovimentacaoDao
import com.migueldk17.breeze.data.local.entity.MovimentacaoEntity
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.mapper.entity.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import javax.inject.Inject

class MovimentacaoRepository @Inject constructor(
    private val movimentacaoDao: MovimentacaoDao
) {
    //Adiciona a conta para o Room
    suspend fun adicionarMovimentacao(movimentacaoEntity: MovimentacaoEntity) {
        movimentacaoDao.inserirReceita(movimentacaoEntity)
    }
    //Pega a receita já armazanada no Room
      fun getSaldoTotal(): Flow<BigDecimal?> {
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

    fun getMovimentacoes(): Flow<List<MovimentacaoDomain>> {
        return movimentacaoDao.getAllMovimentacoes().map { list ->
            list.map {
                it.toDomain()
            }
        }
    }
}
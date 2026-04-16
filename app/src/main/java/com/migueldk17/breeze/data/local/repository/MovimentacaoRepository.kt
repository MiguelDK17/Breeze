package com.migueldk17.breeze.data.local.repository

import com.migueldk17.breeze.data.local.dao.MovimentacaoDao
import com.migueldk17.breeze.data.local.entity.MovimentacaoEntity
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.dto.CategoryTotalDto
import com.migueldk17.breeze.mapper.entity.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import javax.inject.Inject

class MovimentacaoRepository @Inject constructor(
    private val movimentacaoDao: MovimentacaoDao
) {
    //Adiciona a conta para o Room
    suspend fun insertMovimentacao(movimentacaoEntity: MovimentacaoEntity) {
        movimentacaoDao.insertMovimentacao(movimentacaoEntity)
    }
    //Pega a receita já armazanada no Room
      fun getSaldoTotal(): Flow<BigDecimal?> {
        return movimentacaoDao.getSaldoTotal()
    }
    fun getReceitasDoMes(mesAno: String): Flow<List<MovimentacaoEntity>> {
        return movimentacaoDao.getReceitasDoMes(mesAno)
    }
    fun getAllMovimentacoes(): Flow<List<MovimentacaoEntity>> {
        return movimentacaoDao.getAllMovimentacoes()
    }

    fun apagaMovimentacao(movimentacaoEntity: MovimentacaoEntity) {
        movimentacaoDao.apagaMovimentacao(movimentacaoEntity)
    }

    fun getMovimentacoesDoDia(diaMesAno: String): Flow<List<MovimentacaoDomain>> {
        return movimentacaoDao.getMovimentacoesDoDia(diaMesAno).map { list ->
            list.map {
                it.toDomain()
            }
        }
    }

    fun getMovimentacoesDoMes(mesAno: String): Flow<List<MovimentacaoDomain>> {
        return movimentacaoDao.getMovimentacoesDoMes(mesAno).map { list ->
            list.map {
                it.toDomain()
            }
        }
    }

    fun getCategoryTotalByMonth(mesAno: String): Flow<List<CategoryTotalDto>> {
        return movimentacaoDao.getCategoryTotalsByMonth(mesAno)
    }
}
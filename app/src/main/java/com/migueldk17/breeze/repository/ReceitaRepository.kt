package com.migueldk17.breeze.repository

import com.migueldk17.breeze.dao.ReceitaDao
import com.migueldk17.breeze.entity.Receita
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReceitaRepository @Inject constructor(
    private val receitaDao: ReceitaDao
) {
    //Adiciona a conta para o Room
    suspend fun adicionarReceita(receita: Receita) {
        receitaDao.inserirReceita(receita)
    }
    //Pega a receita já armazanada no Room
      fun getSaldoTotal(): Flow<Double?> {
        return receitaDao.getSaldoTotal()
    }
    fun getReceitasDoMes(mesAno: String): Flow<List<Receita>> {
        return receitaDao.getReceitasDoMes(mesAno)
    }

}
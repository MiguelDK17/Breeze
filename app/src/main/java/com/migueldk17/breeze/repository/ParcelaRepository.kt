package com.migueldk17.breeze.repository

import com.migueldk17.breeze.dao.ParcelaDao
import com.migueldk17.breeze.entity.ParcelaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ParcelaRepository @Inject constructor(
    private val parcelaDao: ParcelaDao,

){
    //Adiciona as parcelas da conta baseado no id da conta pai
    suspend fun adicionaParcelas(parcelaEntity: List<ParcelaEntity>){
            parcelaDao.inserirParcelas(parcelaEntity)
    }

    suspend fun buscaParcelaMes(idContaPai: Long, mesAno: String): ParcelaEntity {
        return parcelaDao.getParcelaDoMes(idContaPai, mesAno)
    }

    //Busca a parcela baseado no id da conta pai
     fun buscaParcelas(idContaPai: Long): Flow<List<ParcelaEntity>>{
        return parcelaDao.getParcelasDaConta(idContaPai)
    }

    //Deleta todas as parcelas
    suspend fun apagarTodasAsParcelas(parcelas: List<ParcelaEntity>){
        parcelaDao.apagarTodasAsParcelas(parcelas)
    }
}
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
     fun buscaTodasAsParcelasDoMes(mesAno: String): Flow<List<ParcelaEntity>>{
        return parcelaDao.getParcelasDoMes(mesAno)
    }
     fun buscaParcelaDoMesParaConta(idContaPai: Long, mesAno: String): Flow<ParcelaEntity?> {
        return parcelaDao.getParcelaDoMes(idContaPai, mesAno)
    }
    suspend fun getParcelaPorId(idParcela: Long): ParcelaEntity? {
        return parcelaDao.getParcelaPorId(idParcela)
    }

    //Busca a parcela baseado no id da conta pai
     fun buscaParcelasDaConta(idContaPai: Long): Flow<List<ParcelaEntity>>{
        return parcelaDao.getParcelasDaConta(idContaPai)
    }


    //Deleta todas as parcelas
    suspend fun apagarTodasAsParcelas(parcelas: List<ParcelaEntity>){
        parcelaDao.apagarTodasAsParcelas(parcelas)
    }
}
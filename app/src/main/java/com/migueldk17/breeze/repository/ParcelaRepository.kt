package com.migueldk17.breeze.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.migueldk17.breeze.dao.ParcelaDao
import com.migueldk17.breeze.entity.ParcelaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ParcelaRepository @Inject constructor(
    private val parcelaDao: ParcelaDao,

){
    //Adiciona as parcelas da conta baseado no id da conta pai
    suspend fun adicionaParcelas(parcelaEntity: List<ParcelaEntity>){
        try {
            parcelaDao.inserirParcelas(parcelaEntity)
            Log.d(TAG, "adicionaParcelas: Parcelas adicionadas com sucesso")
        } catch (e: Exception){
            Log.d(TAG, "adicionaParcelas: Ocorreu um erro ao adicionar a conta, $e")
        }
    }

    //Busca a parcela baseado no id da conta pai
     fun buscaParcelas(idContaPai: Long): Flow<List<ParcelaEntity>>{
        return parcelaDao.getParcelasDaConta(idContaPai)
    }
}
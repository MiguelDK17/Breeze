package com.migueldk17.breeze.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.migueldk17.breeze.entity.ParcelaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ParcelaDao {
    //Busca todas as parcelas do mês atual
    @Query("SELECT * FROM parcela_entity WHERE data_vencimento LIKE :mesAno || '%'")
     fun getParcelasDoMes(mesAno: String): Flow<List<ParcelaEntity>>

    @Query("SELECT * FROM parcela_entity WHERE id_conta_pai = :idContaPai AND data_vencimento LIKE :mesAno LIMIT 1")
    fun getParcelaDoMes(idContaPai: Long, mesAno: String): Flow<ParcelaEntity?>
    //Busca todas as parcelas de uma conta específica
    @Query("SELECT * FROM parcela_entity WHERE id_conta_pai = :idContaPai ORDER BY numero_parcela ASC")
    fun getParcelasDaConta(idContaPai: Long): Flow<List<ParcelaEntity>>
    @Query("SELECT * FROM parcela_entity WHERE id = :idParcela")
    suspend fun getParcelaPorId(idParcela: Long): ParcelaEntity?
    //Insere as parcelas de uma conta específica
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirParcelas(parcelas: List<ParcelaEntity>)
    //Atualiza uma parcela
    @Query("UPDATE parcela_entity SET esta_paga = 1,  data_pagamento = :data WHERE id_conta_pai = :idContaPai AND id = :idParcela")
    suspend fun efetuarPagamentoParcela(data: String, idContaPai: Long, idParcela: Long): Int
    @Update
    suspend fun atualizarParcela(parcela: ParcelaEntity)
    //Deleta uma parcela
    @Delete
    suspend fun deletarParcela(parcela: ParcelaEntity)
    //Deleta todas as parcelas
    @Delete
    suspend fun apagarTodasAsParcelas(parcelas: List<ParcelaEntity>)


}